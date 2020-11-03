package tect.her.ccm.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A History.
 */
@Entity
@Table(name = "history")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class History implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "table_name", length = 50, nullable = false)
    private String tableName;

    @NotNull
    @Column(name = "record_id", nullable = false)
    private String recordId;

    @Column(name = "event_type")
    private String eventType;

    @Column(name = "event_date")
    private Instant eventDate;

    @Column(name = "info")
    private String info;

    @NotNull
    @Size(max = 50)
    @Column(name = "host", length = 50, nullable = false)
    private String host;

    @Lob
    @Column(name = "properties")
    private String properties;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTableName() {
        return tableName;
    }

    public History tableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getRecordId() {
        return recordId;
    }

    public History recordId(String recordId) {
        this.recordId = recordId;
        return this;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getEventType() {
        return eventType;
    }

    public History eventType(String eventType) {
        this.eventType = eventType;
        return this;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Instant getEventDate() {
        return eventDate;
    }

    public History eventDate(Instant eventDate) {
        this.eventDate = eventDate;
        return this;
    }

    public void setEventDate(Instant eventDate) {
        this.eventDate = eventDate;
    }

    public String getInfo() {
        return info;
    }

    public History info(String info) {
        this.info = info;
        return this;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getHost() {
        return host;
    }

    public History host(String host) {
        this.host = host;
        return this;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getProperties() {
        return properties;
    }

    public History properties(String properties) {
        this.properties = properties;
        return this;
    }

    public void setProperties(String properties) {
        this.properties = properties;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof History)) {
            return false;
        }
        return id != null && id.equals(((History) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "History{" +
            "id=" + getId() +
            ", tableName='" + getTableName() + "'" +
            ", recordId='" + getRecordId() + "'" +
            ", eventType='" + getEventType() + "'" +
            ", eventDate='" + getEventDate() + "'" +
            ", info='" + getInfo() + "'" +
            ", host='" + getHost() + "'" +
            ", properties='" + getProperties() + "'" +
            "}";
    }
}
