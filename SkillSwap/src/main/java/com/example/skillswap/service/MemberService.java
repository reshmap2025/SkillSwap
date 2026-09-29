package com.example.skillswap.service;

import com.example.skillswap.entity.Member;
import com.example.skillswap.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member createMember(Member member) {
        return memberRepository.save(member);
    }

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    public Member getMemberById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Member not found"));
    }

    public Member updateMember(Long id, Member member) {
        Member existingMember = getMemberById(id);

        existingMember.setName(member.getName());
        existingMember.setEmail(member.getEmail());
        existingMember.setCreditBalance(member.getCreditBalance());

        return memberRepository.save(existingMember);
    }

    public void deleteMember(Long id) {
        Member member = getMemberById(id);
        memberRepository.delete(member);
    }
}