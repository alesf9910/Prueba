package com.fyself.post.service.post.contract.to;


import javax.validation.constraints.NotEmpty;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
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
        AtomicInteger index = new AtomicInteger();
        this.setOptions(this.options.stream()
                .map(option -> {
                    option.put("id", index.getAndIncrement());
                    return option;
                }).collect(Collectors.toSet()));
        return this;
    }
}
