package com.fyself.post.service.post.contract.to;


import com.fyself.seedwork.service.to.TransferObject;
import com.fyself.seedwork.service.to.annotation.ReadOnly;

import java.util.Set;

public class PostShareBulkTO extends TransferObject {

    private static final long serialVersionUID = -4274198442867743149L;
    private String post;
    private Set<String> sharedWith;

    @ReadOnly
    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public Set<String> getSharedWith() {
        return sharedWith;
    }

    public void setSharedWith(Set<String> sharedWith) {
        this.sharedWith = sharedWith;
    }

    public PostShareBulkTO withId(String id) {
        this.post = id;
        return this;
    }
}
