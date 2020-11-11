package tect.her.ccm.service.dto;

import java.io.Serializable;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link tect.her.ccm.domain.Action} entity.
 */
public class ActionDTO implements Serializable {
    private Long id;

    @NotNull
    private String label;

    @NotNull
    @Size(max = 32)
    private String keyword;

    @NotNull
    private Boolean isSystem;

    private String actionPage;

    private Boolean history;

    @Size(max = 128)
    private String composant;

    @Lob
    private String parameters;

    private Long statusId;

    private String statusLabel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Boolean isIsSystem() {
        return isSystem;
    }

    public void setIsSystem(Boolean isSystem) {
        this.isSystem = isSystem;
    }

    public String getActionPage() {
        return actionPage;
    }

    public void setActionPage(String actionPage) {
        this.actionPage = actionPage;
    }

    public Boolean isHistory() {
        return history;
    }

    public void setHistory(Boolean history) {
        this.history = history;
    }

    public String getComposant() {
        return composant;
    }

    public void setComposant(String composant) {
        this.composant = composant;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public String getStatusLabel() {
        return statusLabel;
    }

    public void setStatusLabel(String statusLabel) {
        this.statusLabel = statusLabel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ActionDTO)) {
            return false;
        }

        return id != null && id.equals(((ActionDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ActionDTO{" +
            "id=" + getId() +
            ", label='" + getLabel() + "'" +
            ", keyword='" + getKeyword() + "'" +
            ", isSystem='" + isIsSystem() + "'" +
            ", actionPage='" + getActionPage() + "'" +
            ", history='" + isHistory() + "'" +
            ", composant='" + getComposant() + "'" +
            ", parameters='" + getParameters() + "'" +
            ", statusId=" + getStatusId() +
            ", statusLabel='" + getStatusLabel() + "'" +
            "}";
    }
}
