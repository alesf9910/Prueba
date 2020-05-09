package com.fyself.post.service.stream.contract.to;

import java.util.HashMap;
import java.util.Map;

public class PayloadTO {

    private Map<String, Object> body = new HashMap<>();

    //<editor-fold desc="Encapsulation">
    public Map<String, Object> getBody() {
        return body;
    }
    public void setBody(Map<String, Object> body) {
        this.body = body;
    }
    //</editor-fold>

}
