package com.example.skillswap.controller;

import com.example.skillswap.entity.SkillOffer;
import com.example.skillswap.service.SkillOfferService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/skill-offers")
@CrossOrigin(origins = "*")
public class SkillOfferController {

    private final SkillOfferService skillOfferService;

    public SkillOfferController(SkillOfferService skillOfferService) {
        this.skillOfferService = skillOfferService;
    }

    @PostMapping
    public ResponseEntity<SkillOffer> createSkillOffer(@RequestBody SkillOffer skillOffer) {
        return new ResponseEntity<>(
                skillOfferService.createSkillOffer(skillOffer),
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<List<SkillOffer>> getAllSkillOffers() {
        return ResponseEntity.ok(skillOfferService.getAllSkillOffers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SkillOffer> getSkillOfferById(@PathVariable Long id) {
        return ResponseEntity.ok(skillOfferService.getSkillOfferById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SkillOffer> updateSkillOffer(
            @PathVariable Long id,
            @RequestBody SkillOffer skillOffer) {
        return ResponseEntity.ok(
                skillOfferService.updateSkillOffer(id, skillOffer)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSkillOffer(@PathVariable Long id) {
        skillOfferService.deleteSkillOffer(id);
        return ResponseEntity.ok("Skill offer deleted successfully");
    }
}