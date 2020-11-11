package tect.her.ccm.service.dto;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link tect.her.ccm.domain.Notes} entity.
 */
public class NotesDTO implements Serializable {
    private Long id;

    @Lob
    private String description;

    private Instant createdDate;

    private Long userId;

    private String userFirstName;

    private Long engagementId;

    private String engagementSubject;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NotesDTO)) {
            return false;
        }

        return id != null && id.equals(((NotesDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NotesDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", userId=" + getUserId() +
            ", userFirstName='" + getUserFirstName() + "'" +
            ", engagementId=" + getEngagementId() +
            ", engagementSubject='" + getEngagementSubject() + "'" +
            "}";
    }
}
