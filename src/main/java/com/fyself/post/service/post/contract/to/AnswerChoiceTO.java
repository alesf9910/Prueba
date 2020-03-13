package com.fyself.post.service.post.contract.to;


import javax.validation.constraints.NotEmpty;
import java.util.Map;

public class AnswerChoiceTO extends AnswerTO {
    private static final long serialVersionUID = -6768015360211032033L;
    private Map<String, Boolean> answer;

    @NotEmpty
    public java.util.Map<String, Boolean> getAnswer() {
        return answer;
    }

    public void setAnswer(Map<String, Boolean> answer) {
        this.answer = answer;
    }
}
