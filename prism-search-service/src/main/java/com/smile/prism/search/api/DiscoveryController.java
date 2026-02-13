package com.smile.prism.search.api;

import com.smile.prism.search.service.DiscoveryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.smile.prism.search.api.SearchResponse;
import com.smile.prism.search.api.DiscoveryRequest;

@RestController
@RequestMapping("/api/v1/discovery")
@RequiredArgsConstructor
public class DiscoveryController {

    private final DiscoveryService discoveryService;

    /**
     * Advanced Search Endpoint.
     * POST is used here to accommodate complex search bodies (standard in Discovery Engines).
     */
    @PostMapping("/search")
    public SearchResponse advancedSearch(@RequestBody @Valid DiscoveryRequest request) {
        return discoveryService.discover(request);
    }
}