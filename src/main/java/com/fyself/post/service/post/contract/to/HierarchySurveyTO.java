package com.fyself.post.service.post.contract.to;


import javax.validation.constraints.NotEmpty;
import java.util.Set;

public class HierarchySurveyTO extends SurveyContentTO {
    private static final long serialVersionUID = 5688252080662594286L;
    private Set<String> choices;

    @NotEmpty
    public Set<String> getChoices() {
        return choices;
    }

    public void setChoices(Set<String> choices) {
        this.choices = choices;
    }
}
