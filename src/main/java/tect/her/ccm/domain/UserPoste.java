package tect.her.ccm.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A UserPoste.
 */
@Entity
@Table(name = "user_poste")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserPoste implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role")
    private String role;

    @NotNull
    @Column(name = "is_primary", nullable = false)
    private Boolean isPrimary;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "userPostes", allowSetters = true)
    private User user;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "userPostes", allowSetters = true)
    private Poste entities;

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

    public UserPoste role(String role) {
        this.role = role;
        return this;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Boolean isIsPrimary() {
        return isPrimary;
    }

    public UserPoste isPrimary(Boolean isPrimary) {
        this.isPrimary = isPrimary;
        return this;
    }

    public void setIsPrimary(Boolean isPrimary) {
        this.isPrimary = isPrimary;
    }

    public User getUser() {
        return user;
    }

    public UserPoste user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Poste getEntities() {
        return entities;
    }

    public UserPoste entities(Poste poste) {
        this.entities = poste;
        return this;
    }

    public void setEntities(Poste poste) {
        this.entities = poste;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserPoste)) {
            return false;
        }
        return id != null && id.equals(((UserPoste) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserPoste{" +
            "id=" + getId() +
            ", role='" + getRole() + "'" +
            ", isPrimary='" + isIsPrimary() + "'" +
            "}";
    }
}
