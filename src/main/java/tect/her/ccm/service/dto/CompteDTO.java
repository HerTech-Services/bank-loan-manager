package tect.her.ccm.service.dto;

import java.io.Serializable;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link tect.her.ccm.domain.Compte} entity.
 */
public class CompteDTO implements Serializable {
    private Long id;

    @Size(max = 4)
    private String codBnk;

    @Size(max = 15)
    private String codCpt;

    @Size(max = 15)
    private String numCpt;

    @Size(max = 20)
    private String libCpt;

    @Size(max = 15)
    private String numCpta;

    @Size(max = 3)
    private String numAgc;

    @Size(max = 4)
    private String typCpt;

    @Size(max = 4)
    private String etat;

    @Size(max = 7)
    private String codCli;

    @Size(max = 50)
    private String numCtr;

    @Size(max = 50)
    private String codeFor;

    private Long clientId;

    private String clientNomCli;

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

    public String getCodCpt() {
        return codCpt;
    }

    public void setCodCpt(String codCpt) {
        this.codCpt = codCpt;
    }

    public String getNumCpt() {
        return numCpt;
    }

    public void setNumCpt(String numCpt) {
        this.numCpt = numCpt;
    }

    public String getLibCpt() {
        return libCpt;
    }

    public void setLibCpt(String libCpt) {
        this.libCpt = libCpt;
    }

    public String getNumCpta() {
        return numCpta;
    }

    public void setNumCpta(String numCpta) {
        this.numCpta = numCpta;
    }

    public String getNumAgc() {
        return numAgc;
    }

    public void setNumAgc(String numAgc) {
        this.numAgc = numAgc;
    }

    public String getTypCpt() {
        return typCpt;
    }

    public void setTypCpt(String typCpt) {
        this.typCpt = typCpt;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getCodCli() {
        return codCli;
    }

    public void setCodCli(String codCli) {
        this.codCli = codCli;
    }

    public String getNumCtr() {
        return numCtr;
    }

    public void setNumCtr(String numCtr) {
        this.numCtr = numCtr;
    }

    public String getCodeFor() {
        return codeFor;
    }

    public void setCodeFor(String codeFor) {
        this.codeFor = codeFor;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getClientNomCli() {
        return clientNomCli;
    }

    public void setClientNomCli(String clientNomCli) {
        this.clientNomCli = clientNomCli;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CompteDTO)) {
            return false;
        }

        return id != null && id.equals(((CompteDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CompteDTO{" +
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
            ", clientId=" + getClientId() +
            ", clientNomCli='" + getClientNomCli() + "'" +
            "}";
    }
}
