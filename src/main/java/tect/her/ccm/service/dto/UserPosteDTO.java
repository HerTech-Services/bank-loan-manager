package tect.her.ccm.service.dto;

import java.io.Serializable;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link tect.her.ccm.domain.UserPoste} entity.
 */
public class UserPosteDTO implements Serializable {
    private Long id;

    private String role;

    @NotNull
    private Boolean isPrimary;

    private Long userId;

    private String userFirstName;

    private Long entitiesId;

    private String entitiesLabel;

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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public Long getEntitiesId() {
        return entitiesId;
    }

    public void setEntitiesId(Long posteId) {
        this.entitiesId = posteId;
    }

    public String getEntitiesLabel() {
        return entitiesLabel;
    }

    public void setEntitiesLabel(String posteLabel) {
        this.entitiesLabel = posteLabel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserPosteDTO)) {
            return false;
        }

        return id != null && id.equals(((UserPosteDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserPosteDTO{" +
            "id=" + getId() +
            ", role='" + getRole() + "'" +
            ", isPrimary='" + isIsPrimary() + "'" +
            ", userId=" + getUserId() +
            ", userFirstName='" + getUserFirstName() + "'" +
            ", entitiesId=" + getEntitiesId() +
            ", entitiesLabel='" + getEntitiesLabel() + "'" +
            "}";
    }
}
