package org.example;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JSONUtils {
    public static JSONObject parseJSON(String filePath) throws IOException {
        String jsonData = new String(Files.readAllBytes(Paths.get(filePath)));
        return new JSONObject(jsonData);
    }

    public static void printJSON(JSONObject jsonObject) {
        System.out.println("Root element :BookShelf");
        System.out.println("----------------------------");

        JSONArray bookShelf = jsonObject.getJSONArray("BookShelf");
        for (int i = 0; i < bookShelf.length(); i++) {
            JSONObject book = bookShelf.getJSONObject(i);

            System.out.println("Title : " + book.getString("title"));
            System.out.println("Published Year : " + book.getInt("publishedYear"));
            System.out.println("Number of Pages : " + book.getInt("numberOfPages"));

            System.out.print("Authors : ");
            JSONArray authors = book.getJSONArray("authors");
            for (int j = 0; j < authors.length(); j++) {
                System.out.print(authors.getString(j));
                if (j < authors.length() - 1) System.out.print(", ");
            }
            System.out.println("\n");
        }
    }

    public static void addBookToJSON(JSONObject jsonObject, Book book) {
        JSONArray bookShelf = jsonObject.getJSONArray("BookShelf");

        JSONObject newBook = new JSONObject();
        newBook.put("title", book.getTitle());
        newBook.put("publishedYear", book.getPublishedYear());
        newBook.put("numberOfPages", book.getNumberOfPages());
        newBook.put("authors", book.getAuthors());

        bookShelf.put(newBook);
    }
}
