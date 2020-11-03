package tect.her.ccm.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link tect.her.ccm.domain.Downgrading} entity.
 */
public class DowngradingDTO implements Serializable {
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

    private Long stepId;

    private String stepLabel;

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

    public Long getStepId() {
        return stepId;
    }

    public void setStepId(Long downgradingStepId) {
        this.stepId = downgradingStepId;
    }

    public String getStepLabel() {
        return stepLabel;
    }

    public void setStepLabel(String downgradingStepLabel) {
        this.stepLabel = downgradingStepLabel;
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
        if (!(o instanceof DowngradingDTO)) {
            return false;
        }

        return id != null && id.equals(((DowngradingDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DowngradingDTO{" +
            "id=" + getId() +
            ", createdDate='" + getCreatedDate() + "'" +
            ", capital=" + getCapital() +
            ", interests=" + getInterests() +
            ", penalties=" + getPenalties() +
            ", accessories=" + getAccessories() +
            ", description='" + getDescription() + "'" +
            ", stepId=" + getStepId() +
            ", stepLabel='" + getStepLabel() + "'" +
            ", engagementId=" + getEngagementId() +
            ", engagementSubject='" + getEngagementSubject() + "'" +
            "}";
    }
}
