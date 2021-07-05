package com.example.kulinerkotakarawang.model;

public class CityModel {
    private String city;
    private boolean selected = false;

    public CityModel(String city, boolean selected) {
        this.city = city;
        this.selected = selected;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
