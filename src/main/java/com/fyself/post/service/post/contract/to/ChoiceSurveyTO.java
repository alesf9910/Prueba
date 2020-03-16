package com.fyself.post.service.post.contract.to;


import javax.validation.constraints.NotEmpty;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class ChoiceSurveyTO extends SurveyContentTO {
    private static final long serialVersionUID = 8652078821989507492L;
    private Set<Map<String, Object>> choices;

    @NotEmpty
    public Set<Map<String, Object>> getChoices() {
        return choices;
    }

    public void setChoices(Set<Map<String, Object>> choices) {
        this.choices = choices;
    }

    public ChoiceSurveyTO generateChoicesIds() {
        AtomicInteger index = new AtomicInteger();
        this.setChoices(this.choices.stream()
                .map(choice -> {
                    choice.put("id", index.getAndIncrement());
                    return choice;
                }).collect(Collectors.toSet()));
        return this;
    }
}
