package com.fyself.post.service.post.contract.to;

import java.io.Serializable;

public class UrlTo implements Serializable {

    private static final long serialVersionUID = 9043563622743672733L;

    String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
