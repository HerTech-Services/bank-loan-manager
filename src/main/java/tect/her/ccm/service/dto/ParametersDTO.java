package tect.her.ccm.service.dto;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link tect.her.ccm.domain.Parameters} entity.
 */
public class ParametersDTO implements Serializable {
    private Long id;

    @NotNull
    @Size(max = 50)
    private String identifier;

    private String description;

    private String paramValueString;

    private Integer paramValueInt;

    private Instant paramValueDate;

    @Lob
    private String paramValueJson;

    private Instant updatedDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getParamValueString() {
        return paramValueString;
    }

    public void setParamValueString(String paramValueString) {
        this.paramValueString = paramValueString;
    }

    public Integer getParamValueInt() {
        return paramValueInt;
    }

    public void setParamValueInt(Integer paramValueInt) {
        this.paramValueInt = paramValueInt;
    }

    public Instant getParamValueDate() {
        return paramValueDate;
    }

    public void setParamValueDate(Instant paramValueDate) {
        this.paramValueDate = paramValueDate;
    }

    public String getParamValueJson() {
        return paramValueJson;
    }

    public void setParamValueJson(String paramValueJson) {
        this.paramValueJson = paramValueJson;
    }

    public Instant getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ParametersDTO)) {
            return false;
        }

        return id != null && id.equals(((ParametersDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ParametersDTO{" +
            "id=" + getId() +
            ", identifier='" + getIdentifier() + "'" +
            ", description='" + getDescription() + "'" +
            ", paramValueString='" + getParamValueString() + "'" +
            ", paramValueInt=" + getParamValueInt() +
            ", paramValueDate='" + getParamValueDate() + "'" +
            ", paramValueJson='" + getParamValueJson() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            "}";
    }
}
