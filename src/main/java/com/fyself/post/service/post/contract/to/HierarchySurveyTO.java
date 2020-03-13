package com.fyself.post.service.post.contract.to;


import javax.validation.constraints.NotEmpty;
import java.util.Set;

public class HierarchySurveyTO extends SurveyContentTO {
    private static final long serialVersionUID = 5688252080662594286L;
    private Set<String> options;

    @NotEmpty
    public Set<String> getOptions() {
        return options;
    }

    public void setOptions(Set<String> options) {
        this.options = options;
    }
}
