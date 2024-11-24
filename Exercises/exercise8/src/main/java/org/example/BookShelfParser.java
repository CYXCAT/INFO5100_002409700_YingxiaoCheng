package org.example;

import org.json.JSONObject;
import org.w3c.dom.Document;

import java.util.Arrays;

public class BookShelfParser {
    public static void main(String[] args) throws Exception {
        // Parse and print XML
        System.out.println("Parsing XML...");
        Document xmlDocument = XMLUtils.parseXML("bookshelf.xml");
        XMLUtils.printXML(xmlDocument);

        // Add and re-print XML
        Book xmlBook = new Book("The Three-Body Problem", 2008, 390, Arrays.asList("Liu Cixin"));
        XMLUtils.addBookToXML(xmlDocument, xmlBook);
        System.out.println("\nAfter Adding New Book to XML:");
        XMLUtils.printXML(xmlDocument);

        // Parse and print JSON
        System.out.println("\nParsing JSON...");
        JSONObject jsonDocument = JSONUtils.parseJSON("bookshelf.json");
        JSONUtils.printJSON(jsonDocument);

        // Add and re-print JSON
        Book jsonBook = new Book("Diary of a Madman", 1918, 357, Arrays.asList("Lu Xun"));
        JSONUtils.addBookToJSON(jsonDocument, jsonBook);
        System.out.println("\nAfter Adding New Book to JSON:");
        JSONUtils.printJSON(jsonDocument);
    }
}
