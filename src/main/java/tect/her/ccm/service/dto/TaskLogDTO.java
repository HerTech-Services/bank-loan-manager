package tect.her.ccm.service.dto;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.Lob;

/**
 * A DTO for the {@link tect.her.ccm.domain.TaskLog} entity.
 */
public class TaskLogDTO implements Serializable {
    private Long id;

    private String comment;

    private Instant createdDate;

    @Lob
    private String taskProperties;

    @Lob
    private String engagementProperties;

    private Long userId;

    private String userFirstName;

    private Long actionId;

    private String actionLabel;

    private Long taskId;

    private Long engagementId;

    private String engagementSubject;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getTaskProperties() {
        return taskProperties;
    }

    public void setTaskProperties(String taskProperties) {
        this.taskProperties = taskProperties;
    }

    public String getEngagementProperties() {
        return engagementProperties;
    }

    public void setEngagementProperties(String engagementProperties) {
        this.engagementProperties = engagementProperties;
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

    public Long getActionId() {
        return actionId;
    }

    public void setActionId(Long actionId) {
        this.actionId = actionId;
    }

    public String getActionLabel() {
        return actionLabel;
    }

    public void setActionLabel(String actionLabel) {
        this.actionLabel = actionLabel;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
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
        if (!(o instanceof TaskLogDTO)) {
            return false;
        }

        return id != null && id.equals(((TaskLogDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaskLogDTO{" +
            "id=" + getId() +
            ", comment='" + getComment() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", taskProperties='" + getTaskProperties() + "'" +
            ", engagementProperties='" + getEngagementProperties() + "'" +
            ", userId=" + getUserId() +
            ", userFirstName='" + getUserFirstName() + "'" +
            ", actionId=" + getActionId() +
            ", actionLabel='" + getActionLabel() + "'" +
            ", taskId=" + getTaskId() +
            ", engagementId=" + getEngagementId() +
            ", engagementSubject='" + getEngagementSubject() + "'" +
            "}";
    }
}
