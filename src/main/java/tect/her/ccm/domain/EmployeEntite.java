package tect.her.ccm.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A EmployeEntite.
 */
@Entity
@Table(name = "employe_entite")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EmployeEntite implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role")
    private String role;

    @NotNull
    @Column(name = "is_primary", nullable = false)
    private Boolean isPrimary;

    @NotNull
    @Column(name = "is_chief", nullable = false)
    private Boolean isChief;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "employeEntites", allowSetters = true)
    private Employe employe;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "employeEntites", allowSetters = true)
    private Entite entite;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public EmployeEntite role(String role) {
        this.role = role;
        return this;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Boolean isIsPrimary() {
        return isPrimary;
    }

    public EmployeEntite isPrimary(Boolean isPrimary) {
        this.isPrimary = isPrimary;
        return this;
    }

    public void setIsPrimary(Boolean isPrimary) {
        this.isPrimary = isPrimary;
    }

    public Boolean isIsChief() {
        return isChief;
    }

    public EmployeEntite isChief(Boolean isChief) {
        this.isChief = isChief;
        return this;
    }

    public void setIsChief(Boolean isChief) {
        this.isChief = isChief;
    }

    public Employe getEmploye() {
        return employe;
    }

    public EmployeEntite employe(Employe employe) {
        this.employe = employe;
        return this;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }

    public Entite getEntite() {
        return entite;
    }

    public EmployeEntite entite(Entite entite) {
        this.entite = entite;
        return this;
    }

    public void setEntite(Entite entite) {
        this.entite = entite;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmployeEntite)) {
            return false;
        }
        return id != null && id.equals(((EmployeEntite) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeEntite{" +
            "id=" + getId() +
            ", role='" + getRole() + "'" +
            ", isPrimary='" + isIsPrimary() + "'" +
            ", isChief='" + isIsChief() + "'" +
            "}";
    }
}
