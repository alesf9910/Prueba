package com.fyself.post.service.post.contract.to;


import javax.validation.constraints.NotEmpty;
import java.util.Set;

public class ChoiceSurveyTO extends SurveyContentTO {
    private static final long serialVersionUID = 8652078821989507492L;
    private Set<String> choices;

    @NotEmpty
    public Set<String> getChoices() {
        return choices;
    }

    public void setChoices(Set<String> choices) {
        this.choices = choices;
    }
}
