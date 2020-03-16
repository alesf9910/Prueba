package com.fyself.post.service.post.contract.to;


import java.util.Map;

public class TextContentTO extends ContentTO{

    private static final long serialVersionUID = -1073487522607459128L;
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
