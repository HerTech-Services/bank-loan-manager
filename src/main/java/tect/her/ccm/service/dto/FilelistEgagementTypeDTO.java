package tect.her.ccm.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link tect.her.ccm.domain.FilelistEgagementType} entity.
 */
public class FilelistEgagementTypeDTO implements Serializable {
    private Long id;

    private Boolean mandatory;

    private Long engagementTypeId;

    private String engagementTypeLabel;

    private Long filelistId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isMandatory() {
        return mandatory;
    }

    public void setMandatory(Boolean mandatory) {
        this.mandatory = mandatory;
    }

    public Long getEngagementTypeId() {
        return engagementTypeId;
    }

    public void setEngagementTypeId(Long engagementTypeId) {
        this.engagementTypeId = engagementTypeId;
    }

    public String getEngagementTypeLabel() {
        return engagementTypeLabel;
    }

    public void setEngagementTypeLabel(String engagementTypeLabel) {
        this.engagementTypeLabel = engagementTypeLabel;
    }

    public Long getFilelistId() {
        return filelistId;
    }

    public void setFilelistId(Long filelistId) {
        this.filelistId = filelistId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FilelistEgagementTypeDTO)) {
            return false;
        }

        return id != null && id.equals(((FilelistEgagementTypeDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FilelistEgagementTypeDTO{" +
            "id=" + getId() +
            ", mandatory='" + isMandatory() + "'" +
            ", engagementTypeId=" + getEngagementTypeId() +
            ", engagementTypeLabel='" + getEngagementTypeLabel() + "'" +
            ", filelistId=" + getFilelistId() +
            "}";
    }
}
