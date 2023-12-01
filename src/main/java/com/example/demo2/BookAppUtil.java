package com.example.demo2;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BookAppUtil {

    public static String fetchDataFromAPI(String apiUrl) throws IOException {
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");
        connection.setReadTimeout(5000);

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            return response.toString();
        } else {
            throw new IOException("Failed to fetch. HTTP error code: " + responseCode);
        }
    }

    public static BookData parseJson(String jsonResponse) {
        JSONObject jsonObject = new JSONObject(jsonResponse);

        if (jsonObject.has("items")) {
            JSONArray items = jsonObject.getJSONArray("items");

            if (items.length() > 0) {
                JSONObject volumeInfo = items.getJSONObject(0).optJSONObject("volumeInfo");

                String name = volumeInfo.optString("title", "Unknown Title");
                String author = getAuthors(volumeInfo.optJSONArray("authors"));
                String imageUrl = getThumbnailUrl(volumeInfo.optJSONObject("imageLinks"));

                return new BookData(name, author, imageUrl);
            }
        }

        // If no relevant data is found, return a default BookData object
        return new BookData("Unknown Title", "Unknown Author", "");
    }

    private static String getAuthors(JSONArray authorsArray) {
        if (authorsArray != null && authorsArray.length() > 0) {
            List<String> authors = new ArrayList<>();
            for (int i = 0; i < authorsArray.length(); i++) {
                authors.add(authorsArray.getString(i));
            }
            return String.join(", ", authors);
        } else {
            return "Unknown Author";
        }
    }

    private static String getThumbnailUrl(JSONObject imageLinks) {
        return imageLinks != null ? imageLinks.optString("thumbnail", "") : "";
    }
}
