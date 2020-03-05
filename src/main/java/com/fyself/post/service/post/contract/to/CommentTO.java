package com.fyself.post.service.post.contract.to;


import com.fyself.seedwork.service.to.TransferObject;

public class CommentTO extends TransferObject {
    private static final long serialVersionUID = -853413377748237564L;

    private PostTO post;
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public PostTO getPost() {
        return post;
    }

    public void setPost(PostTO post) {
        this.post = post;
    }
}
