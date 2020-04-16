package com.fyself.post.service.post.contract.to;


import com.fyself.seedwork.service.to.annotation.ReadOnly;

import javax.validation.constraints.NotBlank;

public class SurveyContentTO extends ContentTO {

    private static final long serialVersionUID = 2597014116630110418L;
    private String ask;
    private AnswerSurveyTO answer;

    @NotBlank
    public String getAsk() {
        return ask;
    }

    public void setAsk(String ask) {
        this.ask = ask;
    }

    @ReadOnly
    public AnswerSurveyTO getAnswer() {
        return answer;
    }

    public void setAnswer(AnswerSurveyTO answer) {
        this.answer = answer;
    }
}
