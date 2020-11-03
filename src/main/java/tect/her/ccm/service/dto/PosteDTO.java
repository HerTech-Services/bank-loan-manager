package tect.her.ccm.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link tect.her.ccm.domain.Poste} entity.
 */
public class PosteDTO implements Serializable {
    private Long id;

    @NotNull
    @Size(max = 32)
    private String code;

    @NotNull
    private String label;

    @Size(max = 50)
    private String shortLabel;

    private String entity;

    @NotNull
    private Boolean enabled;

    private String adrs1;

    private String adrs2;

    private String adrs3;

    private String city;

    private Set<PosteDTO> parentPostes = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Set<PosteDTO> getParentPostes() {
        return parentPostes;
    }

    public void setParentPostes(Set<PosteDTO> postes) {
        this.parentPostes = postes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PosteDTO)) {
            return false;
        }

        return id != null && id.equals(((PosteDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PosteDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", label='" + getLabel() + "'" +
            ", shortLabel='" + getShortLabel() + "'" +
            ", entity='" + getEntity() + "'" +
            ", enabled='" + isEnabled() + "'" +
            ", adrs1='" + getAdrs1() + "'" +
            ", adrs2='" + getAdrs2() + "'" +
            ", adrs3='" + getAdrs3() + "'" +
            ", city='" + getCity() + "'" +
            ", parentPostes='" + getParentPostes() + "'" +
            "}";
    }
}
