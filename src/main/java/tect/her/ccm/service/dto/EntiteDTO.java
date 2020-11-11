package tect.her.ccm.service.dto;

import java.io.Serializable;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link tect.her.ccm.domain.Entite} entity.
 */
public class EntiteDTO implements Serializable {
    private Long id;

    @NotNull
    @Size(max = 32)
    private String identifier;

    @NotNull
    private String label;

    @Size(max = 50)
    private String shortLabel;

    private Integer parent;

    @NotNull
    private Boolean isEnabled;

    private String adrs1;

    private String adrs2;

    private String adrs3;

    private String zipcode;

    private String city;

    private String country;

    private String email;

    @Lob
    private String parameters;

    @Size(max = 32)
    private String parentId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getShortLabel() {
        return shortLabel;
    }

    public void setShortLabel(String shortLabel) {
        this.shortLabel = shortLabel;
    }

    public Integer getParent() {
        return parent;
    }

    public void setParent(Integer parent) {
        this.parent = parent;
    }

    public Boolean isIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public String getAdrs1() {
        return adrs1;
    }

    public void setAdrs1(String adrs1) {
        this.adrs1 = adrs1;
    }

    public String getAdrs2() {
        return adrs2;
    }

    public void setAdrs2(String adrs2) {
        this.adrs2 = adrs2;
    }

    public String getAdrs3() {
        return adrs3;
    }

    public void setAdrs3(String adrs3) {
        this.adrs3 = adrs3;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EntiteDTO)) {
            return false;
        }

        return id != null && id.equals(((EntiteDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EntiteDTO{" +
            "id=" + getId() +
            ", identifier='" + getIdentifier() + "'" +
            ", label='" + getLabel() + "'" +
            ", shortLabel='" + getShortLabel() + "'" +
            ", parent=" + getParent() +
            ", isEnabled='" + isIsEnabled() + "'" +
            ", adrs1='" + getAdrs1() + "'" +
            ", adrs2='" + getAdrs2() + "'" +
            ", adrs3='" + getAdrs3() + "'" +
            ", zipcode='" + getZipcode() + "'" +
            ", city='" + getCity() + "'" +
            ", country='" + getCountry() + "'" +
            ", email='" + getEmail() + "'" +
            ", parameters='" + getParameters() + "'" +
            ", parentId='" + getParentId() + "'" +
            "}";
    }
}
