package tect.her.ccm.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TaskLog.
 */
@Entity
@Table(name = "task_log")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TaskLog implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "comment")
    private String comment;

    @Column(name = "created_date")
    private Instant createdDate;

    @Lob
    @Column(name = "task_properties")
    private String taskProperties;

    @Lob
    @Column(name = "engagement_properties")
    private String engagementProperties;

    @ManyToOne
    @JsonIgnoreProperties(value = "taskLogs", allowSetters = true)
    private User user;

    @ManyToOne
    @JsonIgnoreProperties(value = "taskLogs", allowSetters = true)
    private Action action;

    @ManyToOne
    @JsonIgnoreProperties(value = "taskLogs", allowSetters = true)
    private Task task;

    @ManyToOne
    @JsonIgnoreProperties(value = "taskLogs", allowSetters = true)
    private Engagement engagement;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public TaskLog comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public TaskLog createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getTaskProperties() {
        return taskProperties;
    }

    public TaskLog taskProperties(String taskProperties) {
        this.taskProperties = taskProperties;
        return this;
    }

    public void setTaskProperties(String taskProperties) {
        this.taskProperties = taskProperties;
    }

    public String getEngagementProperties() {
        return engagementProperties;
    }

    public TaskLog engagementProperties(String engagementProperties) {
        this.engagementProperties = engagementProperties;
        return this;
    }

    public void setEngagementProperties(String engagementProperties) {
        this.engagementProperties = engagementProperties;
    }

    public User getUser() {
        return user;
    }

    public TaskLog user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Action getAction() {
        return action;
    }

    public TaskLog action(Action action) {
        this.action = action;
        return this;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public Task getTask() {
        return task;
    }

    public TaskLog task(Task task) {
        this.task = task;
        return this;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Engagement getEngagement() {
        return engagement;
    }

    public TaskLog engagement(Engagement engagement) {
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
        if (!(o instanceof TaskLog)) {
            return false;
        }
        return id != null && id.equals(((TaskLog) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaskLog{" +
            "id=" + getId() +
            ", comment='" + getComment() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", taskProperties='" + getTaskProperties() + "'" +
            ", engagementProperties='" + getEngagementProperties() + "'" +
            "}";
    }
}
