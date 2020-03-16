package com.fyself.post.service.post.datasource.domain.subentities;


import java.util.Map;

public class TextContent extends Content{

    private Map<String,String> title;
    private Map<String,String> description;

    public Map<String,String> getTitle() {
        return title;
    }

    public void setTitle(Map<String,String> title) {
        this.title = title;
    }

    public Map<String,String> getDescription() {
        return description;
    }

    public void setDescription(Map<String,String> description) {
        this.description = description;
    }
}
