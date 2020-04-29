package com.fyself.post.service.post.datasource.domain.subentities;


import java.util.Map;

public class RateSurvey extends SurveyContent {

    private Map<String, Integer> limits;

    public Map<String, Integer> getLimits() {
        return limits;
    }

    public void setLimits(Map<String, Integer> limits) {
        this.limits = limits;
    }
}
