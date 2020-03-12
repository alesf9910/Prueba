package com.fyself.post.service.post.contract.to;


import javax.validation.constraints.NotEmpty;
import java.util.Map;

public class AnswerChoiceTO extends AnswerTO {
    private static final long serialVersionUID = -6768015360211032033L;
    private Map<String, Boolean> answers;

    @NotEmpty
    public java.util.Map<String, Boolean> getAnswers() {
        return answers;
    }

    public void setAnswers(Map<String, Boolean> answers) {
        this.answers = answers;
    }
}
