package com.fyself.post.service.post.contract.to;


public class ProfileContentTO extends ContentTO {
    private static final long serialVersionUID = 1442393430511046760L;
    private String profile;
    private String title;
    private String description;
    private Double fyCoins;

    public String getAward() {
        return profile;
    }

    public void setAward(String award) {
        this.profile = award;
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

    public Double getFyCoins() {
        return fyCoins;
    }

    public void setFyCoins(Double fyCoins) {
        this.fyCoins = fyCoins;
    }
}
