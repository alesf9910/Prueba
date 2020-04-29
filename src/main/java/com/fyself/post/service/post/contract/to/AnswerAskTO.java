package com.fyself.post.service.post.contract.to;


import javax.validation.constraints.NotBlank;

public class AnswerAskTO extends AnswerTO {
    private static final long serialVersionUID = 3132916442787780858L;
    private String answer;

    @NotBlank
    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
