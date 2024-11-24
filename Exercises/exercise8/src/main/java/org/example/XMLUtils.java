package org.example;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class XMLUtils {
    public static Document parseXML(String filePath) throws Exception {
        File file = new File(filePath);
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        return builder.parse(file);
    }

    public static void printXML(Document document) {
        Element root = document.getDocumentElement();
        System.out.println("Root element :" + root.getTagName());
        System.out.println("----------------------------");

        NodeList bookList = root.getElementsByTagName("book");
        for (int i = 0; i < bookList.getLength(); i++) {
            Node bookNode = bookList.item(i);

            if (bookNode.getNodeType() == Node.ELEMENT_NODE) {
                Element book = (Element) bookNode;

                System.out.println("Title : " + book.getElementsByTagName("title").item(0).getTextContent());
                System.out.println("Published Year : " + book.getElementsByTagName("publishedYear").item(0).getTextContent());
                System.out.println("Number of Pages : " + book.getElementsByTagName("numberOfPages").item(0).getTextContent());

                System.out.print("Authors : ");
                NodeList authors = book.getElementsByTagName("author");
                for (int j = 0; j < authors.getLength(); j++) {
                    System.out.print(authors.item(j).getTextContent());
                    if (j < authors.getLength() - 1) System.out.print(", ");
                }
                System.out.println("\n");
            }
        }
    }

    public static void addBookToXML(Document document, Book book) {
        Element bookShelf = (Element) document.getElementsByTagName("BookShelf").item(0);

        Element newBook = document.createElement("book");

        Element titleElement = document.createElement("title");
        titleElement.setTextContent(book.getTitle());
        newBook.appendChild(titleElement);

        Element yearElement = document.createElement("publishedYear");
        yearElement.setTextContent(String.valueOf(book.getPublishedYear()));
        newBook.appendChild(yearElement);

        Element pagesElement = document.createElement("numberOfPages");
        pagesElement.setTextContent(String.valueOf(book.getNumberOfPages()));
        newBook.appendChild(pagesElement);

        Element authorsElement = document.createElement("authors");
        for (String author : book.getAuthors()) {
            Element authorElement = document.createElement("author");
            authorElement.setTextContent(author);
            authorsElement.appendChild(authorElement);
        }
        newBook.appendChild(authorsElement);

        bookShelf.appendChild(newBook);
    }
}
