package com.fyself.post.service.post.datasource.domain.subentities;


public class AwardContent extends Content{
    private String award;
    private String title;
    private String description;
    private Double fyCoins;

    public String getAward() {
        return award;
    }

    public void setAward(String award) {
        this.award = award;
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
