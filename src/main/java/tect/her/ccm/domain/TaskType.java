package tect.her.ccm.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TaskType.
 */
@Entity
@Table(name = "task_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TaskType implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "label", nullable = false)
    private String label;

    @NotNull
    @Size(max = 50)
    @Column(name = "identifier", length = 50, nullable = false)
    private String identifier;

    @Column(name = "delay")
    private Integer delay;

    @Column(name = "is_enabled")
    private Boolean isEnabled;

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

    public TaskType label(String label) {
        this.label = label;
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getIdentifier() {
        return identifier;
    }

    public TaskType identifier(String identifier) {
        this.identifier = identifier;
        return this;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Integer getDelay() {
        return delay;
    }

    public TaskType delay(Integer delay) {
        this.delay = delay;
        return this;
    }

    public void setDelay(Integer delay) {
        this.delay = delay;
    }

    public Boolean isIsEnabled() {
        return isEnabled;
    }

    public TaskType isEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
        return this;
    }

    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TaskType)) {
            return false;
        }
        return id != null && id.equals(((TaskType) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaskType{" +
            "id=" + getId() +
            ", label='" + getLabel() + "'" +
            ", identifier='" + getIdentifier() + "'" +
            ", delay=" + getDelay() +
            ", isEnabled='" + isIsEnabled() + "'" +
            "}";
    }
}
