package com.fyself.post.service.system.contract.to;
import com.fyself.seedwork.service.to.DomainAuditTransferObject;
import javax.validation.constraints.NotBlank;
import static java.time.LocalDateTime.now;

public class EnterpriseTO extends DomainAuditTransferObject {

    private String name;
    private String url_logo;
    private String url;
    private String description;
    private String location;
    private boolean tenant;
    private String country;
    private String phone;
    private String email;
    private Integer cantSubscribers;
    private boolean isSubscribed;
    private int weight;


    public EnterpriseTO withId(String id) {
        this.setId(id);
        return this;
    }
    public EnterpriseTO withUserId(String id) {
        this.setOwner(id);
        return this;
    }
    public EnterpriseTO withCreatedAt() {
        this.setCreatedAt(now());
        return this;
    }
    public EnterpriseTO withUpdatedAt() {
        this.setUpdatedAt(now());
        return this;
    }
    public EnterpriseTO withOwner(String owner) {
        this.setOwner(owner);
        return this;
    }
    public EnterpriseTO withLocation(String location) {
        setLocation(location);
        return this;
    }
    public EnterpriseTO whitDescription(String description) {
        setDescription(description);
        return this;
    }
    public EnterpriseTO withLogo(String logo) {
        setUrl_logo(logo);
        return this;
    }
    public EnterpriseTO withName(String name) {
        setName(name);
        return this;
    }
    public Integer getCantSubscribers() {
        return cantSubscribers;
    }
    public EnterpriseTO putCantSubscribers(Integer cantSubscribers) {
        this.cantSubscribers = cantSubscribers;
        return this;
    }
    public boolean isUserSubscribed() {
        return isSubscribed;
    }
    public EnterpriseTO putUserSubscribed(boolean isSubscribed) {
        this.isSubscribed = isSubscribed;
        return this;
    }

    @NotBlank
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getUrl_logo() {
        return url_logo;
    }
    public void setUrl_logo(String url_logo) {
        this.url_logo = url_logo;
    }

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isTenant() {
        return tenant;
    }
    public void setTenant(boolean tenant) {
        this.tenant = tenant;
    }

    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}

