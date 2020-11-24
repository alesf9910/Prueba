package com.fyself.post.service.post.datasource.domain.subentities;


import java.util.List;
import java.util.Map;
import java.util.Set;

public class ChoiceSurvey extends SurveyContent {

    /*private Set<Map<String, Object>> choices;*/

    private List<Map<String, Object>> choices;

    /*public Set<Map<String, Object>> getChoices() {
        return choices;
    }

    public void setChoices(Set<Map<String, Object>> choices) {
        this.choices = choices;
    }*/

    public List<Map<String, Object>> getChoices() {
        return choices;
    }

    public void setChoices(List<Map<String, Object>> choices) {
        this.choices = choices;
    }
}
