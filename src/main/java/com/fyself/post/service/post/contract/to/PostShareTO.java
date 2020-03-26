package com.fyself.post.service.post.contract.to;


import com.fyself.seedwork.service.to.TransferObject;

public class PostShareTO extends TransferObject {

    private static final long serialVersionUID = 6462147240462395494L;
    private String post;
    private String sharedWith;

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getSharedWith() {
        return sharedWith;
    }

    public void setSharedWith(String sharedWith) {
        this.sharedWith = sharedWith;
    }

    public PostShareTO withId(String id) {
        this.post = id;
        return this;
    }
}
