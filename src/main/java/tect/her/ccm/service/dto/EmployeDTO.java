package tect.her.ccm.service.dto;

import java.io.Serializable;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link tect.her.ccm.domain.Employe} entity.
 */
public class EmployeDTO implements Serializable {
    private Long id;

    @Size(max = 4)
    private String codBnk;

    @Size(max = 6)
    private String codEmp;

    @Size(max = 50)
    private String rsEmp;

    private String nomEmp;

    private String prenomEmp;

    @Size(max = 50)
    private String fctEmp;

    @Size(max = 50)
    private String adrEmp;

    @Size(max = 50)
    private String teEmp;

    @Size(max = 50)
    private String typEnmp;

    @Size(max = 50)
    private String numMat;

    private Long userId;

    private String userLogin;

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

    public String getCodEmp() {
        return codEmp;
    }

    public void setCodEmp(String codEmp) {
        this.codEmp = codEmp;
    }

    public String getRsEmp() {
        return rsEmp;
    }

    public void setRsEmp(String rsEmp) {
        this.rsEmp = rsEmp;
    }

    public String getNomEmp() {
        return nomEmp;
    }

    public void setNomEmp(String nomEmp) {
        this.nomEmp = nomEmp;
    }

    public String getPrenomEmp() {
        return prenomEmp;
    }

    public void setPrenomEmp(String prenomEmp) {
        this.prenomEmp = prenomEmp;
    }

    public String getFctEmp() {
        return fctEmp;
    }

    public void setFctEmp(String fctEmp) {
        this.fctEmp = fctEmp;
    }

    public String getAdrEmp() {
        return adrEmp;
    }

    public void setAdrEmp(String adrEmp) {
        this.adrEmp = adrEmp;
    }

    public String getTeEmp() {
        return teEmp;
    }

    public void setTeEmp(String teEmp) {
        this.teEmp = teEmp;
    }

    public String getTypEnmp() {
        return typEnmp;
    }

    public void setTypEnmp(String typEnmp) {
        this.typEnmp = typEnmp;
    }

    public String getNumMat() {
        return numMat;
    }

    public void setNumMat(String numMat) {
        this.numMat = numMat;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmployeDTO)) {
            return false;
        }

        return id != null && id.equals(((EmployeDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeDTO{" +
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
            ", userId=" + getUserId() +
            ", userLogin='" + getUserLogin() + "'" +
            "}";
    }
}
