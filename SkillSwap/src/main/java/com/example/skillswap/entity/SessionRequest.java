package com.example.skillswap.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class SessionRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long requesterId;
    private Long skillOfferId;
    private Double requestedHours;
    private Double deliveredHours;
    private String status;

    public SessionRequest() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRequesterId() {
        return requesterId;
    }

    public void setRequesterId(Long requesterId) {
        this.requesterId = requesterId;
    }

    public Long getSkillOfferId() {
        return skillOfferId;
    }

    public void setSkillOfferId(Long skillOfferId) {
        this.skillOfferId = skillOfferId;
    }

    public Double getRequestedHours() {
        return requestedHours;
    }

    public void setRequestedHours(Double requestedHours) {
        this.requestedHours = requestedHours;
    }

    public Double getDeliveredHours() {
        return deliveredHours;
    }

    public void setDeliveredHours(Double deliveredHours) {
        this.deliveredHours = deliveredHours;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}