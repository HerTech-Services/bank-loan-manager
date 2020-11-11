package tect.her.ccm.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Filelist.
 */
@Entity
@Table(name = "filelist")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Filelist implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "label", length = 50, nullable = false)
    private String label;

    @Size(max = 10)
    @Column(name = "identifier", length = 10)
    private String identifier;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "filelist")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<FilelistEgagementType> filelistEngagementTypes = new HashSet<>();

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

    public Filelist label(String label) {
        this.label = label;
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getIdentifier() {
        return identifier;
    }

    public Filelist identifier(String identifier) {
        this.identifier = identifier;
        return this;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getDescription() {
        return description;
    }

    public Filelist description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<FilelistEgagementType> getFilelistEngagementTypes() {
        return filelistEngagementTypes;
    }

    public Filelist filelistEngagementTypes(Set<FilelistEgagementType> filelistEgagementTypes) {
        this.filelistEngagementTypes = filelistEgagementTypes;
        return this;
    }

    public Filelist addFilelistEngagementType(FilelistEgagementType filelistEgagementType) {
        this.filelistEngagementTypes.add(filelistEgagementType);
        filelistEgagementType.setFilelist(this);
        return this;
    }

    public Filelist removeFilelistEngagementType(FilelistEgagementType filelistEgagementType) {
        this.filelistEngagementTypes.remove(filelistEgagementType);
        filelistEgagementType.setFilelist(null);
        return this;
    }

    public void setFilelistEngagementTypes(Set<FilelistEgagementType> filelistEgagementTypes) {
        this.filelistEngagementTypes = filelistEgagementTypes;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Filelist)) {
            return false;
        }
        return id != null && id.equals(((Filelist) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Filelist{" +
            "id=" + getId() +
            ", label='" + getLabel() + "'" +
            ", identifier='" + getIdentifier() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
