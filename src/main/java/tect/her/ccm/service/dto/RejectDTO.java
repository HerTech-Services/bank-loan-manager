package tect.her.ccm.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link tect.her.ccm.domain.Reject} entity.
 */
public class RejectDTO implements Serializable {
    private Long id;

    @NotNull
    private Instant createdDate;

    @NotNull
    @DecimalMin(value = "0")
    private BigDecimal capital;

    @NotNull
    @DecimalMin(value = "0")
    private BigDecimal interests;

    @NotNull
    @DecimalMin(value = "0")
    private BigDecimal penalties;

    @NotNull
    @DecimalMin(value = "0")
    private BigDecimal accessories;

    @Lob
    private String description;

    private Long typeId;

    private String typeLabel;

    private Long engagementId;

    private String engagementSubject;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public BigDecimal getCapital() {
        return capital;
    }

    public void setCapital(BigDecimal capital) {
        this.capital = capital;
    }

    public BigDecimal getInterests() {
        return interests;
    }

    public void setInterests(BigDecimal interests) {
        this.interests = interests;
    }

    public BigDecimal getPenalties() {
        return penalties;
    }

    public void setPenalties(BigDecimal penalties) {
        this.penalties = penalties;
    }

    public BigDecimal getAccessories() {
        return accessories;
    }

    public void setAccessories(BigDecimal accessories) {
        this.accessories = accessories;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long rejectTypeId) {
        this.typeId = rejectTypeId;
    }

    public String getTypeLabel() {
        return typeLabel;
    }

    public void setTypeLabel(String rejectTypeLabel) {
        this.typeLabel = rejectTypeLabel;
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
        if (!(o instanceof RejectDTO)) {
            return false;
        }

        return id != null && id.equals(((RejectDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RejectDTO{" +
            "id=" + getId() +
            ", createdDate='" + getCreatedDate() + "'" +
            ", capital=" + getCapital() +
            ", interests=" + getInterests() +
            ", penalties=" + getPenalties() +
            ", accessories=" + getAccessories() +
            ", description='" + getDescription() + "'" +
            ", typeId=" + getTypeId() +
            ", typeLabel='" + getTypeLabel() + "'" +
            ", engagementId=" + getEngagementId() +
            ", engagementSubject='" + getEngagementSubject() + "'" +
            "}";
    }
}
