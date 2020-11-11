package tect.her.ccm.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A FilelistEgagementType.
 */
@Entity
@Table(name = "filelist_egagement_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class FilelistEgagementType implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "mandatory")
    private Boolean mandatory;

    @ManyToOne
    @JsonIgnoreProperties(value = "filelistEgagementTypes", allowSetters = true)
    private EngagementType engagementType;

    @ManyToOne
    @JsonIgnoreProperties(value = "filelistEngagementTypes", allowSetters = true)
    private Filelist filelist;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isMandatory() {
        return mandatory;
    }

    public FilelistEgagementType mandatory(Boolean mandatory) {
        this.mandatory = mandatory;
        return this;
    }

    public void setMandatory(Boolean mandatory) {
        this.mandatory = mandatory;
    }

    public EngagementType getEngagementType() {
        return engagementType;
    }

    public FilelistEgagementType engagementType(EngagementType engagementType) {
        this.engagementType = engagementType;
        return this;
    }

    public void setEngagementType(EngagementType engagementType) {
        this.engagementType = engagementType;
    }

    public Filelist getFilelist() {
        return filelist;
    }

    public FilelistEgagementType filelist(Filelist filelist) {
        this.filelist = filelist;
        return this;
    }

    public void setFilelist(Filelist filelist) {
        this.filelist = filelist;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FilelistEgagementType)) {
            return false;
        }
        return id != null && id.equals(((FilelistEgagementType) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FilelistEgagementType{" +
            "id=" + getId() +
            ", mandatory='" + isMandatory() + "'" +
            "}";
    }
}
