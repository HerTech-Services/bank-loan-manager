package tect.her.ccm.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link tect.her.ccm.domain.Attachment} entity.
 */
public class AttachmentDTO implements Serializable {
    private Long id;

    private String label;

    @NotNull
    @Size(max = 32)
    private String format;

    private Instant createdDate;

    private Instant updatedDate;

    private Integer version;

    private String path;

    private String filename;

    private Integer filesize;

    private Instant validationDate;

    private Long engagementId;

    private String engagementSubject;

    private Long userId;

    private String userFirstName;

    private Long updatedById;

    private String updatedByFirstName;

    private Long statusId;

    private String statusLabel;
    private Set<AttachmentDTO> origins = new HashSet<>();

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

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Integer getFilesize() {
        return filesize;
    }

    public void setFilesize(Integer filesize) {
        this.filesize = filesize;
    }

    public Instant getValidationDate() {
        return validationDate;
    }

    public void setValidationDate(Instant validationDate) {
        this.validationDate = validationDate;
    }

    public Long getEngagementId() {
        return engagementId;
    }

    public void setEngagementId(Long engagementId) {
        this.engagementId = engagementId;
    }

    public String getEngagementSubject() {
        return engagementSubject;
    }

    public void setEngagementSubject(String engagementSubject) {
        this.engagementSubject = engagementSubject;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public Long getUpdatedById() {
        return updatedById;
    }

    public void setUpdatedById(Long userId) {
        this.updatedById = userId;
    }

    public String getUpdatedByFirstName() {
        return updatedByFirstName;
    }

    public void setUpdatedByFirstName(String userFirstName) {
        this.updatedByFirstName = userFirstName;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public String getStatusLabel() {
        return statusLabel;
    }

    public void setStatusLabel(String statusLabel) {
        this.statusLabel = statusLabel;
    }

    public Set<AttachmentDTO> getOrigins() {
        return origins;
    }

    public void setOrigins(Set<AttachmentDTO> attachments) {
        this.origins = attachments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AttachmentDTO)) {
            return false;
        }

        return id != null && id.equals(((AttachmentDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AttachmentDTO{" +
            "id=" + getId() +
            ", label='" + getLabel() + "'" +
            ", format='" + getFormat() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            ", version=" + getVersion() +
            ", path='" + getPath() + "'" +
            ", filename='" + getFilename() + "'" +
            ", filesize=" + getFilesize() +
            ", validationDate='" + getValidationDate() + "'" +
            ", engagementId=" + getEngagementId() +
            ", engagementSubject='" + getEngagementSubject() + "'" +
            ", userId=" + getUserId() +
            ", userFirstName='" + getUserFirstName() + "'" +
            ", updatedById=" + getUpdatedById() +
            ", updatedByFirstName='" + getUpdatedByFirstName() + "'" +
            ", statusId=" + getStatusId() +
            ", statusLabel='" + getStatusLabel() + "'" +
            ", origins='" + getOrigins() + "'" +
            "}";
    }
}
