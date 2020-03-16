package com.fyself.post.service.post.datasource.domain.subentities;


import java.util.Map;
import java.util.Set;

public class HierarchySurvey extends SurveyContent {

    private Set<Map<String, Object>> options;

    public Set<Map<String, Object>> getOptions() {
        return options;
    }

    public void setOptions(Set<Map<String, Object>> options) {
        this.options = options;
    }
}
