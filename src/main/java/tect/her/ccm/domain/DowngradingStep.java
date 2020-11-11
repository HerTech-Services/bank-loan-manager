package tect.her.ccm.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DowngradingStep.
 */
@Entity
@Table(name = "downgrading_step")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DowngradingStep implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "label", nullable = false)
    private String label;

    @Size(max = 32)
    @Column(name = "identifier", length = 32, unique = true)
    private String identifier;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "downgradingSteps", allowSetters = true)
    private Scoring scoring;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public DowngradingStep label(String label) {
        this.label = label;
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getIdentifier() {
        return identifier;
    }

    public DowngradingStep identifier(String identifier) {
        this.identifier = identifier;
        return this;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Scoring getScoring() {
        return scoring;
    }

    public DowngradingStep scoring(Scoring scoring) {
        this.scoring = scoring;
        return this;
    }

    public void setScoring(Scoring scoring) {
        this.scoring = scoring;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DowngradingStep)) {
            return false;
        }
        return id != null && id.equals(((DowngradingStep) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DowngradingStep{" +
            "id=" + getId() +
            ", label='" + getLabel() + "'" +
            ", identifier='" + getIdentifier() + "'" +
            "}";
    }
}
