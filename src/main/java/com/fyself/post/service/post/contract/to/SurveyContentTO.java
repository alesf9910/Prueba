package com.fyself.post.service.post.contract.to;


import javax.validation.constraints.NotBlank;

public class SurveyContentTO extends ContentTO{

    private static final long serialVersionUID = 2597014116630110418L;
    private String ask;

    @NotBlank
    public String getAsk() {
        return ask;
    }

    public void setAsk(String ask) {
        this.ask = ask;
    }
}
