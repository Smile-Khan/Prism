package com.smile.prism.search.service;

import com.smile.prism.search.api.DiscoveryRequest;
import com.smile.prism.search.api.SearchResponse;
import com.smile.prism.search.model.EventDocument;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Advanced Discovery Service utilizing Elasticsearch Native Query DSL.
 * Implements Fuzzy Search and Category Filtering.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class DiscoveryService {

    private final ElasticsearchOperations elasticsearchOperations;

    public SearchResponse discover(DiscoveryRequest request) {
        log.info("Executing Advanced Discovery: query='{}', category='{}'", request.query(), request.category());

        // 1. Build the Multi-Match Query with Fuzziness
        // This searches the 'title' field and allows for 1-2 character typos (AUTO)
        Query query = NativeQuery.builder()
                .withQuery(q -> q
                        .bool(b -> {
                            b.must(m -> m
                                    .multiMatch(mm -> mm
                                            .fields("title")
                                            .query(request.query())
                                            .fuzziness("AUTO")
                                    )
                            );
                            // 2. Add Category Filter if provided
                            if (request.category() != null && !request.category().isBlank()) {
                                b.filter(f -> f
                                        .term(t -> t
                                                .field("category")
                                                .value(request.category())
                                        )
                                );
                            }
                            return b;
                        })
                )
                .withPageable(PageRequest.of(request.page(), request.size()))
                .build();

        // 3. Execute Search
        SearchHits<EventDocument> searchHits = elasticsearchOperations.search(query, EventDocument.class);

        // 4. Map Results
        List<EventDocument> results = searchHits.getSearchHits().stream()
                .map(SearchHit::getContent)
                .toList();

        return new SearchResponse((int) searchHits.getTotalHits(), results, "SUCCESS");
    }
}