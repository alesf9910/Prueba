package com.fyself.post.service.stream.contract.to;

import com.fyself.seedwork.service.to.TransferObject;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

/**
 * Generic Notification Message
 * @author Yero
 * @since 0.1.0
 */
public class MessageTO extends TransferObject {

    /**
     * Id
     */
    protected String id;
    /**
     * Type of event
     */
    protected String type;
    /**
     * PayLoad
     */
    protected PayloadTO payload;
    /**
     * Instant
     */
    protected LocalDateTime date;

    /**
     * From
     */
    protected String from;

    /**
     * To
     */
    protected String to;

    /**
     * Read
     */
    protected boolean read;

    /**
     * Read
     */
    protected boolean todb;


    /**
     * Read
     */
    protected String fix;

    //<editor-fold desc="Encapsulation">


    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
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

    public boolean isRead() {
        return read;
    }
    public void setRead(boolean read) {
        this.read = read;
    }

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

    public LocalDateTime getDate() {
        return date;
    }
    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public MessageTO withCreatedAt() {
        setDate(now());
        return this;
    }

    public boolean isTodb() {
        return todb;
    }
    public void setTodb(boolean todb) {
        this.todb = todb;
    }

    public String getFix() {
        return fix;
    }
    public void setFix(String fix) {
        this.fix = fix;
    }
    //</editor-fold>

}
