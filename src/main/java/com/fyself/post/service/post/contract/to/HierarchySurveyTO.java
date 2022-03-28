package com.fyself.post.service.post.contract.to;


import javax.validation.constraints.NotEmpty;
import java.util.*;
import java.util.stream.Collectors;

public class HierarchySurveyTO extends SurveyContentTO {
    private static final long serialVersionUID = 5688252080662594286L;
    private List<Map<String, Object>> options;

    @NotEmpty
    public List<Map<String, Object>> getOptions() {
        return options;
    }

    public void setOptions(List<Map<String, Object>> options) {
        this.options = options;
    }

    public HierarchySurveyTO generateOptionsIds() {
        List<Map<String, Object>> result = this.options.stream()
                .map(option -> {
                    option.put("id", UUID.randomUUID().toString());
                    return option;
                })
                .collect(Collectors.toList());
        this.setOptions(result);
        return this;
    }
}
