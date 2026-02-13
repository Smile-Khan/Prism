package com.smile.prism.search.api;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

/**
 * Structured request for Advanced Discovery.
 */
public record DiscoveryRequest(
        @NotBlank(message = "Search query cannot be empty")
        String query,

        String category,

        @Min(0)
        int page,

        @Min(1)
        int size
) {
    // Default constructor for standard searches
    public DiscoveryRequest {
        if (page < 0) page = 0;
        if (size <= 0) size = 10;
    }
}