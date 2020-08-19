package com.fyself.post.service.post.contract.to;

import com.fyself.post.service.post.datasource.domain.enums.ReactionType;

public class ReactionResumeTO {
    private static final long serialVersionUID = -6218018823754241592L;

    private long total;
    private ReactionType reaction;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public ReactionType getReaction() {
        return reaction;
    }

    public void setReaction(ReactionType reaction) {
        this.reaction = reaction;
    }
}
