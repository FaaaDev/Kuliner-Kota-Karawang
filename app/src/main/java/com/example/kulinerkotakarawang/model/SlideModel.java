package com.example.kulinerkotakarawang.model;

public class SlideModel {
    String id, link;


    public SlideModel() {

    }

    public SlideModel(String link) {
        this.link = link;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
