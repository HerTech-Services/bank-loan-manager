package tect.her.ccm.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Compte.
 */
@Entity
@Table(name = "compte")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Compte implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 4)
    @Column(name = "cod_bnk", length = 4)
    private String codBnk;

    @Size(max = 15)
    @Column(name = "cod_cpt", length = 15)
    private String codCpt;

    @Size(max = 15)
    @Column(name = "num_cpt", length = 15)
    private String numCpt;

    @Size(max = 20)
    @Column(name = "lib_cpt", length = 20)
    private String libCpt;

    @Size(max = 15)
    @Column(name = "num_cpta", length = 15)
    private String numCpta;

    @Size(max = 3)
    @Column(name = "num_agc", length = 3)
    private String numAgc;

    @Size(max = 4)
    @Column(name = "typ_cpt", length = 4)
    private String typCpt;

    @Size(max = 4)
    @Column(name = "etat", length = 4)
    private String etat;

    @Size(max = 7)
    @Column(name = "cod_cli", length = 7)
    private String codCli;

    @Size(max = 50)
    @Column(name = "num_ctr", length = 50)
    private String numCtr;

    @Size(max = 50)
    @Column(name = "code_for", length = 50)
    private String codeFor;

    @OneToMany(mappedBy = "compte")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Engagement> engagements = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "comptes", allowSetters = true)
    private Client client;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodBnk() {
        return codBnk;
    }

    public Compte codBnk(String codBnk) {
        this.codBnk = codBnk;
        return this;
    }

    public void setCodBnk(String codBnk) {
        this.codBnk = codBnk;
    }

    public String getCodCpt() {
        return codCpt;
    }

    public Compte codCpt(String codCpt) {
        this.codCpt = codCpt;
        return this;
    }

    public void setCodCpt(String codCpt) {
        this.codCpt = codCpt;
    }

    public String getNumCpt() {
        return numCpt;
    }

    public Compte numCpt(String numCpt) {
        this.numCpt = numCpt;
        return this;
    }

    public void setNumCpt(String numCpt) {
        this.numCpt = numCpt;
    }

    public String getLibCpt() {
        return libCpt;
    }

    public Compte libCpt(String libCpt) {
        this.libCpt = libCpt;
        return this;
    }

    public void setLibCpt(String libCpt) {
        this.libCpt = libCpt;
    }

    public String getNumCpta() {
        return numCpta;
    }

    public Compte numCpta(String numCpta) {
        this.numCpta = numCpta;
        return this;
    }

    public void setNumCpta(String numCpta) {
        this.numCpta = numCpta;
    }

    public String getNumAgc() {
        return numAgc;
    }

    public Compte numAgc(String numAgc) {
        this.numAgc = numAgc;
        return this;
    }

    public void setNumAgc(String numAgc) {
        this.numAgc = numAgc;
    }

    public String getTypCpt() {
        return typCpt;
    }

    public Compte typCpt(String typCpt) {
        this.typCpt = typCpt;
        return this;
    }

    public void setTypCpt(String typCpt) {
        this.typCpt = typCpt;
    }

    public String getEtat() {
        return etat;
    }

    public Compte etat(String etat) {
        this.etat = etat;
        return this;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getCodCli() {
        return codCli;
    }

    public Compte codCli(String codCli) {
        this.codCli = codCli;
        return this;
    }

    public void setCodCli(String codCli) {
        this.codCli = codCli;
    }

    public String getNumCtr() {
        return numCtr;
    }

    public Compte numCtr(String numCtr) {
        this.numCtr = numCtr;
        return this;
    }

    public void setNumCtr(String numCtr) {
        this.numCtr = numCtr;
    }

    public String getCodeFor() {
        return codeFor;
    }

    public Compte codeFor(String codeFor) {
        this.codeFor = codeFor;
        return this;
    }

    public void setCodeFor(String codeFor) {
        this.codeFor = codeFor;
    }

    public Set<Engagement> getEngagements() {
        return engagements;
    }

    public Compte engagements(Set<Engagement> engagements) {
        this.engagements = engagements;
        return this;
    }

    public Compte addEngagement(Engagement engagement) {
        this.engagements.add(engagement);
        engagement.setCompte(this);
        return this;
    }

    public Compte removeEngagement(Engagement engagement) {
        this.engagements.remove(engagement);
        engagement.setCompte(null);
        return this;
    }

    public void setEngagements(Set<Engagement> engagements) {
        this.engagements = engagements;
    }

    public Client getClient() {
        return client;
    }

    public Compte client(Client client) {
        this.client = client;
        return this;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Compte)) {
            return false;
        }
        return id != null && id.equals(((Compte) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Compte{" +
            "id=" + getId() +
            ", codBnk='" + getCodBnk() + "'" +
            ", codCpt='" + getCodCpt() + "'" +
            ", numCpt='" + getNumCpt() + "'" +
            ", libCpt='" + getLibCpt() + "'" +
            ", numCpta='" + getNumCpta() + "'" +
            ", numAgc='" + getNumAgc() + "'" +
            ", typCpt='" + getTypCpt() + "'" +
            ", etat='" + getEtat() + "'" +
            ", codCli='" + getCodCli() + "'" +
            ", numCtr='" + getNumCtr() + "'" +
            ", codeFor='" + getCodeFor() + "'" +
            "}";
    }
}
