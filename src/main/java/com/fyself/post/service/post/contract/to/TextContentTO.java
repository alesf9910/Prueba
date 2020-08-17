package com.fyself.post.service.post.contract.to;


public class TextContentTO extends ContentTO{

    private static final long serialVersionUID = -1073487522607459128L;
    private String title;
    private String description;
    private boolean background;
    private String backgroundColor;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isBackground() {
        return background;
    }

    public void setBackground(boolean background) {
        this.background = background;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
}
