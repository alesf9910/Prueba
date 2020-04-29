package com.fyself.post.service.stream.contract.to;

import com.fyself.seedwork.service.to.TransferObject;

/**
 * MessageTO
 *
 * @author jmmarin
 * @since 0.0.2
 */
public class MessageTO extends TransferObject {
    private static final long serialVersionUID = 8379711136577456695L;
    /**
     * Type of event
     */
    protected String type;
    /**
     * PayLoad
     */
    protected PayloadTO payload;
    /**
     * From
     */
    protected String from;
    /**
     * To
     */
    protected String to;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public PayloadTO getPayload() {
        return payload;
    }

    public void setPayload(PayloadTO payload) {
        this.payload = payload;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
