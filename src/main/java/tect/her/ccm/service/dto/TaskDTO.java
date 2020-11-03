package tect.her.ccm.service.dto;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link tect.her.ccm.domain.Task} entity.
 */
public class TaskDTO implements Serializable {
    private Long id;

    @NotNull
    @Min(value = 1)
    private Integer sequence;

    @NotNull
    private Boolean isSystem;

    @NotNull
    private Integer processDelay;

    @NotNull
    private Integer delay1;

    @NotNull
    private Integer delay2;

    private Integer viewed;

    private Instant createdDate;

    private Instant processDate;

    @Lob
    private String processComment;

    private Long typeId;

    private String typeLabel;

    private Long processUserId;

    private String processUserFirstName;

    private Long posteId;

    private String posteLabel;

    private Long engagementId;

    private String engagementSubject;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public Boolean isIsSystem() {
        return isSystem;
    }

    public void setIsSystem(Boolean isSystem) {
        this.isSystem = isSystem;
    }

    public Integer getProcessDelay() {
        return processDelay;
    }

    public void setProcessDelay(Integer processDelay) {
        this.processDelay = processDelay;
    }

    public Integer getDelay1() {
        return delay1;
    }

    public void setDelay1(Integer delay1) {
        this.delay1 = delay1;
    }

    public Integer getDelay2() {
        return delay2;
    }

    public void setDelay2(Integer delay2) {
        this.delay2 = delay2;
    }

    public Integer getViewed() {
        return viewed;
    }

    public void setViewed(Integer viewed) {
        this.viewed = viewed;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getProcessDate() {
        return processDate;
    }

    public void setProcessDate(Instant processDate) {
        this.processDate = processDate;
    }

    public String getProcessComment() {
        return processComment;
    }

    public void setProcessComment(String processComment) {
        this.processComment = processComment;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long taskTypeId) {
        this.typeId = taskTypeId;
    }

    public String getTypeLabel() {
        return typeLabel;
    }

    public void setTypeLabel(String taskTypeLabel) {
        this.typeLabel = taskTypeLabel;
    }

    public Long getProcessUserId() {
        return processUserId;
    }

    public void setProcessUserId(Long userId) {
        this.processUserId = userId;
    }

    public String getProcessUserFirstName() {
        return processUserFirstName;
    }

    public void setProcessUserFirstName(String userFirstName) {
        this.processUserFirstName = userFirstName;
    }

    public Long getPosteId() {
        return posteId;
    }

    public void setPosteId(Long posteId) {
        this.posteId = posteId;
    }

    public String getPosteLabel() {
        return posteLabel;
    }

    public void setPosteLabel(String posteLabel) {
        this.posteLabel = posteLabel;
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
        if (!(o instanceof TaskDTO)) {
            return false;
        }

        return id != null && id.equals(((TaskDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaskDTO{" +
            "id=" + getId() +
            ", sequence=" + getSequence() +
            ", isSystem='" + isIsSystem() + "'" +
            ", processDelay=" + getProcessDelay() +
            ", delay1=" + getDelay1() +
            ", delay2=" + getDelay2() +
            ", viewed=" + getViewed() +
            ", createdDate='" + getCreatedDate() + "'" +
            ", processDate='" + getProcessDate() + "'" +
            ", processComment='" + getProcessComment() + "'" +
            ", typeId=" + getTypeId() +
            ", typeLabel='" + getTypeLabel() + "'" +
            ", processUserId=" + getProcessUserId() +
            ", processUserFirstName='" + getProcessUserFirstName() + "'" +
            ", posteId=" + getPosteId() +
            ", posteLabel='" + getPosteLabel() + "'" +
            ", engagementId=" + getEngagementId() +
            ", engagementSubject='" + getEngagementSubject() + "'" +
            "}";
    }
}
