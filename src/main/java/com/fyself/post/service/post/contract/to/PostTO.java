package com.fyself.post.service.post.contract.to;


import com.fyself.seedwork.service.to.TransferObject;

import java.util.Set;

public class PostTO extends TransferObject {

    private static final long serialVersionUID = -1060990002867022621L;
    private Set<ContentTO> contents;

    public Set<ContentTO> getContents() {
        return contents;
    }

    public void setContents(Set<ContentTO> contents) {
        this.contents = contents;
    }
}
