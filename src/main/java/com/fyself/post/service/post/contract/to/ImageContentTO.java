package com.fyself.post.service.post.contract.to;


import java.util.Map;

public class ImageContentTO extends ContentTO{

    private static final long serialVersionUID = -7966541316827990619L;
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
