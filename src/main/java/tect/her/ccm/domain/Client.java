package tect.her.ccm.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import tect.her.ccm.domain.enumeration.MaritalStatus;
import tect.her.ccm.domain.enumeration.Sex;

/**
 * A Client.
 */
@Entity
@Table(name = "client")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Client implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 4)
    @Column(name = "cod_bnk", length = 4)
    private String codBnk;

    @Size(max = 7)
    @Column(name = "cod_cli", length = 7)
    private String codCli;

    @Column(name = "nom_cli")
    private String nomCli;

    @Column(name = "mend_cli")
    private String mendCli;

    @Enumerated(EnumType.STRING)
    @Column(name = "sf_cli")
    private MaritalStatus sfCli;

    @Size(max = 15)
    @Column(name = "titre", length = 15)
    private String titre;

    @Size(max = 10)
    @Column(name = "dat_nai", length = 10)
    private String datNai;

    @Size(max = 50)
    @Column(name = "lieu_nai", length = 50)
    private String lieuNai;

    @Size(max = 20)
    @Column(name = "nat_cli", length = 20)
    private String natCli;

    @Size(max = 20)
    @Column(name = "lng_cli", length = 20)
    private String lngCli;

    @Size(max = 20)
    @Column(name = "soc_cli", length = 20)
    private String socCli;

    @Size(max = 20)
    @Column(name = "emploi", length = 20)
    private String emploi;

    @Enumerated(EnumType.STRING)
    @Column(name = "sexe")
    private Sex sexe;

    @Size(max = 20)
    @Column(name = "num_cni", length = 20)
    private String numCni;

    @Size(max = 10)
    @Column(name = "dat_cni", length = 10)
    private String datCni;

    @Size(max = 10)
    @Column(name = "fin_cni", length = 10)
    private String finCni;

    @Size(max = 20)
    @Column(name = "lieu_cni", length = 20)
    private String lieuCni;

    @Size(max = 50)
    @Column(name = "auto_cni", length = 50)
    private String autoCni;

    @Size(max = 50)
    @Column(name = "adr", length = 50)
    private String adr;

    @Size(max = 50)
    @Column(name = "tel", length = 50)
    private String tel;

    @Size(max = 50)
    @Column(name = "ville", length = 50)
    private String ville;

    @Size(max = 50)
    @Column(name = "site", length = 50)
    private String site;

    @Size(max = 50)
    @Column(name = "loc", length = 50)
    private String loc;

    @Size(max = 50)
    @Column(name = "fax", length = 50)
    private String fax;

    @Size(max = 50)
    @Column(name = "agnce", length = 50)
    private String agnce;

    @Size(max = 50)
    @Column(name = "mail", length = 50)
    private String mail;

    @Size(max = 50)
    @Column(name = "pays", length = 50)
    private String pays;

    @OneToMany(mappedBy = "client")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Compte> comptes = new HashSet<>();

    @OneToMany(mappedBy = "client")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Engagement> engagements = new HashSet<>();

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

    public Client codBnk(String codBnk) {
        this.codBnk = codBnk;
        return this;
    }

    public void setCodBnk(String codBnk) {
        this.codBnk = codBnk;
    }

    public String getCodCli() {
        return codCli;
    }

    public Client codCli(String codCli) {
        this.codCli = codCli;
        return this;
    }

    public void setCodCli(String codCli) {
        this.codCli = codCli;
    }

    public String getNomCli() {
        return nomCli;
    }

    public Client nomCli(String nomCli) {
        this.nomCli = nomCli;
        return this;
    }

    public void setNomCli(String nomCli) {
        this.nomCli = nomCli;
    }

    public String getMendCli() {
        return mendCli;
    }

    public Client mendCli(String mendCli) {
        this.mendCli = mendCli;
        return this;
    }

    public void setMendCli(String mendCli) {
        this.mendCli = mendCli;
    }

    public MaritalStatus getSfCli() {
        return sfCli;
    }

    public Client sfCli(MaritalStatus sfCli) {
        this.sfCli = sfCli;
        return this;
    }

    public void setSfCli(MaritalStatus sfCli) {
        this.sfCli = sfCli;
    }

    public String getTitre() {
        return titre;
    }

    public Client titre(String titre) {
        this.titre = titre;
        return this;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDatNai() {
        return datNai;
    }

    public Client datNai(String datNai) {
        this.datNai = datNai;
        return this;
    }

    public void setDatNai(String datNai) {
        this.datNai = datNai;
    }

    public String getLieuNai() {
        return lieuNai;
    }

    public Client lieuNai(String lieuNai) {
        this.lieuNai = lieuNai;
        return this;
    }

    public void setLieuNai(String lieuNai) {
        this.lieuNai = lieuNai;
    }

    public String getNatCli() {
        return natCli;
    }

    public Client natCli(String natCli) {
        this.natCli = natCli;
        return this;
    }

    public void setNatCli(String natCli) {
        this.natCli = natCli;
    }

    public String getLngCli() {
        return lngCli;
    }

    public Client lngCli(String lngCli) {
        this.lngCli = lngCli;
        return this;
    }

    public void setLngCli(String lngCli) {
        this.lngCli = lngCli;
    }

    public String getSocCli() {
        return socCli;
    }

    public Client socCli(String socCli) {
        this.socCli = socCli;
        return this;
    }

    public void setSocCli(String socCli) {
        this.socCli = socCli;
    }

    public String getEmploi() {
        return emploi;
    }

    public Client emploi(String emploi) {
        this.emploi = emploi;
        return this;
    }

    public void setEmploi(String emploi) {
        this.emploi = emploi;
    }

    public Sex getSexe() {
        return sexe;
    }

    public Client sexe(Sex sexe) {
        this.sexe = sexe;
        return this;
    }

    public void setSexe(Sex sexe) {
        this.sexe = sexe;
    }

    public String getNumCni() {
        return numCni;
    }

    public Client numCni(String numCni) {
        this.numCni = numCni;
        return this;
    }

    public void setNumCni(String numCni) {
        this.numCni = numCni;
    }

    public String getDatCni() {
        return datCni;
    }

    public Client datCni(String datCni) {
        this.datCni = datCni;
        return this;
    }

    public void setDatCni(String datCni) {
        this.datCni = datCni;
    }

    public String getFinCni() {
        return finCni;
    }

    public Client finCni(String finCni) {
        this.finCni = finCni;
        return this;
    }

    public void setFinCni(String finCni) {
        this.finCni = finCni;
    }

    public String getLieuCni() {
        return lieuCni;
    }

    public Client lieuCni(String lieuCni) {
        this.lieuCni = lieuCni;
        return this;
    }

    public void setLieuCni(String lieuCni) {
        this.lieuCni = lieuCni;
    }

    public String getAutoCni() {
        return autoCni;
    }

    public Client autoCni(String autoCni) {
        this.autoCni = autoCni;
        return this;
    }

    public void setAutoCni(String autoCni) {
        this.autoCni = autoCni;
    }

    public String getAdr() {
        return adr;
    }

    public Client adr(String adr) {
        this.adr = adr;
        return this;
    }

    public void setAdr(String adr) {
        this.adr = adr;
    }

    public String getTel() {
        return tel;
    }

    public Client tel(String tel) {
        this.tel = tel;
        return this;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getVille() {
        return ville;
    }

    public Client ville(String ville) {
        this.ville = ville;
        return this;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getSite() {
        return site;
    }

    public Client site(String site) {
        this.site = site;
        return this;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getLoc() {
        return loc;
    }

    public Client loc(String loc) {
        this.loc = loc;
        return this;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String getFax() {
        return fax;
    }

    public Client fax(String fax) {
        this.fax = fax;
        return this;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getAgnce() {
        return agnce;
    }

    public Client agnce(String agnce) {
        this.agnce = agnce;
        return this;
    }

    public void setAgnce(String agnce) {
        this.agnce = agnce;
    }

    public String getMail() {
        return mail;
    }

    public Client mail(String mail) {
        this.mail = mail;
        return this;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPays() {
        return pays;
    }

    public Client pays(String pays) {
        this.pays = pays;
        return this;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public Set<Compte> getComptes() {
        return comptes;
    }

    public Client comptes(Set<Compte> comptes) {
        this.comptes = comptes;
        return this;
    }

    public Client addCompte(Compte compte) {
        this.comptes.add(compte);
        compte.setClient(this);
        return this;
    }

    public Client removeCompte(Compte compte) {
        this.comptes.remove(compte);
        compte.setClient(null);
        return this;
    }

    public void setComptes(Set<Compte> comptes) {
        this.comptes = comptes;
    }

    public Set<Engagement> getEngagements() {
        return engagements;
    }

    public Client engagements(Set<Engagement> engagements) {
        this.engagements = engagements;
        return this;
    }

    public Client addEngagement(Engagement engagement) {
        this.engagements.add(engagement);
        engagement.setClient(this);
        return this;
    }

    public Client removeEngagement(Engagement engagement) {
        this.engagements.remove(engagement);
        engagement.setClient(null);
        return this;
    }

    public void setEngagements(Set<Engagement> engagements) {
        this.engagements = engagements;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Client)) {
            return false;
        }
        return id != null && id.equals(((Client) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Client{" +
            "id=" + getId() +
            ", codBnk='" + getCodBnk() + "'" +
            ", codCli='" + getCodCli() + "'" +
            ", nomCli='" + getNomCli() + "'" +
            ", mendCli='" + getMendCli() + "'" +
            ", sfCli='" + getSfCli() + "'" +
            ", titre='" + getTitre() + "'" +
            ", datNai='" + getDatNai() + "'" +
            ", lieuNai='" + getLieuNai() + "'" +
            ", natCli='" + getNatCli() + "'" +
            ", lngCli='" + getLngCli() + "'" +
            ", socCli='" + getSocCli() + "'" +
            ", emploi='" + getEmploi() + "'" +
            ", sexe='" + getSexe() + "'" +
            ", numCni='" + getNumCni() + "'" +
            ", datCni='" + getDatCni() + "'" +
            ", finCni='" + getFinCni() + "'" +
            ", lieuCni='" + getLieuCni() + "'" +
            ", autoCni='" + getAutoCni() + "'" +
            ", adr='" + getAdr() + "'" +
            ", tel='" + getTel() + "'" +
            ", ville='" + getVille() + "'" +
            ", site='" + getSite() + "'" +
            ", loc='" + getLoc() + "'" +
            ", fax='" + getFax() + "'" +
            ", agnce='" + getAgnce() + "'" +
            ", mail='" + getMail() + "'" +
            ", pays='" + getPays() + "'" +
            "}";
    }
}
