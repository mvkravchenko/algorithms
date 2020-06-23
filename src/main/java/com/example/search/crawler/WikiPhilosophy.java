package com.example.search.crawler;

import com.example.search.base.WikiFetcher;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WikiPhilosophy {

    private final static List<String> visited = new ArrayList<>();
    private final static WikiFetcher wf = WikiFetcher.getInstance();

    private static final Logger LOG = LoggerFactory.getLogger(WikiPhilosophy.class);
    private static final String LINK_NOT_FOUND = "Empty content error. Valid link not found on page %s. Visited pages: %s";
    private static final String LOOP_ERR = "We are looped. Exit. Visited pages: %s";
    private static final String LIMIT_EXCEEDED = "Failed to get to the Philosophy page within the limit of %s attempts.";

    /**
     * Tests a conjecture about Wikipedia and Philosophy.
     *
     * https://en.wikipedia.org/wiki/Wikipedia:Getting_to_Philosophy
     *
     * 1. Clicking on the first non-parenthesized, non-italicized link
     * 2. Ignoring external links, links to the current page, or red links
     * 3. Stopping when reaching "Philosophy", a page with no links or a page
     *    that does not exist, or when a loop occurs
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        String destination = "https://en.wikipedia.org/wiki/Philosophy";
        String source = "https://en.wikipedia.org/wiki/Java_(programming_language)";
//        String source = "https://en.wikipedia.org/wiki/Wikipedia:Getting_to_Philosophy";
//        String source = "https://en.wikipedia.org/wiki/Javanese_language";
        testConjecture(destination, source, 10);
    }

    /**
     * Starts from given URL and follows first link until it finds the destination or exceeds the limit.
     *
     * @param destination
     * @param source
     * @throws IOException
     */
    public static void testConjecture(String destination, String source, int limit) throws IOException {
        String url = source;
        boolean found = false;
        for (int i = 0; i< limit; i++) {
            if (visited.contains(url)) {
                LOG.error(format(LOOP_ERR, visited));
                break;
            } else {
                visited.add(url);
                Element link = getFirstValidLink(url);
                if (link == null) {
                    LOG.error(format(LINK_NOT_FOUND, url, visited));
                    break;
                }
                LOG.info("**{}**", link.text());
                if (link.attr("abs:href").equals(destination)){
                    LOG.info("Success!");
                    found = true;
                    break;
                }
                url = link.absUrl("abs:href");
            }
        }
        if (!found)
            LOG.error(format(LIMIT_EXCEEDED, limit));
    }

    private static Element getFirstValidLink(String url) throws IOException {
        LOG.info("Fetching {}", url);
        Elements paragraphs = wf.fetchWikipedia(url);
        WikiParser wp = new WikiParser(paragraphs);
        return wp.getFirstValidLink();
    }

    private static String format(String text, Object... args){
        return String.format(text, args);
    }
}
