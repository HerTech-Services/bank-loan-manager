package tect.her.ccm.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A EngagementType.
 */
@Entity
@Table(name = "engagement_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EngagementType implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "label", nullable = false)
    private String label;

    @NotNull
    @Column(name = "is_enabled", nullable = false)
    private Boolean isEnabled;

    @NotNull
    @Column(name = "process_delay", nullable = false)
    private Integer processDelay;

    @NotNull
    @Column(name = "delay_1", nullable = false)
    private Integer delay1;

    @NotNull
    @Column(name = "delay_2", nullable = false)
    private Integer delay2;

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

    public EngagementType label(String label) {
        this.label = label;
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Boolean isIsEnabled() {
        return isEnabled;
    }

    public EngagementType isEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
        return this;
    }

    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public Integer getProcessDelay() {
        return processDelay;
    }

    public EngagementType processDelay(Integer processDelay) {
        this.processDelay = processDelay;
        return this;
    }

    public void setProcessDelay(Integer processDelay) {
        this.processDelay = processDelay;
    }

    public Integer getDelay1() {
        return delay1;
    }

    public EngagementType delay1(Integer delay1) {
        this.delay1 = delay1;
        return this;
    }

    public void setDelay1(Integer delay1) {
        this.delay1 = delay1;
    }

    public Integer getDelay2() {
        return delay2;
    }

    public EngagementType delay2(Integer delay2) {
        this.delay2 = delay2;
        return this;
    }

    public void setDelay2(Integer delay2) {
        this.delay2 = delay2;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EngagementType)) {
            return false;
        }
        return id != null && id.equals(((EngagementType) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EngagementType{" +
            "id=" + getId() +
            ", label='" + getLabel() + "'" +
            ", isEnabled='" + isIsEnabled() + "'" +
            ", processDelay=" + getProcessDelay() +
            ", delay1=" + getDelay1() +
            ", delay2=" + getDelay2() +
            "}";
    }
}
