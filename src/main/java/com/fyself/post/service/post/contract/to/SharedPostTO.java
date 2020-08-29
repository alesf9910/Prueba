package com.fyself.post.service.post.contract.to;

public class SharedPostTO extends ContentTO{
    private static final long serialVersionUID = -1073487522607459128L;
    private String post;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }
}
