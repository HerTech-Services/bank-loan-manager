package tect.her.ccm.service.dto;

import java.io.Serializable;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link tect.her.ccm.domain.EmployeEntite} entity.
 */
public class EmployeEntiteDTO implements Serializable {
    private Long id;

    private String role;

    @NotNull
    private Boolean isPrimary;

    @NotNull
    private Boolean isChief;

    private Long employeId;

    private String employeNomEmp;

    private Long entiteId;

    private String entiteLabel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Boolean isIsPrimary() {
        return isPrimary;
    }

    public void setIsPrimary(Boolean isPrimary) {
        this.isPrimary = isPrimary;
    }

    public Boolean isIsChief() {
        return isChief;
    }

    public void setIsChief(Boolean isChief) {
        this.isChief = isChief;
    }

    public Long getEmployeId() {
        return employeId;
    }

    public void setEmployeId(Long employeId) {
        this.employeId = employeId;
    }

    public String getEmployeNomEmp() {
        return employeNomEmp;
    }

    public void setEmployeNomEmp(String employeNomEmp) {
        this.employeNomEmp = employeNomEmp;
    }

    public Long getEntiteId() {
        return entiteId;
    }

    public void setEntiteId(Long entiteId) {
        this.entiteId = entiteId;
    }

    public String getEntiteLabel() {
        return entiteLabel;
    }

    public void setEntiteLabel(String entiteLabel) {
        this.entiteLabel = entiteLabel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmployeEntiteDTO)) {
            return false;
        }

        return id != null && id.equals(((EmployeEntiteDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeEntiteDTO{" +
            "id=" + getId() +
            ", role='" + getRole() + "'" +
            ", isPrimary='" + isIsPrimary() + "'" +
            ", isChief='" + isIsChief() + "'" +
            ", employeId=" + getEmployeId() +
            ", employeNomEmp='" + getEmployeNomEmp() + "'" +
            ", entiteId=" + getEntiteId() +
            ", entiteLabel='" + getEntiteLabel() + "'" +
            "}";
    }
}
