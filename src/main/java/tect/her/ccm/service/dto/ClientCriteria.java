package tect.her.ccm.service.dto;

import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import java.io.Serializable;
import java.util.Objects;
import tect.her.ccm.domain.enumeration.MaritalStatus;
import tect.her.ccm.domain.enumeration.Sex;

/**
 * Criteria class for the {@link tect.her.ccm.domain.Client} entity. This class is used
 * in {@link tect.her.ccm.web.rest.ClientResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /clients?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ClientCriteria implements Serializable, Criteria {

    /**
     * Class for filtering MaritalStatus
     */
    public static class MaritalStatusFilter extends Filter<MaritalStatus> {

        public MaritalStatusFilter() {}

        public MaritalStatusFilter(MaritalStatusFilter filter) {
            super(filter);
        }

        @Override
        public MaritalStatusFilter copy() {
            return new MaritalStatusFilter(this);
        }
    }

    /**
     * Class for filtering Sex
     */
    public static class SexFilter extends Filter<Sex> {

        public SexFilter() {}

        public SexFilter(SexFilter filter) {
            super(filter);
        }

        @Override
        public SexFilter copy() {
            return new SexFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter codBnk;

    private StringFilter codCli;

    private StringFilter nomCli;

    private StringFilter mendCli;

    private MaritalStatusFilter sfCli;

    private StringFilter titre;

    private StringFilter datNai;

    private StringFilter lieuNai;

    private StringFilter natCli;

    private StringFilter lngCli;

    private StringFilter socCli;

    private StringFilter emploi;

    private SexFilter sexe;

    private StringFilter numCni;

    private StringFilter datCni;

    private StringFilter finCni;

    private StringFilter lieuCni;

    private StringFilter autoCni;

    private StringFilter adr;

    private StringFilter tel;

    private StringFilter ville;

    private StringFilter site;

    private StringFilter loc;

    private StringFilter fax;

    private StringFilter agnce;

    private StringFilter mail;

    private StringFilter pays;

    private LongFilter compteId;

    private LongFilter engagementId;

    public ClientCriteria() {}

    public ClientCriteria(ClientCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.codBnk = other.codBnk == null ? null : other.codBnk.copy();
        this.codCli = other.codCli == null ? null : other.codCli.copy();
        this.nomCli = other.nomCli == null ? null : other.nomCli.copy();
        this.mendCli = other.mendCli == null ? null : other.mendCli.copy();
        this.sfCli = other.sfCli == null ? null : other.sfCli.copy();
        this.titre = other.titre == null ? null : other.titre.copy();
        this.datNai = other.datNai == null ? null : other.datNai.copy();
        this.lieuNai = other.lieuNai == null ? null : other.lieuNai.copy();
        this.natCli = other.natCli == null ? null : other.natCli.copy();
        this.lngCli = other.lngCli == null ? null : other.lngCli.copy();
        this.socCli = other.socCli == null ? null : other.socCli.copy();
        this.emploi = other.emploi == null ? null : other.emploi.copy();
        this.sexe = other.sexe == null ? null : other.sexe.copy();
        this.numCni = other.numCni == null ? null : other.numCni.copy();
        this.datCni = other.datCni == null ? null : other.datCni.copy();
        this.finCni = other.finCni == null ? null : other.finCni.copy();
        this.lieuCni = other.lieuCni == null ? null : other.lieuCni.copy();
        this.autoCni = other.autoCni == null ? null : other.autoCni.copy();
        this.adr = other.adr == null ? null : other.adr.copy();
        this.tel = other.tel == null ? null : other.tel.copy();
        this.ville = other.ville == null ? null : other.ville.copy();
        this.site = other.site == null ? null : other.site.copy();
        this.loc = other.loc == null ? null : other.loc.copy();
        this.fax = other.fax == null ? null : other.fax.copy();
        this.agnce = other.agnce == null ? null : other.agnce.copy();
        this.mail = other.mail == null ? null : other.mail.copy();
        this.pays = other.pays == null ? null : other.pays.copy();
        this.compteId = other.compteId == null ? null : other.compteId.copy();
        this.engagementId = other.engagementId == null ? null : other.engagementId.copy();
    }

    @Override
    public ClientCriteria copy() {
        return new ClientCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getCodBnk() {
        return codBnk;
    }

    public void setCodBnk(StringFilter codBnk) {
        this.codBnk = codBnk;
    }

    public StringFilter getCodCli() {
        return codCli;
    }

    public void setCodCli(StringFilter codCli) {
        this.codCli = codCli;
    }

    public StringFilter getNomCli() {
        return nomCli;
    }

    public void setNomCli(StringFilter nomCli) {
        this.nomCli = nomCli;
    }

    public StringFilter getMendCli() {
        return mendCli;
    }

    public void setMendCli(StringFilter mendCli) {
        this.mendCli = mendCli;
    }

    public MaritalStatusFilter getSfCli() {
        return sfCli;
    }

    public void setSfCli(MaritalStatusFilter sfCli) {
        this.sfCli = sfCli;
    }

    public StringFilter getTitre() {
        return titre;
    }

    public void setTitre(StringFilter titre) {
        this.titre = titre;
    }

    public StringFilter getDatNai() {
        return datNai;
    }

    public void setDatNai(StringFilter datNai) {
        this.datNai = datNai;
    }

    public StringFilter getLieuNai() {
        return lieuNai;
    }

    public void setLieuNai(StringFilter lieuNai) {
        this.lieuNai = lieuNai;
    }

    public StringFilter getNatCli() {
        return natCli;
    }

    public void setNatCli(StringFilter natCli) {
        this.natCli = natCli;
    }

    public StringFilter getLngCli() {
        return lngCli;
    }

    public void setLngCli(StringFilter lngCli) {
        this.lngCli = lngCli;
    }

    public StringFilter getSocCli() {
        return socCli;
    }

    public void setSocCli(StringFilter socCli) {
        this.socCli = socCli;
    }

    public StringFilter getEmploi() {
        return emploi;
    }

    public void setEmploi(StringFilter emploi) {
        this.emploi = emploi;
    }

    public SexFilter getSexe() {
        return sexe;
    }

    public void setSexe(SexFilter sexe) {
        this.sexe = sexe;
    }

    public StringFilter getNumCni() {
        return numCni;
    }

    public void setNumCni(StringFilter numCni) {
        this.numCni = numCni;
    }

    public StringFilter getDatCni() {
        return datCni;
    }

    public void setDatCni(StringFilter datCni) {
        this.datCni = datCni;
    }

    public StringFilter getFinCni() {
        return finCni;
    }

    public void setFinCni(StringFilter finCni) {
        this.finCni = finCni;
    }

    public StringFilter getLieuCni() {
        return lieuCni;
    }

    public void setLieuCni(StringFilter lieuCni) {
        this.lieuCni = lieuCni;
    }

    public StringFilter getAutoCni() {
        return autoCni;
    }

    public void setAutoCni(StringFilter autoCni) {
        this.autoCni = autoCni;
    }

    public StringFilter getAdr() {
        return adr;
    }

    public void setAdr(StringFilter adr) {
        this.adr = adr;
    }

    public StringFilter getTel() {
        return tel;
    }

    public void setTel(StringFilter tel) {
        this.tel = tel;
    }

    public StringFilter getVille() {
        return ville;
    }

    public void setVille(StringFilter ville) {
        this.ville = ville;
    }

    public StringFilter getSite() {
        return site;
    }

    public void setSite(StringFilter site) {
        this.site = site;
    }

    public StringFilter getLoc() {
        return loc;
    }

    public void setLoc(StringFilter loc) {
        this.loc = loc;
    }

    public StringFilter getFax() {
        return fax;
    }

    public void setFax(StringFilter fax) {
        this.fax = fax;
    }

    public StringFilter getAgnce() {
        return agnce;
    }

    public void setAgnce(StringFilter agnce) {
        this.agnce = agnce;
    }

    public StringFilter getMail() {
        return mail;
    }

    public void setMail(StringFilter mail) {
        this.mail = mail;
    }

    public StringFilter getPays() {
        return pays;
    }

    public void setPays(StringFilter pays) {
        this.pays = pays;
    }

    public LongFilter getCompteId() {
        return compteId;
    }

    public void setCompteId(LongFilter compteId) {
        this.compteId = compteId;
    }

    public LongFilter getEngagementId() {
        return engagementId;
    }

    public void setEngagementId(LongFilter engagementId) {
        this.engagementId = engagementId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ClientCriteria that = (ClientCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(codBnk, that.codBnk) &&
            Objects.equals(codCli, that.codCli) &&
            Objects.equals(nomCli, that.nomCli) &&
            Objects.equals(mendCli, that.mendCli) &&
            Objects.equals(sfCli, that.sfCli) &&
            Objects.equals(titre, that.titre) &&
            Objects.equals(datNai, that.datNai) &&
            Objects.equals(lieuNai, that.lieuNai) &&
            Objects.equals(natCli, that.natCli) &&
            Objects.equals(lngCli, that.lngCli) &&
            Objects.equals(socCli, that.socCli) &&
            Objects.equals(emploi, that.emploi) &&
            Objects.equals(sexe, that.sexe) &&
            Objects.equals(numCni, that.numCni) &&
            Objects.equals(datCni, that.datCni) &&
            Objects.equals(finCni, that.finCni) &&
            Objects.equals(lieuCni, that.lieuCni) &&
            Objects.equals(autoCni, that.autoCni) &&
            Objects.equals(adr, that.adr) &&
            Objects.equals(tel, that.tel) &&
            Objects.equals(ville, that.ville) &&
            Objects.equals(site, that.site) &&
            Objects.equals(loc, that.loc) &&
            Objects.equals(fax, that.fax) &&
            Objects.equals(agnce, that.agnce) &&
            Objects.equals(mail, that.mail) &&
            Objects.equals(pays, that.pays) &&
            Objects.equals(compteId, that.compteId) &&
            Objects.equals(engagementId, that.engagementId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            codBnk,
            codCli,
            nomCli,
            mendCli,
            sfCli,
            titre,
            datNai,
            lieuNai,
            natCli,
            lngCli,
            socCli,
            emploi,
            sexe,
            numCni,
            datCni,
            finCni,
            lieuCni,
            autoCni,
            adr,
            tel,
            ville,
            site,
            loc,
            fax,
            agnce,
            mail,
            pays,
            compteId,
            engagementId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClientCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (codBnk != null ? "codBnk=" + codBnk + ", " : "") +
                (codCli != null ? "codCli=" + codCli + ", " : "") +
                (nomCli != null ? "nomCli=" + nomCli + ", " : "") +
                (mendCli != null ? "mendCli=" + mendCli + ", " : "") +
                (sfCli != null ? "sfCli=" + sfCli + ", " : "") +
                (titre != null ? "titre=" + titre + ", " : "") +
                (datNai != null ? "datNai=" + datNai + ", " : "") +
                (lieuNai != null ? "lieuNai=" + lieuNai + ", " : "") +
                (natCli != null ? "natCli=" + natCli + ", " : "") +
                (lngCli != null ? "lngCli=" + lngCli + ", " : "") +
                (socCli != null ? "socCli=" + socCli + ", " : "") +
                (emploi != null ? "emploi=" + emploi + ", " : "") +
                (sexe != null ? "sexe=" + sexe + ", " : "") +
                (numCni != null ? "numCni=" + numCni + ", " : "") +
                (datCni != null ? "datCni=" + datCni + ", " : "") +
                (finCni != null ? "finCni=" + finCni + ", " : "") +
                (lieuCni != null ? "lieuCni=" + lieuCni + ", " : "") +
                (autoCni != null ? "autoCni=" + autoCni + ", " : "") +
                (adr != null ? "adr=" + adr + ", " : "") +
                (tel != null ? "tel=" + tel + ", " : "") +
                (ville != null ? "ville=" + ville + ", " : "") +
                (site != null ? "site=" + site + ", " : "") +
                (loc != null ? "loc=" + loc + ", " : "") +
                (fax != null ? "fax=" + fax + ", " : "") +
                (agnce != null ? "agnce=" + agnce + ", " : "") +
                (mail != null ? "mail=" + mail + ", " : "") +
                (pays != null ? "pays=" + pays + ", " : "") +
                (compteId != null ? "compteId=" + compteId + ", " : "") +
                (engagementId != null ? "engagementId=" + engagementId + ", " : "") +
            "}";
    }
}
