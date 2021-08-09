package com.example.informational_notes;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class CardData implements Serializable {

    private String id;
    private String title;
    private String description;
    private int picture;
    private boolean like;

    public CardData() {
    }

    public CardData(String title, String description, int picture, boolean like) {
        this.title = title;
        this.description = description;
        this.picture = picture;
        this.like = like;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getPicture() {
        return picture;
    }

    public boolean isLike() {
        return like;
    }


}
