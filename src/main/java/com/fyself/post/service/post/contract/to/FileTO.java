package com.fyself.post.service.post.contract.to;

import java.io.Serializable;

public class FileTO implements Serializable {
    private static final long serialVersionUID = 9043563622743672733L;

    String file;

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
