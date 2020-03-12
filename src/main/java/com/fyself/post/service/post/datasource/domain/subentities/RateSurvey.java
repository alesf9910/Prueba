package com.fyself.post.service.post.datasource.domain.subentities;


import java.util.Map;
import java.util.Set;

public class RateSurvey extends SurveyContent {

    private Set<Map<String, Integer>> limits;

    public Set<Map<String, Integer>> getLimits() {
        return limits;
    }

    public void setLimits(Set<Map<String, Integer>> limits) {
        this.limits = limits;
    }
}
