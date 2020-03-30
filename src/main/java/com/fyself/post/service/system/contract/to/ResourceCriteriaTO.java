package com.fyself.post.service.system.contract.to;


import com.fyself.seedwork.service.to.TransferObject;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <b>T</b>ransfer <b>O</b>bject that represents a search criteria for the resources the system handles.
 *
 * @author Yero
 * @since 0.2.0
 */
public class ResourceCriteriaTO extends TransferObject {
    private static final long serialVersionUID = -3770957766547124589L;
    private String type;
    private String name;

    public ResourceCriteriaTO() {}

    static public ResourceCriteriaTO from(String type) {
        var criteria = new ResourceCriteriaTO();
        criteria.setType(type);
        return criteria;
    }

    //<editor-fold desc="Fluent Encapsulation">
    public ResourceCriteriaTO withName(String name) {
        this.setName(name);
        return this;
    }
    //</editor-fold>

    //<editor-fold desc="Encapsulation">
    @NotNull
    public String getType() {
        return type;
    }


    public void setType(String type) {
        this.type = type;
    }

    @NotBlank
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    //</editor-fold>

    public String getFolder() {
        return type;
    }
}
