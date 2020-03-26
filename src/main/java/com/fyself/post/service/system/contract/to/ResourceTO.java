package com.fyself.post.service.system.contract.to;

import com.fyself.seedwork.service.to.TransferObject;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.nio.ByteBuffer;

/**
 * <b>T</b>ransfer <b>O</b>bject that represents a simple view of the resources (files) the system handles.
 *
 * @author Yero
 * @since 0.2.0
 */
public class ResourceTO extends TransferObject {
    private static final long serialVersionUID = 6683997003660456109L;
    private ResourceCriteriaTO criteria;
    private ByteBuffer content;

    public ResourceTO() {}

    static public ResourceTO of(ResourceCriteriaTO criteria, ByteBuffer content) {
        var resource = new ResourceTO();
        resource.setCriteria(criteria);
        resource.setContent(content);
        return resource;
    }

    //<editor-fold desc="Encapsulation">
    @NotNull
    @Valid
    public ResourceCriteriaTO getCriteria() {
        return criteria;
    }

    public void setCriteria(ResourceCriteriaTO criteria) {
        this.criteria = criteria;
    }

    @NotNull
    public ByteBuffer getContent() {
        return content;
    }

    public void setContent(ByteBuffer content) {
        this.content = content;
    }
    //</editor-fold>
}
