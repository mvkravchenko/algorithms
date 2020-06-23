package com.example.search.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;

public class JsoupTestUtils {

    public Elements parseHtmlFromFile(String filename) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File page = new File(classLoader.getResource(filename).getFile());
        Document doc = Jsoup.parse(page, "UTF-8");
        return doc.select("p");
    }
}
