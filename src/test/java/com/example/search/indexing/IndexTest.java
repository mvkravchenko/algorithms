package com.example.search.indexing;

import com.example.search.base.WikiFetcher;
import com.example.search.utils.JsoupTestUtils;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import javax.xml.bind.Element;
import java.io.IOException;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.number.OrderingComparison.comparesEqualTo;

public class IndexTest {

    private Index index;
    private JsoupTestUtils utils = new JsoupTestUtils();

    @BeforeEach
    public void setUp() {
        index = new Index();
    }

    @Test
    public void testIndexPage() throws IOException {
        // add two pages to the index
        String filename1 = "Java_(Programming_Language).html";
        Elements paragraphs = utils.parseHtmlFromFile(filename1);
        index.indexPage(filename1, paragraphs);

        String filename2 = "Programming_language.html";
        Elements paragraphs2 = utils.parseHtmlFromFile(filename2);
        index.indexPage(filename2, paragraphs2);

        // check the results: the word "occur" only appears on one page, twice
        Set<TermCounter> set = index.get("occur");
        assertThat(set.size(), comparesEqualTo(1));

        for (TermCounter tc: set) {
            // this loop only happens once
            assertThat(tc.size(), comparesEqualTo(4798));
            assertThat(tc.get("occur"), comparesEqualTo(2));
            assertThat(tc.get("not there"), comparesEqualTo(0));
        }
    }
}
