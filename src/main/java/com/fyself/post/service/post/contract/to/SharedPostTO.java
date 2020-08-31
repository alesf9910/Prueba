package com.fyself.post.service.post.contract.to;

public class SharedPostTO extends ContentTO{
    private static final long serialVersionUID = -1073487522607459128L;
    private PostTO postTo;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public PostTO getPostTo() {
        return postTo;
    }

    public void setPostTo(PostTO postTo) {
        this.postTo = postTo;
    }
}
