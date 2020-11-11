package tect.her.ccm.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Entite.
 */
@Entity
@Table(name = "entite")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Entite implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 32)
    @Column(name = "identifier", length = 32, nullable = false, unique = true)
    private String identifier;

    @NotNull
    @Column(name = "label", nullable = false)
    private String label;

    @Size(max = 50)
    @Column(name = "short_label", length = 50)
    private String shortLabel;

    @Column(name = "parent")
    private Integer parent;

    @NotNull
    @Column(name = "is_enabled", nullable = false)
    private Boolean isEnabled;

    @Column(name = "adrs_1")
    private String adrs1;

    @Column(name = "adrs_2")
    private String adrs2;

    @Column(name = "adrs_3")
    private String adrs3;

    @Column(name = "zipcode")
    private String zipcode;

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    @Column(name = "email")
    private String email;

    @Lob
    @Column(name = "parameters")
    private String parameters;

    @Size(max = 32)
    @Column(name = "parent_id", length = 32, unique = true)
    private String parentId;

    @OneToMany(mappedBy = "entite")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<EmployeEntite> employeEntites = new HashSet<>();

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

    public Entite identifier(String identifier) {
        this.identifier = identifier;
        return this;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getLabel() {
        return label;
    }

    public Entite label(String label) {
        this.label = label;
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getShortLabel() {
        return shortLabel;
    }

    public Entite shortLabel(String shortLabel) {
        this.shortLabel = shortLabel;
        return this;
    }

    public void setShortLabel(String shortLabel) {
        this.shortLabel = shortLabel;
    }

    public Integer getParent() {
        return parent;
    }

    public Entite parent(Integer parent) {
        this.parent = parent;
        return this;
    }

    public void setParent(Integer parent) {
        this.parent = parent;
    }

    public Boolean isIsEnabled() {
        return isEnabled;
    }

    public Entite isEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
        return this;
    }

    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public String getAdrs1() {
        return adrs1;
    }

    public Entite adrs1(String adrs1) {
        this.adrs1 = adrs1;
        return this;
    }

    public void setAdrs1(String adrs1) {
        this.adrs1 = adrs1;
    }

    public String getAdrs2() {
        return adrs2;
    }

    public Entite adrs2(String adrs2) {
        this.adrs2 = adrs2;
        return this;
    }

    public void setAdrs2(String adrs2) {
        this.adrs2 = adrs2;
    }

    public String getAdrs3() {
        return adrs3;
    }

    public Entite adrs3(String adrs3) {
        this.adrs3 = adrs3;
        return this;
    }

    public void setAdrs3(String adrs3) {
        this.adrs3 = adrs3;
    }

    public String getZipcode() {
        return zipcode;
    }

    public Entite zipcode(String zipcode) {
        this.zipcode = zipcode;
        return this;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCity() {
        return city;
    }

    public Entite city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public Entite country(String country) {
        this.country = country;
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmail() {
        return email;
    }

    public Entite email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getParameters() {
        return parameters;
    }

    public Entite parameters(String parameters) {
        this.parameters = parameters;
        return this;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    public String getParentId() {
        return parentId;
    }

    public Entite parentId(String parentId) {
        this.parentId = parentId;
        return this;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Set<EmployeEntite> getEmployeEntites() {
        return employeEntites;
    }

    public Entite employeEntites(Set<EmployeEntite> employeEntites) {
        this.employeEntites = employeEntites;
        return this;
    }

    public Entite addEmployeEntite(EmployeEntite employeEntite) {
        this.employeEntites.add(employeEntite);
        employeEntite.setEntite(this);
        return this;
    }

    public Entite removeEmployeEntite(EmployeEntite employeEntite) {
        this.employeEntites.remove(employeEntite);
        employeEntite.setEntite(null);
        return this;
    }

    public void setEmployeEntites(Set<EmployeEntite> employeEntites) {
        this.employeEntites = employeEntites;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Entite)) {
            return false;
        }
        return id != null && id.equals(((Entite) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Entite{" +
            "id=" + getId() +
            ", identifier='" + getIdentifier() + "'" +
            ", label='" + getLabel() + "'" +
            ", shortLabel='" + getShortLabel() + "'" +
            ", parent=" + getParent() +
            ", isEnabled='" + isIsEnabled() + "'" +
            ", adrs1='" + getAdrs1() + "'" +
            ", adrs2='" + getAdrs2() + "'" +
            ", adrs3='" + getAdrs3() + "'" +
            ", zipcode='" + getZipcode() + "'" +
            ", city='" + getCity() + "'" +
            ", country='" + getCountry() + "'" +
            ", email='" + getEmail() + "'" +
            ", parameters='" + getParameters() + "'" +
            ", parentId='" + getParentId() + "'" +
            "}";
    }
}
