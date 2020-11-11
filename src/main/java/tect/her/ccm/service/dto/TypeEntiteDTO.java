package tect.her.ccm.service.dto;

import java.io.Serializable;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link tect.her.ccm.domain.TypeEntite} entity.
 */
public class TypeEntiteDTO implements Serializable {
    private Long id;

    @NotNull
    @Size(max = 32)
    private String identifier;

    @NotNull
    private String label;

    @Size(max = 50)
    private String shortLabel;

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

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getShortLabel() {
        return shortLabel;
    }

    public void setShortLabel(String shortLabel) {
        this.shortLabel = shortLabel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TypeEntiteDTO)) {
            return false;
        }

        return id != null && id.equals(((TypeEntiteDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TypeEntiteDTO{" +
            "id=" + getId() +
            ", identifier='" + getIdentifier() + "'" +
            ", label='" + getLabel() + "'" +
            ", shortLabel='" + getShortLabel() + "'" +
            "}";
    }
}
