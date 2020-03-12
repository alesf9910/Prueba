package com.fyself.post.service.post.contract.to;


import javax.validation.constraints.NotEmpty;
import java.util.Map;
import java.util.Set;

public class RateSurveyTO extends SurveyContentTO {
    private static final long serialVersionUID = -2973679384254864055L;
    private Set<Map<String, Integer>> limits;

    @NotEmpty
    public Set<Map<String, Integer>> getLimits() {
        return limits;
    }

    public void setLimits(Set<Map<String, Integer>> limits) {
        this.limits = limits;
    }
}
