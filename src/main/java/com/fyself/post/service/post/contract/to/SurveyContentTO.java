package com.fyself.post.service.post.contract.to;


import javax.validation.constraints.NotBlank;
import java.util.Map;

public class SurveyContentTO extends ContentTO{

    private static final long serialVersionUID = 2597014116630110418L;
    private Map<String,String> ask;

    @NotBlank
    public Map<String,String> getAsk() {
        return ask;
    }

    public void setAsk(Map<String,String> ask) {
        this.ask = ask;
    }
}
