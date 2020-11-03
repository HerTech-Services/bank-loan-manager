package tect.her.ccm.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import tect.her.ccm.domain.enumeration.Echeance;
import tect.her.ccm.domain.enumeration.ModeRembourssement;

/**
 * A Engagement.
 */
@Entity
@Table(name = "engagement")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Engagement implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Min(value = 0)
    @Column(name = "scoring", nullable = false)
    private Integer scoring;

    @NotNull
    @Column(name = "subject", nullable = false)
    private String subject;

    @NotNull
    @Column(name = "amount", precision = 21, scale = 2, nullable = false)
    private BigDecimal amount;

    @NotNull
    @Min(value = 1)
    @Column(name = "delay", nullable = false)
    private Integer delay;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "payment", nullable = false)
    private Echeance payment;

    @NotNull
    @Min(value = 0)
    @Column(name = "jhi_delayed", nullable = false)
    private Integer delayed;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "reimbursement", nullable = false)
    private ModeRembourssement reimbursement;

    @Lob
    @Column(name = "analyse")
    private String analyse;

    @Column(name = "is_stop")
    private Boolean isStop;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "start_date")
    private Instant startDate;

    @Column(name = "en_date")
    private Instant enDate;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "updated_date")
    private Instant updatedDate;

    @Column(name = "deleted_date")
    private Instant deletedDate;

    @Column(name = "stoped_date")
    private Instant stopedDate;

    @OneToOne
    @JoinColumn(unique = true)
    private Task currentTask;

    @OneToMany(mappedBy = "engagement")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Downgrading> downgradings = new HashSet<>();

    @OneToMany(mappedBy = "engagement")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Reject> rejects = new HashSet<>();

    @OneToMany(mappedBy = "engagement")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Task> tasks = new HashSet<>();

    @OneToMany(mappedBy = "engagement")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Notes> notes = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "engagements", allowSetters = true)
    private EngagementType type;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "engagements", allowSetters = true)
    private Status status;

    @ManyToOne
    @JsonIgnoreProperties(value = "engagements", allowSetters = true)
    private Status decision;

    @ManyToOne
    @JsonIgnoreProperties(value = "engagements", allowSetters = true)
    private User createdBy;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "engagements", allowSetters = true)
    private Client client;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "engagements", allowSetters = true)
    private Compte compte;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getScoring() {
        return scoring;
    }

    public Engagement scoring(Integer scoring) {
        this.scoring = scoring;
        return this;
    }

    public void setScoring(Integer scoring) {
        this.scoring = scoring;
    }

    public String getSubject() {
        return subject;
    }

    public Engagement subject(String subject) {
        this.subject = subject;
        return this;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Engagement amount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getDelay() {
        return delay;
    }

    public Engagement delay(Integer delay) {
        this.delay = delay;
        return this;
    }

    public void setDelay(Integer delay) {
        this.delay = delay;
    }

    public Echeance getPayment() {
        return payment;
    }

    public Engagement payment(Echeance payment) {
        this.payment = payment;
        return this;
    }

    public void setPayment(Echeance payment) {
        this.payment = payment;
    }

    public Integer getDelayed() {
        return delayed;
    }

    public Engagement delayed(Integer delayed) {
        this.delayed = delayed;
        return this;
    }

    public void setDelayed(Integer delayed) {
        this.delayed = delayed;
    }

    public ModeRembourssement getReimbursement() {
        return reimbursement;
    }

    public Engagement reimbursement(ModeRembourssement reimbursement) {
        this.reimbursement = reimbursement;
        return this;
    }

    public void setReimbursement(ModeRembourssement reimbursement) {
        this.reimbursement = reimbursement;
    }

    public String getAnalyse() {
        return analyse;
    }

    public Engagement analyse(String analyse) {
        this.analyse = analyse;
        return this;
    }

    public void setAnalyse(String analyse) {
        this.analyse = analyse;
    }

    public Boolean isIsStop() {
        return isStop;
    }

    public Engagement isStop(Boolean isStop) {
        this.isStop = isStop;
        return this;
    }

    public void setIsStop(Boolean isStop) {
        this.isStop = isStop;
    }

    public Boolean isIsDeleted() {
        return isDeleted;
    }

    public Engagement isDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
        return this;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public Engagement startDate(Instant startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEnDate() {
        return enDate;
    }

    public Engagement enDate(Instant enDate) {
        this.enDate = enDate;
        return this;
    }

    public void setEnDate(Instant enDate) {
        this.enDate = enDate;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public Engagement createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getUpdatedDate() {
        return updatedDate;
    }

    public Engagement updatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
        return this;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Instant getDeletedDate() {
        return deletedDate;
    }

    public Engagement deletedDate(Instant deletedDate) {
        this.deletedDate = deletedDate;
        return this;
    }

    public void setDeletedDate(Instant deletedDate) {
        this.deletedDate = deletedDate;
    }

    public Instant getStopedDate() {
        return stopedDate;
    }

    public Engagement stopedDate(Instant stopedDate) {
        this.stopedDate = stopedDate;
        return this;
    }

    public void setStopedDate(Instant stopedDate) {
        this.stopedDate = stopedDate;
    }

    public Task getCurrentTask() {
        return currentTask;
    }

    public Engagement currentTask(Task task) {
        this.currentTask = task;
        return this;
    }

    public void setCurrentTask(Task task) {
        this.currentTask = task;
    }

    public Set<Downgrading> getDowngradings() {
        return downgradings;
    }

    public Engagement downgradings(Set<Downgrading> downgradings) {
        this.downgradings = downgradings;
        return this;
    }

    public Engagement addDowngrading(Downgrading downgrading) {
        this.downgradings.add(downgrading);
        downgrading.setEngagement(this);
        return this;
    }

    public Engagement removeDowngrading(Downgrading downgrading) {
        this.downgradings.remove(downgrading);
        downgrading.setEngagement(null);
        return this;
    }

    public void setDowngradings(Set<Downgrading> downgradings) {
        this.downgradings = downgradings;
    }

    public Set<Reject> getRejects() {
        return rejects;
    }

    public Engagement rejects(Set<Reject> rejects) {
        this.rejects = rejects;
        return this;
    }

    public Engagement addReject(Reject reject) {
        this.rejects.add(reject);
        reject.setEngagement(this);
        return this;
    }

    public Engagement removeReject(Reject reject) {
        this.rejects.remove(reject);
        reject.setEngagement(null);
        return this;
    }

    public void setRejects(Set<Reject> rejects) {
        this.rejects = rejects;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public Engagement tasks(Set<Task> tasks) {
        this.tasks = tasks;
        return this;
    }

    public Engagement addTask(Task task) {
        this.tasks.add(task);
        task.setEngagement(this);
        return this;
    }

    public Engagement removeTask(Task task) {
        this.tasks.remove(task);
        task.setEngagement(null);
        return this;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    public Set<Notes> getNotes() {
        return notes;
    }

    public Engagement notes(Set<Notes> notes) {
        this.notes = notes;
        return this;
    }

    public Engagement addNote(Notes notes) {
        this.notes.add(notes);
        notes.setEngagement(this);
        return this;
    }

    public Engagement removeNote(Notes notes) {
        this.notes.remove(notes);
        notes.setEngagement(null);
        return this;
    }

    public void setNotes(Set<Notes> notes) {
        this.notes = notes;
    }

    public EngagementType getType() {
        return type;
    }

    public Engagement type(EngagementType engagementType) {
        this.type = engagementType;
        return this;
    }

    public void setType(EngagementType engagementType) {
        this.type = engagementType;
    }

    public Status getStatus() {
        return status;
    }

    public Engagement status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Status getDecision() {
        return decision;
    }

    public Engagement decision(Status status) {
        this.decision = status;
        return this;
    }

    public void setDecision(Status status) {
        this.decision = status;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public Engagement createdBy(User user) {
        this.createdBy = user;
        return this;
    }

    public void setCreatedBy(User user) {
        this.createdBy = user;
    }

    public Client getClient() {
        return client;
    }

    public Engagement client(Client client) {
        this.client = client;
        return this;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Compte getCompte() {
        return compte;
    }

    public Engagement compte(Compte compte) {
        this.compte = compte;
        return this;
    }

    public void setCompte(Compte compte) {
        this.compte = compte;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Engagement)) {
            return false;
        }
        return id != null && id.equals(((Engagement) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Engagement{" +
            "id=" + getId() +
            ", scoring=" + getScoring() +
            ", subject='" + getSubject() + "'" +
            ", amount=" + getAmount() +
            ", delay=" + getDelay() +
            ", payment='" + getPayment() + "'" +
            ", delayed=" + getDelayed() +
            ", reimbursement='" + getReimbursement() + "'" +
            ", analyse='" + getAnalyse() + "'" +
            ", isStop='" + isIsStop() + "'" +
            ", isDeleted='" + isIsDeleted() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", enDate='" + getEnDate() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            ", deletedDate='" + getDeletedDate() + "'" +
            ", stopedDate='" + getStopedDate() + "'" +
            "}";
    }
}
