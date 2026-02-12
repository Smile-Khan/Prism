package com.smile.prism.search.service;

import com.smile.prism.search.api.SearchResponse;
import com.smile.prism.search.model.EventDocument;
import com.smile.prism.search.repository.EventSearchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Core business logic for the Query-side of the Prism Engine.
 * Optimized for execution on Virtual Threads.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class DiscoveryService {

    private final EventSearchRepository searchRepository;

    /**
     * Performs discovery based on a text query.
     */
    public SearchResponse discover(String query) {
        log.info("Executing discovery query for: [{}]", query);

        List<EventDocument> results = searchRepository.findByTitleContainingIgnoreCase(query);

        return new SearchResponse(results.size(), results, "SUCCESS");
    }
}