package com.fyself.post.service.post.contract.to;


import javax.validation.constraints.NotEmpty;
import java.util.Set;

public class AnswerChoiceTO extends AnswerTO {
    private static final long serialVersionUID = -6768015360211032033L;
    private Set<String> answer;

    @NotEmpty
    public Set<String> getAnswer() {
        return answer;
    }

    public void setAnswer(Set<String> answer) {
        this.answer = answer;
    }
}
