package com.fyself.post.service.post.datasource.domain.subentities;


import java.util.List;
import java.util.Map;
import java.util.Set;

public class HierarchySurvey extends SurveyContent {

    private List<Map<String, Object>> options;

    public List<Map<String, Object>> getOptions() {
        return options;
    }

    public void setOptions(List<Map<String, Object>> options) {
        this.options = options;
    }
}
