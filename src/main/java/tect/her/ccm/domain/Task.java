package tect.her.ccm.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Task.
 */
@Entity
@Table(name = "task")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Task implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Min(value = 1)
    @Column(name = "sequence", nullable = false)
    private Integer sequence;

    @NotNull
    @Column(name = "is_system", nullable = false)
    private Boolean isSystem;

    @NotNull
    @Column(name = "process_delay", nullable = false)
    private Integer processDelay;

    @NotNull
    @Column(name = "delay_1", nullable = false)
    private Integer delay1;

    @NotNull
    @Column(name = "delay_2", nullable = false)
    private Integer delay2;

    @Column(name = "viewed")
    private Integer viewed;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "process_date")
    private Instant processDate;

    @Lob
    @Column(name = "process_comment")
    private String processComment;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "tasks", allowSetters = true)
    private TaskType type;

    @ManyToOne
    @JsonIgnoreProperties(value = "tasks", allowSetters = true)
    private User processUser;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "tasks", allowSetters = true)
    private Poste poste;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "tasks", allowSetters = true)
    private Engagement engagement;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSequence() {
        return sequence;
    }

    public Task sequence(Integer sequence) {
        this.sequence = sequence;
        return this;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public Boolean isIsSystem() {
        return isSystem;
    }

    public Task isSystem(Boolean isSystem) {
        this.isSystem = isSystem;
        return this;
    }

    public void setIsSystem(Boolean isSystem) {
        this.isSystem = isSystem;
    }

    public Integer getProcessDelay() {
        return processDelay;
    }

    public Task processDelay(Integer processDelay) {
        this.processDelay = processDelay;
        return this;
    }

    public void setProcessDelay(Integer processDelay) {
        this.processDelay = processDelay;
    }

    public Integer getDelay1() {
        return delay1;
    }

    public Task delay1(Integer delay1) {
        this.delay1 = delay1;
        return this;
    }

    public void setDelay1(Integer delay1) {
        this.delay1 = delay1;
    }

    public Integer getDelay2() {
        return delay2;
    }

    public Task delay2(Integer delay2) {
        this.delay2 = delay2;
        return this;
    }

    public void setDelay2(Integer delay2) {
        this.delay2 = delay2;
    }

    public Integer getViewed() {
        return viewed;
    }

    public Task viewed(Integer viewed) {
        this.viewed = viewed;
        return this;
    }

    public void setViewed(Integer viewed) {
        this.viewed = viewed;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public Task createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getProcessDate() {
        return processDate;
    }

    public Task processDate(Instant processDate) {
        this.processDate = processDate;
        return this;
    }

    public void setProcessDate(Instant processDate) {
        this.processDate = processDate;
    }

    public String getProcessComment() {
        return processComment;
    }

    public Task processComment(String processComment) {
        this.processComment = processComment;
        return this;
    }

    public void setProcessComment(String processComment) {
        this.processComment = processComment;
    }

    public TaskType getType() {
        return type;
    }

    public Task type(TaskType taskType) {
        this.type = taskType;
        return this;
    }

    public void setType(TaskType taskType) {
        this.type = taskType;
    }

    public User getProcessUser() {
        return processUser;
    }

    public Task processUser(User user) {
        this.processUser = user;
        return this;
    }

    public void setProcessUser(User user) {
        this.processUser = user;
    }

    public Poste getPoste() {
        return poste;
    }

    public Task poste(Poste poste) {
        this.poste = poste;
        return this;
    }

    public void setPoste(Poste poste) {
        this.poste = poste;
    }

    public Engagement getEngagement() {
        return engagement;
    }

    public Task engagement(Engagement engagement) {
        this.engagement = engagement;
        return this;
    }

    public void setEngagement(Engagement engagement) {
        this.engagement = engagement;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Task)) {
            return false;
        }
        return id != null && id.equals(((Task) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Task{" +
            "id=" + getId() +
            ", sequence=" + getSequence() +
            ", isSystem='" + isIsSystem() + "'" +
            ", processDelay=" + getProcessDelay() +
            ", delay1=" + getDelay1() +
            ", delay2=" + getDelay2() +
            ", viewed=" + getViewed() +
            ", createdDate='" + getCreatedDate() + "'" +
            ", processDate='" + getProcessDate() + "'" +
            ", processComment='" + getProcessComment() + "'" +
            "}";
    }
}
