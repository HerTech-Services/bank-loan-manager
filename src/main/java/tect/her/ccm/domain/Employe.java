package tect.her.ccm.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Employe.
 */
@Entity
@Table(name = "employe")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Employe implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 4)
    @Column(name = "cod_bnk", length = 4)
    private String codBnk;

    @Size(max = 6)
    @Column(name = "cod_emp", length = 6, unique = true)
    private String codEmp;

    @Size(max = 50)
    @Column(name = "rs_emp", length = 50, unique = true)
    private String rsEmp;

    @Column(name = "nom_emp")
    private String nomEmp;

    @Column(name = "prenom_emp")
    private String prenomEmp;

    @Size(max = 50)
    @Column(name = "fct_emp", length = 50)
    private String fctEmp;

    @Size(max = 50)
    @Column(name = "adr_emp", length = 50)
    private String adrEmp;

    @Size(max = 50)
    @Column(name = "te_emp", length = 50)
    private String teEmp;

    @Size(max = 50)
    @Column(name = "typ_enmp", length = 50)
    private String typEnmp;

    @Size(max = 50)
    @Column(name = "num_mat", length = 50)
    private String numMat;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

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

    public Employe codBnk(String codBnk) {
        this.codBnk = codBnk;
        return this;
    }

    public void setCodBnk(String codBnk) {
        this.codBnk = codBnk;
    }

    public String getCodEmp() {
        return codEmp;
    }

    public Employe codEmp(String codEmp) {
        this.codEmp = codEmp;
        return this;
    }

    public void setCodEmp(String codEmp) {
        this.codEmp = codEmp;
    }

    public String getRsEmp() {
        return rsEmp;
    }

    public Employe rsEmp(String rsEmp) {
        this.rsEmp = rsEmp;
        return this;
    }

    public void setRsEmp(String rsEmp) {
        this.rsEmp = rsEmp;
    }

    public String getNomEmp() {
        return nomEmp;
    }

    public Employe nomEmp(String nomEmp) {
        this.nomEmp = nomEmp;
        return this;
    }

    public void setNomEmp(String nomEmp) {
        this.nomEmp = nomEmp;
    }

    public String getPrenomEmp() {
        return prenomEmp;
    }

    public Employe prenomEmp(String prenomEmp) {
        this.prenomEmp = prenomEmp;
        return this;
    }

    public void setPrenomEmp(String prenomEmp) {
        this.prenomEmp = prenomEmp;
    }

    public String getFctEmp() {
        return fctEmp;
    }

    public Employe fctEmp(String fctEmp) {
        this.fctEmp = fctEmp;
        return this;
    }

    public void setFctEmp(String fctEmp) {
        this.fctEmp = fctEmp;
    }

    public String getAdrEmp() {
        return adrEmp;
    }

    public Employe adrEmp(String adrEmp) {
        this.adrEmp = adrEmp;
        return this;
    }

    public void setAdrEmp(String adrEmp) {
        this.adrEmp = adrEmp;
    }

    public String getTeEmp() {
        return teEmp;
    }

    public Employe teEmp(String teEmp) {
        this.teEmp = teEmp;
        return this;
    }

    public void setTeEmp(String teEmp) {
        this.teEmp = teEmp;
    }

    public String getTypEnmp() {
        return typEnmp;
    }

    public Employe typEnmp(String typEnmp) {
        this.typEnmp = typEnmp;
        return this;
    }

    public void setTypEnmp(String typEnmp) {
        this.typEnmp = typEnmp;
    }

    public String getNumMat() {
        return numMat;
    }

    public Employe numMat(String numMat) {
        this.numMat = numMat;
        return this;
    }

    public void setNumMat(String numMat) {
        this.numMat = numMat;
    }

    public User getUser() {
        return user;
    }

    public Employe user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Employe)) {
            return false;
        }
        return id != null && id.equals(((Employe) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Employe{" +
            "id=" + getId() +
            ", codBnk='" + getCodBnk() + "'" +
            ", codEmp='" + getCodEmp() + "'" +
            ", rsEmp='" + getRsEmp() + "'" +
            ", nomEmp='" + getNomEmp() + "'" +
            ", prenomEmp='" + getPrenomEmp() + "'" +
            ", fctEmp='" + getFctEmp() + "'" +
            ", adrEmp='" + getAdrEmp() + "'" +
            ", teEmp='" + getTeEmp() + "'" +
            ", typEnmp='" + getTypEnmp() + "'" +
            ", numMat='" + getNumMat() + "'" +
            "}";
    }
}
