package com.smile.prism.search.api;

import com.smile.prism.search.model.EventDocument;
import java.util.List;

/**
 * Immutable Search Response.
 * Leveraging Java 21 Records for efficient memory management in high-throughput scenarios.
 */
public record SearchResponse(
        int totalHits,
        List<EventDocument> results,
        String status
) {}