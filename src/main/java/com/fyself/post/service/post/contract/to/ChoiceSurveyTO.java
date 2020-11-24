package com.fyself.post.service.post.contract.to;


import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class ChoiceSurveyTO extends SurveyContentTO {
    private static final long serialVersionUID = 8652078821989507492L;
    /*private Set<Map<String, Object>> choices;*/

    private List<Map<String, Object>> choices;

    @NotEmpty
    public List<Map<String, Object>> getChoices() {
        return choices;
    }

    public void setChoices(List<Map<String, Object>> choices) {
        this.choices = choices;
    }

    /*@NotEmpty
    public Set<Map<String, Object>> getChoices() {
        return choices;
    }

    public void setChoices(Set<Map<String, Object>> choices) {
        this.choices = choices;
    }*/

    public ChoiceSurveyTO generateChoicesIds() {
        this.setChoices(this.choices.stream()
                .map(choices -> {
                    choices.put("id", UUID.randomUUID().toString());
                    return choices;
                }).collect(Collectors.toList()));

        /*this.setChoices(this.choices.stream()
                .map(choice -> {
                    choice.put("id", UUID.randomUUID().toString());
                    return choice;
                }).collect(Collectors.toSet()));*/
        return this;
    }
}
