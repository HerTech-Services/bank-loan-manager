package tect.her.ccm.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Poste.
 */
@Entity
@Table(name = "poste")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Poste implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 32)
    @Column(name = "code", length = 32, nullable = false, unique = true)
    private String code;

    @NotNull
    @Column(name = "label", nullable = false)
    private String label;

    @Size(max = 50)
    @Column(name = "short_label", length = 50)
    private String shortLabel;

    @Column(name = "entity")
    private String entity;

    @NotNull
    @Column(name = "enabled", nullable = false)
    private Boolean enabled;

    @Column(name = "adrs_1")
    private String adrs1;

    @Column(name = "adrs_2")
    private String adrs2;

    @Column(name = "adrs_3")
    private String adrs3;

    @Column(name = "city")
    private String city;

    @OneToMany(mappedBy = "entities")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<UserPoste> userPostes = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(
        name = "poste_parent_poste",
        joinColumns = @JoinColumn(name = "poste_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "parent_poste_id", referencedColumnName = "id")
    )
    private Set<Poste> parentPostes = new HashSet<>();

    @ManyToMany(mappedBy = "parentPostes")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<Poste> children = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public Poste code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLabel() {
        return label;
    }

    public Poste label(String label) {
        this.label = label;
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getShortLabel() {
        return shortLabel;
    }

    public Poste shortLabel(String shortLabel) {
        this.shortLabel = shortLabel;
        return this;
    }

    public void setShortLabel(String shortLabel) {
        this.shortLabel = shortLabel;
    }

    public String getEntity() {
        return entity;
    }

    public Poste entity(String entity) {
        this.entity = entity;
        return this;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public Poste enabled(Boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getAdrs1() {
        return adrs1;
    }

    public Poste adrs1(String adrs1) {
        this.adrs1 = adrs1;
        return this;
    }

    public void setAdrs1(String adrs1) {
        this.adrs1 = adrs1;
    }

    public String getAdrs2() {
        return adrs2;
    }

    public Poste adrs2(String adrs2) {
        this.adrs2 = adrs2;
        return this;
    }

    public void setAdrs2(String adrs2) {
        this.adrs2 = adrs2;
    }

    public String getAdrs3() {
        return adrs3;
    }

    public Poste adrs3(String adrs3) {
        this.adrs3 = adrs3;
        return this;
    }

    public void setAdrs3(String adrs3) {
        this.adrs3 = adrs3;
    }

    public String getCity() {
        return city;
    }

    public Poste city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Set<UserPoste> getUserPostes() {
        return userPostes;
    }

    public Poste userPostes(Set<UserPoste> userPostes) {
        this.userPostes = userPostes;
        return this;
    }

    public Poste addUserPoste(UserPoste userPoste) {
        this.userPostes.add(userPoste);
        userPoste.setEntities(this);
        return this;
    }

    public Poste removeUserPoste(UserPoste userPoste) {
        this.userPostes.remove(userPoste);
        userPoste.setEntities(null);
        return this;
    }

    public void setUserPostes(Set<UserPoste> userPostes) {
        this.userPostes = userPostes;
    }

    public Set<Poste> getParentPostes() {
        return parentPostes;
    }

    public Poste parentPostes(Set<Poste> postes) {
        this.parentPostes = postes;
        return this;
    }

    public Poste addParentPoste(Poste poste) {
        this.parentPostes.add(poste);
        poste.getChildren().add(this);
        return this;
    }

    public Poste removeParentPoste(Poste poste) {
        this.parentPostes.remove(poste);
        poste.getChildren().remove(this);
        return this;
    }

    public void setParentPostes(Set<Poste> postes) {
        this.parentPostes = postes;
    }

    public Set<Poste> getChildren() {
        return children;
    }

    public Poste children(Set<Poste> postes) {
        this.children = postes;
        return this;
    }

    public Poste addChild(Poste poste) {
        this.children.add(poste);
        poste.getParentPostes().add(this);
        return this;
    }

    public Poste removeChild(Poste poste) {
        this.children.remove(poste);
        poste.getParentPostes().remove(this);
        return this;
    }

    public void setChildren(Set<Poste> postes) {
        this.children = postes;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Poste)) {
            return false;
        }
        return id != null && id.equals(((Poste) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Poste{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", label='" + getLabel() + "'" +
            ", shortLabel='" + getShortLabel() + "'" +
            ", entity='" + getEntity() + "'" +
            ", enabled='" + isEnabled() + "'" +
            ", adrs1='" + getAdrs1() + "'" +
            ", adrs2='" + getAdrs2() + "'" +
            ", adrs3='" + getAdrs3() + "'" +
            ", city='" + getCity() + "'" +
            "}";
    }
}
