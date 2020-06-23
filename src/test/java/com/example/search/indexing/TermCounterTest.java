package com.example.search.indexing;

import com.example.search.base.WikiFetcher;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.number.OrderingComparison.comparesEqualTo;

public class TermCounterTest {

    private TermCounter counter;

    /**
     * @throws java.lang.Exception
     */
    @BeforeEach
    public void setUp() throws Exception {
        String url = "https://en.wikipedia.org/wiki/Java_(programming_language)";

        WikiFetcher wf = WikiFetcher.getInstance();
        Elements paragraphs = wf.fetchWikipedia(url);

        counter = new TermCounter(url.toString());
        counter.processElements(paragraphs);
    }

    @Test
    public void testSize() {
        assertThat(counter.size(), comparesEqualTo(4450));
    }
}
