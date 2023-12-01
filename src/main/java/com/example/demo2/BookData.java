package com.example.demo2;

public class BookData {
    private String name;
    private String author;
    private String imageUrl;

    public BookData(String name, String author, String imageUrl) {
        this.name = name;
        this.author = author;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
