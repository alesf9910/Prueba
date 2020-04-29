package com.fyself.post.service.post.contract.to;


import javax.validation.constraints.NotBlank;

public class AnswerRateTO extends AnswerTO {
    private static final long serialVersionUID = -4344616978263673051L;
    private Integer answer;

    @NotBlank
    public Integer getAnswer() {
        return answer;
    }

    public void setAnswer(Integer answer) {
        this.answer = answer;
    }
}
