package tect.her.ccm.service.dto;

import java.io.Serializable;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link tect.her.ccm.domain.Status} entity.
 */
public class StatusDTO implements Serializable {
    private Long id;

    @NotNull
    @Size(max = 50)
    private String label;

    @NotNull
    private Boolean isSystem;

    private String imgFilename;

    @NotNull
    private Boolean canBeSearched;

    @NotNull
    private Boolean canBeModified;

    @NotNull
    private Boolean isEnabled;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Boolean isIsSystem() {
        return isSystem;
    }

    public void setIsSystem(Boolean isSystem) {
        this.isSystem = isSystem;
    }

    public String getImgFilename() {
        return imgFilename;
    }

    public void setImgFilename(String imgFilename) {
        this.imgFilename = imgFilename;
    }

    public Boolean isCanBeSearched() {
        return canBeSearched;
    }

    public void setCanBeSearched(Boolean canBeSearched) {
        this.canBeSearched = canBeSearched;
    }

    public Boolean isCanBeModified() {
        return canBeModified;
    }

    public void setCanBeModified(Boolean canBeModified) {
        this.canBeModified = canBeModified;
    }

    public Boolean isIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StatusDTO)) {
            return false;
        }

        return id != null && id.equals(((StatusDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StatusDTO{" +
            "id=" + getId() +
            ", label='" + getLabel() + "'" +
            ", isSystem='" + isIsSystem() + "'" +
            ", imgFilename='" + getImgFilename() + "'" +
            ", canBeSearched='" + isCanBeSearched() + "'" +
            ", canBeModified='" + isCanBeModified() + "'" +
            ", isEnabled='" + isIsEnabled() + "'" +
            "}";
    }
}
