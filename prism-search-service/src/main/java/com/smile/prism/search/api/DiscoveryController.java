package com.smile.prism.search.api;

import com.smile.prism.search.service.DiscoveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Public Ingress for the Prism Discovery Engine.
 */
@RestController
@RequestMapping("/api/v1/discovery")
@RequiredArgsConstructor
public class DiscoveryController {

    private final DiscoveryService discoveryService;

    /**
     * GET /api/v1/discovery/search?q=Industrial
     */
    @GetMapping("/search")
    public ResponseEntity<SearchResponse> search(@RequestParam(name = "q") String query) {
        if (query == null || query.isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(discoveryService.discover(query));
    }
}