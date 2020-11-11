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

/**
 * Criteria class for the {@link tect.her.ccm.domain.Employe} entity. This class is used
 * in {@link tect.her.ccm.web.rest.EmployeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /employes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EmployeCriteria implements Serializable, Criteria {
    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter codBnk;

    private StringFilter codEmp;

    private StringFilter rsEmp;

    private StringFilter nomEmp;

    private StringFilter prenomEmp;

    private StringFilter fctEmp;

    private StringFilter adrEmp;

    private StringFilter teEmp;

    private StringFilter typEnmp;

    private StringFilter numMat;

    private LongFilter userId;

    public EmployeCriteria() {}

    public EmployeCriteria(EmployeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.codBnk = other.codBnk == null ? null : other.codBnk.copy();
        this.codEmp = other.codEmp == null ? null : other.codEmp.copy();
        this.rsEmp = other.rsEmp == null ? null : other.rsEmp.copy();
        this.nomEmp = other.nomEmp == null ? null : other.nomEmp.copy();
        this.prenomEmp = other.prenomEmp == null ? null : other.prenomEmp.copy();
        this.fctEmp = other.fctEmp == null ? null : other.fctEmp.copy();
        this.adrEmp = other.adrEmp == null ? null : other.adrEmp.copy();
        this.teEmp = other.teEmp == null ? null : other.teEmp.copy();
        this.typEnmp = other.typEnmp == null ? null : other.typEnmp.copy();
        this.numMat = other.numMat == null ? null : other.numMat.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
    }

    @Override
    public EmployeCriteria copy() {
        return new EmployeCriteria(this);
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

    public StringFilter getCodEmp() {
        return codEmp;
    }

    public void setCodEmp(StringFilter codEmp) {
        this.codEmp = codEmp;
    }

    public StringFilter getRsEmp() {
        return rsEmp;
    }

    public void setRsEmp(StringFilter rsEmp) {
        this.rsEmp = rsEmp;
    }

    public StringFilter getNomEmp() {
        return nomEmp;
    }

    public void setNomEmp(StringFilter nomEmp) {
        this.nomEmp = nomEmp;
    }

    public StringFilter getPrenomEmp() {
        return prenomEmp;
    }

    public void setPrenomEmp(StringFilter prenomEmp) {
        this.prenomEmp = prenomEmp;
    }

    public StringFilter getFctEmp() {
        return fctEmp;
    }

    public void setFctEmp(StringFilter fctEmp) {
        this.fctEmp = fctEmp;
    }

    public StringFilter getAdrEmp() {
        return adrEmp;
    }

    public void setAdrEmp(StringFilter adrEmp) {
        this.adrEmp = adrEmp;
    }

    public StringFilter getTeEmp() {
        return teEmp;
    }

    public void setTeEmp(StringFilter teEmp) {
        this.teEmp = teEmp;
    }

    public StringFilter getTypEnmp() {
        return typEnmp;
    }

    public void setTypEnmp(StringFilter typEnmp) {
        this.typEnmp = typEnmp;
    }

    public StringFilter getNumMat() {
        return numMat;
    }

    public void setNumMat(StringFilter numMat) {
        this.numMat = numMat;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final EmployeCriteria that = (EmployeCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(codBnk, that.codBnk) &&
            Objects.equals(codEmp, that.codEmp) &&
            Objects.equals(rsEmp, that.rsEmp) &&
            Objects.equals(nomEmp, that.nomEmp) &&
            Objects.equals(prenomEmp, that.prenomEmp) &&
            Objects.equals(fctEmp, that.fctEmp) &&
            Objects.equals(adrEmp, that.adrEmp) &&
            Objects.equals(teEmp, that.teEmp) &&
            Objects.equals(typEnmp, that.typEnmp) &&
            Objects.equals(numMat, that.numMat) &&
            Objects.equals(userId, that.userId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, codBnk, codEmp, rsEmp, nomEmp, prenomEmp, fctEmp, adrEmp, teEmp, typEnmp, numMat, userId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (codBnk != null ? "codBnk=" + codBnk + ", " : "") +
                (codEmp != null ? "codEmp=" + codEmp + ", " : "") +
                (rsEmp != null ? "rsEmp=" + rsEmp + ", " : "") +
                (nomEmp != null ? "nomEmp=" + nomEmp + ", " : "") +
                (prenomEmp != null ? "prenomEmp=" + prenomEmp + ", " : "") +
                (fctEmp != null ? "fctEmp=" + fctEmp + ", " : "") +
                (adrEmp != null ? "adrEmp=" + adrEmp + ", " : "") +
                (teEmp != null ? "teEmp=" + teEmp + ", " : "") +
                (typEnmp != null ? "typEnmp=" + typEnmp + ", " : "") +
                (numMat != null ? "numMat=" + numMat + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
            "}";
    }
}
