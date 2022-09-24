package ru.practicum.ewm.request.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.request.dto.ParticipationRequestDto;
import ru.practicum.ewm.request.service.RequestService;

import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/users")
@Validated
@AllArgsConstructor
@Slf4j
public class RequestController {
    private final RequestService requestService;

    @GetMapping("/{userId}/requests")
    public List<ParticipationRequestDto> getRequests(@PathVariable @Positive Long userId) {
        log.info("Get requests by user with id = {}", userId);
        return requestService.getRequests(userId);
    }

    @PostMapping("/{userId}/requests")
    public ParticipationRequestDto createRequest(@PathVariable @Positive Long userId,
                                                 @RequestParam @Positive Long eventId) {
        log.info("Create request by user with id = {} and eventId = {}", userId, eventId);
        return requestService.createRequest(userId, eventId);
    }

    @PatchMapping("/{userId}/requests/{requestId}/cancel")
    public ParticipationRequestDto cancelRequest(@PathVariable @Positive Long userId,
                                                 @PathVariable @Positive Long requestId) {
        log.info("Cancel request by user with id = {} and requestId = {}", userId, requestId);
        return requestService.cancelRequest(userId, requestId);
    }

    @GetMapping("/{userId}/events/{eventId}/requests")
    public List<ParticipationRequestDto> getEventRequests(@PathVariable @Positive Long userId,
                                                          @PathVariable @Positive Long eventId) {
        log.info("Get requests by user with id = {} and eventId = {}", userId, eventId);
        return requestService.getEventRequests(userId, eventId);
    }

    @PatchMapping("/users/{userId}/events/{eventId}/requests/{reqId}/confirm")
    public ParticipationRequestDto confirmRequest(@PathVariable @Positive Long userId,
                                                  @PathVariable @Positive Long eventId,
                                                  @PathVariable @Positive Long reqId) {
        log.info("Confirm request with reqId = {},  by user with userId = {} and eventId = {}", reqId, userId, eventId);
        return requestService.confirmRequest(userId, eventId, reqId);
    }

    @PatchMapping("/users/{userId}/events/{eventId}/requests/{reqId}/confirm")
    public ParticipationRequestDto rejectRequest(@PathVariable @Positive Long userId,
                                                  @PathVariable @Positive Long eventId,
                                                  @PathVariable @Positive Long reqId) {
        log.info("Reject request with reqId = {},  by user with userId = {} and eventId = {}", reqId, userId, eventId);
        return requestService.rejectRequest(userId, eventId, reqId);
    }
}
