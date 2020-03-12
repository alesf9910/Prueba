package com.fyself.post.service.post.datasource.domain.subentities;


import java.util.Set;

public class ChoiceSurvey extends SurveyContent {

    private Set<String> choices;

    public Set<String> getChoices() {
        return choices;
    }

    public void setChoices(Set<String> choices) {
        this.choices = choices;
    }
}
