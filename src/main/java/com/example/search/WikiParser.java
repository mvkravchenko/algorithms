package com.example.search;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

public class WikiParser {


    private final Elements paragraphs;

    private Deque<String> bracketsStack;

    public WikiParser(Elements paragraphs) {
        this.paragraphs = paragraphs;
        this.bracketsStack = new ArrayDeque<>();
    }

    /**
     *
     * @return the first valid link from the content of the page
     */
    public Element getFirstValidLink() {
        for (Element para: paragraphs) {
            Iterable<Node> iter = new WikiNodeIterable(para);
            for (Node node: iter) {
                if (node instanceof TextNode) {
                    processTextNode((TextNode)node);
                }
                if (node instanceof Element) {
                    if (processElementNode((Element)node))
                        return (Element)node;
                }
            }
        }
        return null;
    }

    /**
     *
     * @param element element to process
     * @return true if link is valid, i.e. it's a link, it's internal, not Help and not the same page, not in brackets or in italic
     */
    private boolean processElementNode(Element element) {
        if (!isInternalLink(element))
            return false;
        if (isItalic(element))
            return false;
        return !inBrackets();
    }

    private boolean inBrackets() {
        return !bracketsStack.isEmpty();
    }

    private boolean isItalic(Element element) {
        return false;
    }

    private boolean isInternalLink(Element element) {
        return element.tagName().equals("a") &&
                element.attr("href").startsWith("/wiki/") &&
                !element.attr("href").startsWith("/wiki/Help:");
    }

    /**
     * Method processes nodes to save brackets to determine if link is in brackets
     * @param node
     */
    private void processTextNode(TextNode node) {
        StringTokenizer st = new StringTokenizer(node.text(), " ()", true);
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            if (token.equals("("))
                bracketsStack.push(token);
            if (token.equals(")")) {
                if (bracketsStack.isEmpty()) {
                    throw new IllegalStateException("Unbalanced brackets. Exit");
                }
                bracketsStack.pop();
            }
        }
    }
}
