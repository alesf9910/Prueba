package com.fyself.post.service.stream.contract.to;

import com.fyself.seedwork.service.to.TransferObject;

import java.util.HashMap;
import java.util.Map;

/**
 * PayloadTO
 *
 * @author jmmarin
 * @since 0.0.2
 */
public class PayloadTO extends TransferObject {
    private static final long serialVersionUID = 8888950539009223850L;
    private Map<String, Object> body = new HashMap<>();

    public Map<String, Object> getBody() {
        return body;
    }

    public void setBody(Map<String, Object> body) {
        this.body = body;
    }
}
