package tect.her.ccm.service.dto;

import java.io.Serializable;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link tect.her.ccm.domain.DowngradingStep} entity.
 */
public class DowngradingStepDTO implements Serializable {
    private Long id;

    @NotNull
    private String label;

    @Size(max = 32)
    private String identifier;

    private Long scoringId;

    private String scoringLabel;

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

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Long getScoringId() {
        return scoringId;
    }

    public void setScoringId(Long scoringId) {
        this.scoringId = scoringId;
    }

    public String getScoringLabel() {
        return scoringLabel;
    }

    public void setScoringLabel(String scoringLabel) {
        this.scoringLabel = scoringLabel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DowngradingStepDTO)) {
            return false;
        }

        return id != null && id.equals(((DowngradingStepDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DowngradingStepDTO{" +
            "id=" + getId() +
            ", label='" + getLabel() + "'" +
            ", identifier='" + getIdentifier() + "'" +
            ", scoringId=" + getScoringId() +
            ", scoringLabel='" + getScoringLabel() + "'" +
            "}";
    }
}
