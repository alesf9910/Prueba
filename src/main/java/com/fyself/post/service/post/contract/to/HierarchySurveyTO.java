package com.fyself.post.service.post.contract.to;


import javax.validation.constraints.NotEmpty;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class HierarchySurveyTO extends SurveyContentTO {
    private static final long serialVersionUID = 5688252080662594286L;
    private Set<Map<String, Object>> options;

    @NotEmpty
    public Set<Map<String, Object>> getOptions() {
        return options;
    }

    public void setOptions(Set<Map<String, Object>> options) {
        this.options = options;
    }

    public HierarchySurveyTO generateOptionsIds() {
        this.setOptions(this.options.stream()
                .map(option -> {
                    option.put("id", UUID.randomUUID().toString());
                    return option;
                }).collect(Collectors.toSet()));
        return this;
    }
}
