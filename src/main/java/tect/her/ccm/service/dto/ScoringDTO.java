package tect.her.ccm.service.dto;

import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link tect.her.ccm.domain.Scoring} entity.
 */
@ApiModel(description = "Model CCM-BANK")
public class ScoringDTO implements Serializable {
    private Long id;

    @NotNull
    private String label;

    @NotNull
    @Min(value = 0)
    private Integer score;

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

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ScoringDTO)) {
            return false;
        }

        return id != null && id.equals(((ScoringDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ScoringDTO{" +
            "id=" + getId() +
            ", label='" + getLabel() + "'" +
            ", score=" + getScore() +
            "}";
    }
}
