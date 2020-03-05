package com.fyself.post.service.post.contract.to;


public class ImageContentTO extends ContentTO{

    private static final long serialVersionUID = -7966541316827990619L;
    private String title;
    private String url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
