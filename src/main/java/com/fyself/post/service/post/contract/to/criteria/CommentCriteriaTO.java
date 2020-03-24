package com.fyself.post.service.post.contract.to.criteria;

import com.fyself.seedwork.service.to.CriteriaTO;

public class CommentCriteriaTO extends CriteriaTO {
    private static final long serialVersionUID = 2138765270323056960L;

    private String father;

    public String getFather() {
        return father;
    }

    public void setFather(String father) {
        this.father = father;
    }
}
