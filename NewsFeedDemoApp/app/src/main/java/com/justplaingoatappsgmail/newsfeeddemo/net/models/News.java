package com.justplaingoatappsgmail.newsfeeddemo.net.models;

public class News {

    private int id;
    private String category;
    private String image_url;
    private String title;
    private String description;
    private String source;

    public News(int id, String category, String image_url, String title, String description, String source) {
        this.id = id;
        this.category = category;
        this.image_url = image_url;
        this.title = title;
        this.description = description;
        this.source = source;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() {
        return image_url;
    }

    public void setImage(String image_url) {
        this.image_url = image_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

}
