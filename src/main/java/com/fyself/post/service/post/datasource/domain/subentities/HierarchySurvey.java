package com.fyself.post.service.post.datasource.domain.subentities;


import java.util.Set;

public class HierarchySurvey extends SurveyContent {

    private Set<String> options;

    public Set<String> getOptions() {
        return options;
    }

    public void setOptions(Set<String> options) {
        this.options = options;
    }
}
