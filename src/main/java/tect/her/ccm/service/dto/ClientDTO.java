package tect.her.ccm.service.dto;

import java.io.Serializable;
import javax.validation.constraints.*;
import tect.her.ccm.domain.enumeration.MaritalStatus;
import tect.her.ccm.domain.enumeration.Sex;

/**
 * A DTO for the {@link tect.her.ccm.domain.Client} entity.
 */
public class ClientDTO implements Serializable {
    private Long id;

    @Size(max = 4)
    private String codBnk;

    @Size(max = 7)
    private String codCli;

    private String nomCli;

    private String mendCli;

    private MaritalStatus sfCli;

    @Size(max = 15)
    private String titre;

    @Size(max = 10)
    private String datNai;

    @Size(max = 50)
    private String lieuNai;

    @Size(max = 20)
    private String natCli;

    @Size(max = 20)
    private String lngCli;

    @Size(max = 20)
    private String socCli;

    @Size(max = 20)
    private String emploi;

    private Sex sexe;

    @Size(max = 20)
    private String numCni;

    @Size(max = 10)
    private String datCni;

    @Size(max = 10)
    private String finCni;

    @Size(max = 20)
    private String lieuCni;

    @Size(max = 50)
    private String autoCni;

    @Size(max = 50)
    private String adr;

    @Size(max = 50)
    private String tel;

    @Size(max = 50)
    private String ville;

    @Size(max = 50)
    private String site;

    @Size(max = 50)
    private String loc;

    @Size(max = 50)
    private String fax;

    @Size(max = 50)
    private String agnce;

    @Size(max = 50)
    private String mail;

    @Size(max = 50)
    private String pays;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodBnk() {
        return codBnk;
    }

    public void setCodBnk(String codBnk) {
        this.codBnk = codBnk;
    }

    public String getCodCli() {
        return codCli;
    }

    public void setCodCli(String codCli) {
        this.codCli = codCli;
    }

    public String getNomCli() {
        return nomCli;
    }

    public void setNomCli(String nomCli) {
        this.nomCli = nomCli;
    }

    public String getMendCli() {
        return mendCli;
    }

    public void setMendCli(String mendCli) {
        this.mendCli = mendCli;
    }

    public MaritalStatus getSfCli() {
        return sfCli;
    }

    public void setSfCli(MaritalStatus sfCli) {
        this.sfCli = sfCli;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDatNai() {
        return datNai;
    }

    public void setDatNai(String datNai) {
        this.datNai = datNai;
    }

    public String getLieuNai() {
        return lieuNai;
    }

    public void setLieuNai(String lieuNai) {
        this.lieuNai = lieuNai;
    }

    public String getNatCli() {
        return natCli;
    }

    public void setNatCli(String natCli) {
        this.natCli = natCli;
    }

    public String getLngCli() {
        return lngCli;
    }

    public void setLngCli(String lngCli) {
        this.lngCli = lngCli;
    }

    public String getSocCli() {
        return socCli;
    }

    public void setSocCli(String socCli) {
        this.socCli = socCli;
    }

    public String getEmploi() {
        return emploi;
    }

    public void setEmploi(String emploi) {
        this.emploi = emploi;
    }

    public Sex getSexe() {
        return sexe;
    }

    public void setSexe(Sex sexe) {
        this.sexe = sexe;
    }

    public String getNumCni() {
        return numCni;
    }

    public void setNumCni(String numCni) {
        this.numCni = numCni;
    }

    public String getDatCni() {
        return datCni;
    }

    public void setDatCni(String datCni) {
        this.datCni = datCni;
    }

    public String getFinCni() {
        return finCni;
    }

    public void setFinCni(String finCni) {
        this.finCni = finCni;
    }

    public String getLieuCni() {
        return lieuCni;
    }

    public void setLieuCni(String lieuCni) {
        this.lieuCni = lieuCni;
    }

    public String getAutoCni() {
        return autoCni;
    }

    public void setAutoCni(String autoCni) {
        this.autoCni = autoCni;
    }

    public String getAdr() {
        return adr;
    }

    public void setAdr(String adr) {
        this.adr = adr;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getAgnce() {
        return agnce;
    }

    public void setAgnce(String agnce) {
        this.agnce = agnce;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClientDTO)) {
            return false;
        }

        return id != null && id.equals(((ClientDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClientDTO{" +
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
