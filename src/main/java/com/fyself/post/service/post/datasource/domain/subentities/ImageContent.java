package com.fyself.post.service.post.datasource.domain.subentities;


import java.util.Map;

public class ImageContent extends Content{

    private Map<String,String> title;
    private String url;

    public Map<String,String> getTitle() {
        return title;
    }

    public void setTitle(Map<String,String> title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
