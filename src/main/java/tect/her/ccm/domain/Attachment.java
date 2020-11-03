package tect.her.ccm.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Attachment.
 */
@Entity
@Table(name = "attachment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Attachment implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "label")
    private String label;

    @NotNull
    @Size(max = 32)
    @Column(name = "format", length = 32, nullable = false)
    private String format;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "updated_date")
    private Instant updatedDate;

    @Column(name = "version")
    private Integer version;

    @Column(name = "path")
    private String path;

    @Column(name = "filename")
    private String filename;

    @Column(name = "filesize")
    private Integer filesize;

    @Column(name = "validation_date")
    private Instant validationDate;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Engagement engagement;

    @ManyToOne
    @JsonIgnoreProperties(value = "attachments", allowSetters = true)
    private User user;

    @ManyToOne
    @JsonIgnoreProperties(value = "attachments", allowSetters = true)
    private User updatedBy;

    @ManyToOne
    @JsonIgnoreProperties(value = "attachments", allowSetters = true)
    private Status status;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(
        name = "attachment_origin",
        joinColumns = @JoinColumn(name = "attachment_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "origin_id", referencedColumnName = "id")
    )
    private Set<Attachment> origins = new HashSet<>();

    @ManyToMany(mappedBy = "origins")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<Attachment> children = new HashSet<>();

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

    public Attachment label(String label) {
        this.label = label;
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getFormat() {
        return format;
    }

    public Attachment format(String format) {
        this.format = format;
        return this;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public Attachment createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getUpdatedDate() {
        return updatedDate;
    }

    public Attachment updatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
        return this;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Integer getVersion() {
        return version;
    }

    public Attachment version(Integer version) {
        this.version = version;
        return this;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getPath() {
        return path;
    }

    public Attachment path(String path) {
        this.path = path;
        return this;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFilename() {
        return filename;
    }

    public Attachment filename(String filename) {
        this.filename = filename;
        return this;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Integer getFilesize() {
        return filesize;
    }

    public Attachment filesize(Integer filesize) {
        this.filesize = filesize;
        return this;
    }

    public void setFilesize(Integer filesize) {
        this.filesize = filesize;
    }

    public Instant getValidationDate() {
        return validationDate;
    }

    public Attachment validationDate(Instant validationDate) {
        this.validationDate = validationDate;
        return this;
    }

    public void setValidationDate(Instant validationDate) {
        this.validationDate = validationDate;
    }

    public Engagement getEngagement() {
        return engagement;
    }

    public Attachment engagement(Engagement engagement) {
        this.engagement = engagement;
        return this;
    }

    public void setEngagement(Engagement engagement) {
        this.engagement = engagement;
    }

    public User getUser() {
        return user;
    }

    public Attachment user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUpdatedBy() {
        return updatedBy;
    }

    public Attachment updatedBy(User user) {
        this.updatedBy = user;
        return this;
    }

    public void setUpdatedBy(User user) {
        this.updatedBy = user;
    }

    public Status getStatus() {
        return status;
    }

    public Attachment status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Set<Attachment> getOrigins() {
        return origins;
    }

    public Attachment origins(Set<Attachment> attachments) {
        this.origins = attachments;
        return this;
    }

    public Attachment addOrigin(Attachment attachment) {
        this.origins.add(attachment);
        attachment.getChildren().add(this);
        return this;
    }

    public Attachment removeOrigin(Attachment attachment) {
        this.origins.remove(attachment);
        attachment.getChildren().remove(this);
        return this;
    }

    public void setOrigins(Set<Attachment> attachments) {
        this.origins = attachments;
    }

    public Set<Attachment> getChildren() {
        return children;
    }

    public Attachment children(Set<Attachment> attachments) {
        this.children = attachments;
        return this;
    }

    public Attachment addChild(Attachment attachment) {
        this.children.add(attachment);
        attachment.getOrigins().add(this);
        return this;
    }

    public Attachment removeChild(Attachment attachment) {
        this.children.remove(attachment);
        attachment.getOrigins().remove(this);
        return this;
    }

    public void setChildren(Set<Attachment> attachments) {
        this.children = attachments;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Attachment)) {
            return false;
        }
        return id != null && id.equals(((Attachment) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Attachment{" +
            "id=" + getId() +
            ", label='" + getLabel() + "'" +
            ", format='" + getFormat() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            ", version=" + getVersion() +
            ", path='" + getPath() + "'" +
            ", filename='" + getFilename() + "'" +
            ", filesize=" + getFilesize() +
            ", validationDate='" + getValidationDate() + "'" +
            "}";
    }
}
