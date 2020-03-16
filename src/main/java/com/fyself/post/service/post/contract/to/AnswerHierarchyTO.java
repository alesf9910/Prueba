package com.fyself.post.service.post.contract.to;


import javax.validation.constraints.NotEmpty;
import java.util.Map;

public class AnswerHierarchyTO extends AnswerTO {
    private static final long serialVersionUID = -7136479131330887477L;
    private Map<String, Integer> answer;

    @NotEmpty
    public Map<String, Integer> getAnswer() {
        return answer;
    }

    public void setAnswer(Map<String, Integer> answer) {
        this.answer = answer;
    }
}
