package tect.her.ccm.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Parameters.
 */
@Entity
@Table(name = "parameters")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Parameters implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "identifier", length = 50, nullable = false)
    private String identifier;

    @Column(name = "description")
    private String description;

    @Column(name = "param_value_string")
    private String paramValueString;

    @Column(name = "param_value_int")
    private Integer paramValueInt;

    @Column(name = "param_value_date")
    private Instant paramValueDate;

    @Lob
    @Column(name = "param_value_json")
    private String paramValueJson;

    @Column(name = "updated_date")
    private Instant updatedDate;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public Parameters identifier(String identifier) {
        this.identifier = identifier;
        return this;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getDescription() {
        return description;
    }

    public Parameters description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getParamValueString() {
        return paramValueString;
    }

    public Parameters paramValueString(String paramValueString) {
        this.paramValueString = paramValueString;
        return this;
    }

    public void setParamValueString(String paramValueString) {
        this.paramValueString = paramValueString;
    }

    public Integer getParamValueInt() {
        return paramValueInt;
    }

    public Parameters paramValueInt(Integer paramValueInt) {
        this.paramValueInt = paramValueInt;
        return this;
    }

    public void setParamValueInt(Integer paramValueInt) {
        this.paramValueInt = paramValueInt;
    }

    public Instant getParamValueDate() {
        return paramValueDate;
    }

    public Parameters paramValueDate(Instant paramValueDate) {
        this.paramValueDate = paramValueDate;
        return this;
    }

    public void setParamValueDate(Instant paramValueDate) {
        this.paramValueDate = paramValueDate;
    }

    public String getParamValueJson() {
        return paramValueJson;
    }

    public Parameters paramValueJson(String paramValueJson) {
        this.paramValueJson = paramValueJson;
        return this;
    }

    public void setParamValueJson(String paramValueJson) {
        this.paramValueJson = paramValueJson;
    }

    public Instant getUpdatedDate() {
        return updatedDate;
    }

    public Parameters updatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
        return this;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Parameters)) {
            return false;
        }
        return id != null && id.equals(((Parameters) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Parameters{" +
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
