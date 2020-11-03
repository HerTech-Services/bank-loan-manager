package tect.her.ccm.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TasktypeStatusAction.
 */
@Entity
@Table(name = "tasktype_status_action")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TasktypeStatusAction implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties(value = "tasktypeStatusActions", allowSetters = true)
    private TaskType tasktype;

    @ManyToOne
    @JsonIgnoreProperties(value = "tasktypeStatusActions", allowSetters = true)
    private Action action;

    @ManyToOne
    @JsonIgnoreProperties(value = "tasktypeStatusActions", allowSetters = true)
    private Status status;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TaskType getTasktype() {
        return tasktype;
    }

    public TasktypeStatusAction tasktype(TaskType taskType) {
        this.tasktype = taskType;
        return this;
    }

    public void setTasktype(TaskType taskType) {
        this.tasktype = taskType;
    }

    public Action getAction() {
        return action;
    }

    public TasktypeStatusAction action(Action action) {
        this.action = action;
        return this;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public Status getStatus() {
        return status;
    }

    public TasktypeStatusAction status(Status status) {
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
        if (!(o instanceof TasktypeStatusAction)) {
            return false;
        }
        return id != null && id.equals(((TasktypeStatusAction) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TasktypeStatusAction{" +
            "id=" + getId() +
            "}";
    }
}
