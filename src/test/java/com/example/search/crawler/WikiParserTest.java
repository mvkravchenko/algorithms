package com.example.search.crawler;

import com.example.search.utils.JsoupTestUtils;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

class WikiParserTest {

    private JsoupTestUtils utils = new JsoupTestUtils();

    @Test
    void when_first_link_is_valid_then_return_it() throws NoSuchFieldException, IOException {
        String requiredLink = "<a href=\"/wiki/Point_and_click\" title=\"Point and click\">Clicking</a>";
        Elements para = utils.parseHtmlFromFile("FirstLinkIsValid.html");
        WikiParser wp = new WikiParser(para);

        assertThat(wp.getFirstValidLink().toString(), equalTo(requiredLink));
    }

    @Test
    void when_fist_link_is_external_then_skip_it() throws IOException {
        String requiredLink = "<a href=\"/wiki/Wikipedia\" title=\"Wikipedia\">Wikipedia</a>";
        Elements para = utils.parseHtmlFromFile("FirstLinkIsExternal.html");
        WikiParser wp = new WikiParser(para);
        assertThat(wp.getFirstValidLink().toString(), equalTo(requiredLink));
    }

    @Test
    void when_first_link_is_help_page_then_skip_it() throws IOException {
        String requiredLink = "<a href=\"/wiki/Test\" title=\"Wikipedia\">Test</a>";
        Elements para = utils.parseHtmlFromFile("FirstLinkIsHelp.html");
        WikiParser wp = new WikiParser(para);
        assertThat(wp.getFirstValidLink().toString(), equalTo(requiredLink));
    }

    @Test
    void when_first_link_is_help_then_skip_it(){

    }

    @Test
    void when_first_link_in_brackets_then_skip_it() throws IOException {
        String requiredLink = "<a href=\"/wiki/Wikipedia\" title=\"Wikipedia\">Wikipedia</a>";
        Elements para = utils.parseHtmlFromFile("FirstLinkInBrackets.html");
        WikiParser wp = new WikiParser(para);
        assertThat(wp.getFirstValidLink().toString(), equalTo(requiredLink));
    }

    @Disabled
    @Test
    void when_first_link_in_brackets_in_text_then_skip_it() throws IOException {
        String requiredLink = "<a href=\"/wiki/Wikipedia\" title=\"Wikipedia\">Wikipedia</a>";
        Elements para = utils.parseHtmlFromFile("FirstLinkInBrackets2.html");
        WikiParser wp = new WikiParser(para);
        assertThat(wp.getFirstValidLink().toString(), equalTo(requiredLink));
    }
}