package com.example.skillswap.service;

import com.example.skillswap.entity.CreditLedger;
import com.example.skillswap.entity.Member;
import com.example.skillswap.entity.SessionRequest;
import com.example.skillswap.entity.SkillOffer;
import com.example.skillswap.repository.CreditLedgerRepository;
import com.example.skillswap.repository.MemberRepository;
import com.example.skillswap.repository.SessionRequestRepository;
import com.example.skillswap.repository.SkillOfferRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SessionRequestService {

    private final SessionRequestRepository sessionRequestRepository;
    private final MemberRepository memberRepository;
    private final SkillOfferRepository skillOfferRepository;
    private final CreditLedgerRepository creditLedgerRepository;

    public SessionRequestService(SessionRequestRepository sessionRequestRepository,
                                 MemberRepository memberRepository,
                                 SkillOfferRepository skillOfferRepository,
                                 CreditLedgerRepository creditLedgerRepository) {
        this.sessionRequestRepository = sessionRequestRepository;
        this.memberRepository = memberRepository;
        this.skillOfferRepository = skillOfferRepository;
        this.creditLedgerRepository = creditLedgerRepository;
    }

    public SessionRequest createRequest(SessionRequest request) {

        Member requester = memberRepository.findById(request.getRequesterId())
                .orElseThrow(() -> new RuntimeException("Requester not found"));

        SkillOffer skillOffer = skillOfferRepository.findById(request.getSkillOfferId())
                .orElseThrow(() -> new RuntimeException("Skill offer not found"));

        if (requester.getId().equals(skillOffer.getProviderId())) {
            throw new RuntimeException("You cannot request your own skill");
        }

        if (request.getRequestedHours() <= 0) {
            throw new RuntimeException("Requested hours must be positive");
        }

        if (request.getRequestedHours() > requester.getCreditBalance()) {
            throw new RuntimeException("Insufficient credit balance");
        }

        if (request.getRequestedHours() > skillOffer.getAvailableHours()) {
            throw new RuntimeException("Requested hours exceed available hours");
        }

        request.setDeliveredHours(0.0);
        request.setStatus("REQUESTED");

        return sessionRequestRepository.save(request);
    }

    public List<SessionRequest> getAllRequests() {
        return sessionRequestRepository.findAll();
    }

    public SessionRequest getRequestById(Long id) {
        return sessionRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Session request not found"));
    }

    public SessionRequest confirmSession(Long id, Double deliveredHours) {

        SessionRequest request = getRequestById(id);

        if (!request.getStatus().equals("REQUESTED")) {
            throw new RuntimeException("Session is already processed");
        }

        if (deliveredHours == null || deliveredHours <= 0) {
            throw new RuntimeException("Delivered hours must be positive");
        }

        if (deliveredHours > request.getRequestedHours()) {
            throw new RuntimeException("Delivered hours cannot exceed requested hours");
        }

        Member requester = memberRepository.findById(request.getRequesterId())
                .orElseThrow(() -> new RuntimeException("Requester not found"));

        SkillOffer skillOffer = skillOfferRepository.findById(request.getSkillOfferId())
                .orElseThrow(() -> new RuntimeException("Skill offer not found"));

        Member provider = memberRepository.findById(skillOffer.getProviderId())
                .orElseThrow(() -> new RuntimeException("Provider not found"));

        if (requester.getCreditBalance() < deliveredHours) {
            throw new RuntimeException("Insufficient credit balance");
        }

        if (skillOffer.getAvailableHours() < deliveredHours) {
            throw new RuntimeException("Not enough available hours");
        }

        requester.setCreditBalance(
                requester.getCreditBalance() - deliveredHours
        );

        provider.setCreditBalance(
                provider.getCreditBalance() + deliveredHours
        );

        skillOffer.setAvailableHours(
                skillOffer.getAvailableHours() - deliveredHours
        );

        request.setDeliveredHours(deliveredHours);
        request.setStatus("CONFIRMED");

        memberRepository.save(requester);
        memberRepository.save(provider);
        skillOfferRepository.save(skillOffer);
        sessionRequestRepository.save(request);

        CreditLedger debit = new CreditLedger();
        debit.setMemberId(requester.getId());
        debit.setSessionRequestId(request.getId());
        debit.setCredits(-deliveredHours);
        debit.setTransactionType("DEBIT");
        creditLedgerRepository.save(debit);

        CreditLedger credit = new CreditLedger();
        credit.setMemberId(provider.getId());
        credit.setSessionRequestId(request.getId());
        credit.setCredits(deliveredHours);
        credit.setTransactionType("CREDIT");
        creditLedgerRepository.save(credit);

        return request;
    }

    public SessionRequest cancelSession(Long id) {

        SessionRequest request = getRequestById(id);

        if (!request.getStatus().equals("REQUESTED")) {
            throw new RuntimeException("Only requested sessions can be cancelled");
        }

        request.setStatus("CANCELLED");

        return sessionRequestRepository.save(request);
    }
}