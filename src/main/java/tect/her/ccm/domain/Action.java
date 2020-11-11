package tect.her.ccm.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Action.
 */
@Entity
@Table(name = "action")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Action implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "label", nullable = false)
    private String label;

    @NotNull
    @Size(max = 32)
    @Column(name = "keyword", length = 32, nullable = false)
    private String keyword;

    @NotNull
    @Column(name = "is_system", nullable = false)
    private Boolean isSystem;

    @Column(name = "action_page")
    private String actionPage;

    @Column(name = "history")
    private Boolean history;

    @Size(max = 128)
    @Column(name = "composant", length = 128)
    private String composant;

    @Lob
    @Column(name = "parameters")
    private String parameters;

    @ManyToOne
    @JsonIgnoreProperties(value = "actions", allowSetters = true)
    private Status status;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public Action label(String label) {
        this.label = label;
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getKeyword() {
        return keyword;
    }

    public Action keyword(String keyword) {
        this.keyword = keyword;
        return this;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Boolean isIsSystem() {
        return isSystem;
    }

    public Action isSystem(Boolean isSystem) {
        this.isSystem = isSystem;
        return this;
    }

    public void setIsSystem(Boolean isSystem) {
        this.isSystem = isSystem;
    }

    public String getActionPage() {
        return actionPage;
    }

    public Action actionPage(String actionPage) {
        this.actionPage = actionPage;
        return this;
    }

    public void setActionPage(String actionPage) {
        this.actionPage = actionPage;
    }

    public Boolean isHistory() {
        return history;
    }

    public Action history(Boolean history) {
        this.history = history;
        return this;
    }

    public void setHistory(Boolean history) {
        this.history = history;
    }

    public String getComposant() {
        return composant;
    }

    public Action composant(String composant) {
        this.composant = composant;
        return this;
    }

    public void setComposant(String composant) {
        this.composant = composant;
    }

    public String getParameters() {
        return parameters;
    }

    public Action parameters(String parameters) {
        this.parameters = parameters;
        return this;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    public Status getStatus() {
        return status;
    }

    public Action status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Action)) {
            return false;
        }
        return id != null && id.equals(((Action) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Action{" +
            "id=" + getId() +
            ", label='" + getLabel() + "'" +
            ", keyword='" + getKeyword() + "'" +
            ", isSystem='" + isIsSystem() + "'" +
            ", actionPage='" + getActionPage() + "'" +
            ", history='" + isHistory() + "'" +
            ", composant='" + getComposant() + "'" +
            ", parameters='" + getParameters() + "'" +
            "}";
    }
}
