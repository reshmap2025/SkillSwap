package com.example.skillswap.service;

import com.example.skillswap.entity.SkillOffer;
import com.example.skillswap.repository.SkillOfferRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillOfferService {

    private final SkillOfferRepository skillOfferRepository;

    public SkillOfferService(SkillOfferRepository skillOfferRepository) {
        this.skillOfferRepository = skillOfferRepository;
    }

    public SkillOffer createSkillOffer(SkillOffer skillOffer) {
        return skillOfferRepository.save(skillOffer);
    }

    public List<SkillOffer> getAllSkillOffers() {
        return skillOfferRepository.findAll();
    }

    public SkillOffer getSkillOfferById(Long id) {
        return skillOfferRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Skill offer not found"));
    }

    public SkillOffer updateSkillOffer(Long id, SkillOffer skillOffer) {
        SkillOffer existingOffer = getSkillOfferById(id);

        existingOffer.setProviderId(skillOffer.getProviderId());
        existingOffer.setSkillName(skillOffer.getSkillName());
        existingOffer.setDescription(skillOffer.getDescription());
        existingOffer.setAvailableHours(skillOffer.getAvailableHours());

        return skillOfferRepository.save(existingOffer);
    }

    public void deleteSkillOffer(Long id) {
        SkillOffer skillOffer = getSkillOfferById(id);
        skillOfferRepository.delete(skillOffer);
    }
}