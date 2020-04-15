package com.fyself.post.service.system.contract.to;

import com.fyself.seedwork.service.to.DomainAuditTransferObject;

public class ContactTO extends DomainAuditTransferObject {
    private static final long serialVersionUID = 6516962416920801754L;
    /**
     * ID of contact
     */
    private String contact;

    /**
     * Profile of user
     */
    private UserTO contactProfile;

    private Boolean pending;

    private Boolean reference;


    //<editor-fold desc="Attributes">
    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Boolean getPending() {
        return pending;
    }

    public void setPending(Boolean pending) {
        this.pending = pending;
    }

    public Boolean getReference() {
        return reference;
    }

    public void setReference(Boolean reference) {
        this.reference = reference;
    }

    public UserTO getContactProfile() {
        return contactProfile;
    }

    public void setContactProfile(UserTO contactProfile) {
        this.contactProfile = contactProfile;
    }
    //</editor-fold desc="Attributes">
}
