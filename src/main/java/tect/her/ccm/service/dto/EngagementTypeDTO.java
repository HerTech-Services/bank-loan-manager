package tect.her.ccm.service.dto;

import java.io.Serializable;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link tect.her.ccm.domain.EngagementType} entity.
 */
public class EngagementTypeDTO implements Serializable {
    private Long id;

    @NotNull
    private String label;

    @NotNull
    private Boolean isEnabled;

    @NotNull
    private Integer processDelay;

    @NotNull
    private Integer delay1;

    @NotNull
    private Integer delay2;

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

    public Boolean isIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EngagementTypeDTO)) {
            return false;
        }

        return id != null && id.equals(((EngagementTypeDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EngagementTypeDTO{" +
            "id=" + getId() +
            ", label='" + getLabel() + "'" +
            ", isEnabled='" + isIsEnabled() + "'" +
            ", processDelay=" + getProcessDelay() +
            ", delay1=" + getDelay1() +
            ", delay2=" + getDelay2() +
            "}";
    }
}
