package tect.her.ccm.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Downgrading.
 */
@Entity
@Table(name = "downgrading")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Downgrading implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "created_date", nullable = false)
    private Instant createdDate;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "capital", precision = 21, scale = 2, nullable = false)
    private BigDecimal capital;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "interests", precision = 21, scale = 2, nullable = false)
    private BigDecimal interests;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "penalties", precision = 21, scale = 2, nullable = false)
    private BigDecimal penalties;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "accessories", precision = 21, scale = 2, nullable = false)
    private BigDecimal accessories;

    @Lob
    @Column(name = "description", nullable = false)
    private String description;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "downgradings", allowSetters = true)
    private DowngradingStep step;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "downgradings", allowSetters = true)
    private Engagement engagement;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public Downgrading createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public BigDecimal getCapital() {
        return capital;
    }

    public Downgrading capital(BigDecimal capital) {
        this.capital = capital;
        return this;
    }

    public void setCapital(BigDecimal capital) {
        this.capital = capital;
    }

    public BigDecimal getInterests() {
        return interests;
    }

    public Downgrading interests(BigDecimal interests) {
        this.interests = interests;
        return this;
    }

    public void setInterests(BigDecimal interests) {
        this.interests = interests;
    }

    public BigDecimal getPenalties() {
        return penalties;
    }

    public Downgrading penalties(BigDecimal penalties) {
        this.penalties = penalties;
        return this;
    }

    public void setPenalties(BigDecimal penalties) {
        this.penalties = penalties;
    }

    public BigDecimal getAccessories() {
        return accessories;
    }

    public Downgrading accessories(BigDecimal accessories) {
        this.accessories = accessories;
        return this;
    }

    public void setAccessories(BigDecimal accessories) {
        this.accessories = accessories;
    }

    public String getDescription() {
        return description;
    }

    public Downgrading description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DowngradingStep getStep() {
        return step;
    }

    public Downgrading step(DowngradingStep downgradingStep) {
        this.step = downgradingStep;
        return this;
    }

    public void setStep(DowngradingStep downgradingStep) {
        this.step = downgradingStep;
    }

    public Engagement getEngagement() {
        return engagement;
    }

    public Downgrading engagement(Engagement engagement) {
        this.engagement = engagement;
        return this;
    }

    public void setEngagement(Engagement engagement) {
        this.engagement = engagement;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Downgrading)) {
            return false;
        }
        return id != null && id.equals(((Downgrading) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Downgrading{" +
            "id=" + getId() +
            ", createdDate='" + getCreatedDate() + "'" +
            ", capital=" + getCapital() +
            ", interests=" + getInterests() +
            ", penalties=" + getPenalties() +
            ", accessories=" + getAccessories() +
            ", description='" + getDescription() + "'" +
            "}";
    }
}