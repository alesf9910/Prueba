package com.fyself.post.service.system.contract.to;

import com.fyself.seedwork.service.to.TransferObject;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.nio.ByteBuffer;
import java.util.Map;

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
    private Map<String,String> meta;

    public ResourceTO() {}

    static public ResourceTO of(ResourceCriteriaTO criteria, ByteBuffer content, Map<String, String> metadata) {
        var resource = new ResourceTO();
        resource.setCriteria(criteria);
        resource.setContent(content);
        resource.setMeta(metadata);
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

    @NotNull
    public Map<String, String> getMeta() {
        return meta;
    }
    public void setMeta(Map<String, String> meta) {
        this.meta = meta;
    }
    //</editor-fold>
}
