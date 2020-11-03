package tect.her.ccm.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Status.
 */
@Entity
@Table(name = "status")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Status implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "label", length = 50, nullable = false)
    private String label;

    @NotNull
    @Column(name = "is_system", nullable = false)
    private Boolean isSystem;

    @Column(name = "img_filename")
    private String imgFilename;

    @NotNull
    @Column(name = "can_be_searched", nullable = false)
    private Boolean canBeSearched;

    @NotNull
    @Column(name = "can_be_modified", nullable = false)
    private Boolean canBeModified;

    @NotNull
    @Column(name = "enabled", nullable = false)
    private Boolean enabled;

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

    public Status label(String label) {
        this.label = label;
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Boolean isIsSystem() {
        return isSystem;
    }

    public Status isSystem(Boolean isSystem) {
        this.isSystem = isSystem;
        return this;
    }

    public void setIsSystem(Boolean isSystem) {
        this.isSystem = isSystem;
    }

    public String getImgFilename() {
        return imgFilename;
    }

    public Status imgFilename(String imgFilename) {
        this.imgFilename = imgFilename;
        return this;
    }

    public void setImgFilename(String imgFilename) {
        this.imgFilename = imgFilename;
    }

    public Boolean isCanBeSearched() {
        return canBeSearched;
    }

    public Status canBeSearched(Boolean canBeSearched) {
        this.canBeSearched = canBeSearched;
        return this;
    }

    public void setCanBeSearched(Boolean canBeSearched) {
        this.canBeSearched = canBeSearched;
    }

    public Boolean isCanBeModified() {
        return canBeModified;
    }

    public Status canBeModified(Boolean canBeModified) {
        this.canBeModified = canBeModified;
        return this;
    }

    public void setCanBeModified(Boolean canBeModified) {
        this.canBeModified = canBeModified;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public Status enabled(Boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Status)) {
            return false;
        }
        return id != null && id.equals(((Status) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Status{" +
            "id=" + getId() +
            ", label='" + getLabel() + "'" +
            ", isSystem='" + isIsSystem() + "'" +
            ", imgFilename='" + getImgFilename() + "'" +
            ", canBeSearched='" + isCanBeSearched() + "'" +
            ", canBeModified='" + isCanBeModified() + "'" +
            ", enabled='" + isEnabled() + "'" +
            "}";
    }
}
