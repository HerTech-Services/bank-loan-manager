package tect.her.ccm.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link tect.her.ccm.domain.TasktypeStatusAction} entity.
 */
public class TasktypeStatusActionDTO implements Serializable {
    private Long id;

    private Long tasktypeId;

    private String tasktypeLabel;

    private Long actionId;

    private String actionLabel;

    private Long statusId;

    private String statusLabel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTasktypeId() {
        return tasktypeId;
    }

    public void setTasktypeId(Long taskTypeId) {
        this.tasktypeId = taskTypeId;
    }

    public String getTasktypeLabel() {
        return tasktypeLabel;
    }

    public void setTasktypeLabel(String taskTypeLabel) {
        this.tasktypeLabel = taskTypeLabel;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TasktypeStatusActionDTO)) {
            return false;
        }

        return id != null && id.equals(((TasktypeStatusActionDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TasktypeStatusActionDTO{" +
            "id=" + getId() +
            ", tasktypeId=" + getTasktypeId() +
            ", tasktypeLabel='" + getTasktypeLabel() + "'" +
            ", actionId=" + getActionId() +
            ", actionLabel='" + getActionLabel() + "'" +
            ", statusId=" + getStatusId() +
            ", statusLabel='" + getStatusLabel() + "'" +
            "}";
    }
}
