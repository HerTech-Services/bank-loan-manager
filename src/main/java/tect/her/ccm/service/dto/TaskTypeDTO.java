package tect.her.ccm.service.dto;

import java.io.Serializable;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link tect.her.ccm.domain.TaskType} entity.
 */
public class TaskTypeDTO implements Serializable {
    private Long id;

    @NotNull
    private String label;

    @NotNull
    @Size(max = 50)
    private String identifier;

    private Integer delay;

    private Boolean isEnabled;

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

    public Integer getDelay() {
        return delay;
    }

    public void setDelay(Integer delay) {
        this.delay = delay;
    }

    public Boolean isIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TaskTypeDTO)) {
            return false;
        }

        return id != null && id.equals(((TaskTypeDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaskTypeDTO{" +
            "id=" + getId() +
            ", label='" + getLabel() + "'" +
            ", identifier='" + getIdentifier() + "'" +
            ", delay=" + getDelay() +
            ", isEnabled='" + isIsEnabled() + "'" +
            "}";
    }
}
