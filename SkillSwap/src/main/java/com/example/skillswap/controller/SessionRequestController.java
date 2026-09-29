package com.example.skillswap.controller;

import com.example.skillswap.entity.SessionRequest;
import com.example.skillswap.service.SessionRequestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/session-requests")
@CrossOrigin(origins = "*")
public class SessionRequestController {

    private final SessionRequestService sessionRequestService;

    public SessionRequestController(SessionRequestService sessionRequestService) {
        this.sessionRequestService = sessionRequestService;
    }

    @PostMapping
    public ResponseEntity<SessionRequest> createRequest(
            @RequestBody SessionRequest request) {

        return new ResponseEntity<>(
                sessionRequestService.createRequest(request),
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<List<SessionRequest>> getAllRequests() {

        return ResponseEntity.ok(
                sessionRequestService.getAllRequests()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<SessionRequest> getRequestById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                sessionRequestService.getRequestById(id)
        );
    }

    @PutMapping("/{id}/confirm")
    public ResponseEntity<SessionRequest> confirmSession(
            @PathVariable Long id,
            @RequestParam Double deliveredHours) {

        return ResponseEntity.ok(
                sessionRequestService.confirmSession(id, deliveredHours)
        );
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<SessionRequest> cancelSession(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                sessionRequestService.cancelSession(id)
        );
    }
}