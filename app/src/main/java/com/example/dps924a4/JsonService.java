package com.example.dps924a4;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class JsonService {
    public ArrayList<Book> getBooksFromJSON(String json)  {
        ArrayList<Book> books = new ArrayList<>(0);
        try {
            JSONObject data = new JSONObject(json);
            JSONArray items = data.getJSONArray("items");
            for (int i = 0 ; i < items.length();i++){
                JSONObject volume = items.getJSONObject(i);
                JSONObject volumeInfo = volume.getJSONObject("volumeInfo");

                Book book = new Book();
                book.setId(volume.getString("id"));
                book.setTitle(volumeInfo.getString("title"));
                if (volumeInfo.has("authors")) {
                    book.setAuthors(getStringArray(volumeInfo.getJSONArray("authors")));
                }
                if (volumeInfo.has("publisher")) {
                    book.setPublisher(volumeInfo.getString("publisher"));
                }
                if (volumeInfo.has("publishedDate")) {
                    book.setPublishedDate(volumeInfo.getString("publishedDate"));
                }
                if (volumeInfo.has("description")) {
                    book.setDescription(volumeInfo.getString("description"));
                }
                if (volumeInfo.has("pageCount")) {
                    book.setPageCount(volumeInfo.getInt("pageCount"));
                }
                if (volumeInfo.has("categories")) {
                    book.setCategories(getStringArray(volumeInfo.getJSONArray("categories")));
                }
                if (volumeInfo.has("averageRating")) {
                    book.setAverageRating(volumeInfo.getInt("averageRating"));
                }
                if (volumeInfo.has("ratingCount")) {
                    book.setRatingCount(volumeInfo.getInt("ratingCount"));
                }
                if (volumeInfo.has("maturityRating")) {
                    book.setMaturityRating(volumeInfo.getString("maturityRating"));
                }
                if (volumeInfo.has("imageLinks")) {
                    book.setImageURL(volumeInfo.getJSONObject("imageLinks").getString("thumbnail"));
                }
                if (volumeInfo.has("language")) {
                    book.setLanguage(volumeInfo.getString("language"));
                }
                if (volumeInfo.has("canonicalVolumeLink")) {
                    book.setLink(volumeInfo.getString("canonicalVolumeLink"));
                }
                books.add(book);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return books;
    }

    public static String[] getStringArray(JSONArray jsonArray) {
        int size = jsonArray.length();
        String[] stringArray = new String[size];
        for (int i = 0; i < size; i++) {
            stringArray[i] = jsonArray.optString(i);
        }
        return stringArray;
    }
}
