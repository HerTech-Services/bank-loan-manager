package tect.her.ccm.service.dto;

import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BigDecimalFilter;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.InstantFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LocalDateFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import java.io.Serializable;
import java.util.Objects;
import tect.her.ccm.domain.enumeration.Echeance;
import tect.her.ccm.domain.enumeration.ModeRembourssement;

/**
 * Criteria class for the {@link tect.her.ccm.domain.Engagement} entity. This class is used
 * in {@link tect.her.ccm.web.rest.EngagementResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /engagements?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EngagementCriteria implements Serializable, Criteria {

    /**
     * Class for filtering Echeance
     */
    public static class EcheanceFilter extends Filter<Echeance> {

        public EcheanceFilter() {}

        public EcheanceFilter(EcheanceFilter filter) {
            super(filter);
        }

        @Override
        public EcheanceFilter copy() {
            return new EcheanceFilter(this);
        }
    }

    /**
     * Class for filtering ModeRembourssement
     */
    public static class ModeRembourssementFilter extends Filter<ModeRembourssement> {

        public ModeRembourssementFilter() {}

        public ModeRembourssementFilter(ModeRembourssementFilter filter) {
            super(filter);
        }

        @Override
        public ModeRembourssementFilter copy() {
            return new ModeRembourssementFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter identifier;

    private StringFilter scoring;

    private StringFilter subject;

    private BigDecimalFilter amount;

    private IntegerFilter delay;

    private EcheanceFilter payment;

    private IntegerFilter delayed;

    private ModeRembourssementFilter reimbursement;

    private BooleanFilter isStop;

    private BooleanFilter isDeleted;

    private LocalDateFilter startDate;

    private LocalDateFilter enDate;

    private InstantFilter createdDate;

    private InstantFilter updatedDate;

    private InstantFilter deletedDate;

    private InstantFilter stopedDate;

    private LocalDateFilter submissionDate;

    private LongFilter currentTaskId;

    private LongFilter downgradingId;

    private LongFilter rejectId;

    private LongFilter taskId;

    private LongFilter noteId;

    private LongFilter typeId;

    private LongFilter statusId;

    private LongFilter decisionId;

    private LongFilter createdById;

    private LongFilter clientId;

    private LongFilter compteId;

    public EngagementCriteria() {}

    public EngagementCriteria(EngagementCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.identifier = other.identifier == null ? null : other.identifier.copy();
        this.scoring = other.scoring == null ? null : other.scoring.copy();
        this.subject = other.subject == null ? null : other.subject.copy();
        this.amount = other.amount == null ? null : other.amount.copy();
        this.delay = other.delay == null ? null : other.delay.copy();
        this.payment = other.payment == null ? null : other.payment.copy();
        this.delayed = other.delayed == null ? null : other.delayed.copy();
        this.reimbursement = other.reimbursement == null ? null : other.reimbursement.copy();
        this.isStop = other.isStop == null ? null : other.isStop.copy();
        this.isDeleted = other.isDeleted == null ? null : other.isDeleted.copy();
        this.startDate = other.startDate == null ? null : other.startDate.copy();
        this.enDate = other.enDate == null ? null : other.enDate.copy();
        this.createdDate = other.createdDate == null ? null : other.createdDate.copy();
        this.updatedDate = other.updatedDate == null ? null : other.updatedDate.copy();
        this.deletedDate = other.deletedDate == null ? null : other.deletedDate.copy();
        this.stopedDate = other.stopedDate == null ? null : other.stopedDate.copy();
        this.submissionDate = other.submissionDate == null ? null : other.submissionDate.copy();
        this.currentTaskId = other.currentTaskId == null ? null : other.currentTaskId.copy();
        this.downgradingId = other.downgradingId == null ? null : other.downgradingId.copy();
        this.rejectId = other.rejectId == null ? null : other.rejectId.copy();
        this.taskId = other.taskId == null ? null : other.taskId.copy();
        this.noteId = other.noteId == null ? null : other.noteId.copy();
        this.typeId = other.typeId == null ? null : other.typeId.copy();
        this.statusId = other.statusId == null ? null : other.statusId.copy();
        this.decisionId = other.decisionId == null ? null : other.decisionId.copy();
        this.createdById = other.createdById == null ? null : other.createdById.copy();
        this.clientId = other.clientId == null ? null : other.clientId.copy();
        this.compteId = other.compteId == null ? null : other.compteId.copy();
    }

    @Override
    public EngagementCriteria copy() {
        return new EngagementCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getIdentifier() {
        return identifier;
    }

    public void setIdentifier(StringFilter identifier) {
        this.identifier = identifier;
    }

    public StringFilter getScoring() {
        return scoring;
    }

    public void setScoring(StringFilter scoring) {
        this.scoring = scoring;
    }

    public StringFilter getSubject() {
        return subject;
    }

    public void setSubject(StringFilter subject) {
        this.subject = subject;
    }

    public BigDecimalFilter getAmount() {
        return amount;
    }

    public void setAmount(BigDecimalFilter amount) {
        this.amount = amount;
    }

    public IntegerFilter getDelay() {
        return delay;
    }

    public void setDelay(IntegerFilter delay) {
        this.delay = delay;
    }

    public EcheanceFilter getPayment() {
        return payment;
    }

    public void setPayment(EcheanceFilter payment) {
        this.payment = payment;
    }

    public IntegerFilter getDelayed() {
        return delayed;
    }

    public void setDelayed(IntegerFilter delayed) {
        this.delayed = delayed;
    }

    public ModeRembourssementFilter getReimbursement() {
        return reimbursement;
    }

    public void setReimbursement(ModeRembourssementFilter reimbursement) {
        this.reimbursement = reimbursement;
    }

    public BooleanFilter getIsStop() {
        return isStop;
    }

    public void setIsStop(BooleanFilter isStop) {
        this.isStop = isStop;
    }

    public BooleanFilter getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(BooleanFilter isDeleted) {
        this.isDeleted = isDeleted;
    }

    public LocalDateFilter getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateFilter startDate) {
        this.startDate = startDate;
    }

    public LocalDateFilter getEnDate() {
        return enDate;
    }

    public void setEnDate(LocalDateFilter enDate) {
        this.enDate = enDate;
    }

    public InstantFilter getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(InstantFilter createdDate) {
        this.createdDate = createdDate;
    }

    public InstantFilter getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(InstantFilter updatedDate) {
        this.updatedDate = updatedDate;
    }

    public InstantFilter getDeletedDate() {
        return deletedDate;
    }

    public void setDeletedDate(InstantFilter deletedDate) {
        this.deletedDate = deletedDate;
    }

    public InstantFilter getStopedDate() {
        return stopedDate;
    }

    public void setStopedDate(InstantFilter stopedDate) {
        this.stopedDate = stopedDate;
    }

    public LocalDateFilter getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(LocalDateFilter submissionDate) {
        this.submissionDate = submissionDate;
    }

    public LongFilter getCurrentTaskId() {
        return currentTaskId;
    }

    public void setCurrentTaskId(LongFilter currentTaskId) {
        this.currentTaskId = currentTaskId;
    }

    public LongFilter getDowngradingId() {
        return downgradingId;
    }

    public void setDowngradingId(LongFilter downgradingId) {
        this.downgradingId = downgradingId;
    }

    public LongFilter getRejectId() {
        return rejectId;
    }

    public void setRejectId(LongFilter rejectId) {
        this.rejectId = rejectId;
    }

    public LongFilter getTaskId() {
        return taskId;
    }

    public void setTaskId(LongFilter taskId) {
        this.taskId = taskId;
    }

    public LongFilter getNoteId() {
        return noteId;
    }

    public void setNoteId(LongFilter noteId) {
        this.noteId = noteId;
    }

    public LongFilter getTypeId() {
        return typeId;
    }

    public void setTypeId(LongFilter typeId) {
        this.typeId = typeId;
    }

    public LongFilter getStatusId() {
        return statusId;
    }

    public void setStatusId(LongFilter statusId) {
        this.statusId = statusId;
    }

    public LongFilter getDecisionId() {
        return decisionId;
    }

    public void setDecisionId(LongFilter decisionId) {
        this.decisionId = decisionId;
    }

    public LongFilter getCreatedById() {
        return createdById;
    }

    public void setCreatedById(LongFilter createdById) {
        this.createdById = createdById;
    }

    public LongFilter getClientId() {
        return clientId;
    }

    public void setClientId(LongFilter clientId) {
        this.clientId = clientId;
    }

    public LongFilter getCompteId() {
        return compteId;
    }

    public void setCompteId(LongFilter compteId) {
        this.compteId = compteId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final EngagementCriteria that = (EngagementCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(identifier, that.identifier) &&
            Objects.equals(scoring, that.scoring) &&
            Objects.equals(subject, that.subject) &&
            Objects.equals(amount, that.amount) &&
            Objects.equals(delay, that.delay) &&
            Objects.equals(payment, that.payment) &&
            Objects.equals(delayed, that.delayed) &&
            Objects.equals(reimbursement, that.reimbursement) &&
            Objects.equals(isStop, that.isStop) &&
            Objects.equals(isDeleted, that.isDeleted) &&
            Objects.equals(startDate, that.startDate) &&
            Objects.equals(enDate, that.enDate) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(updatedDate, that.updatedDate) &&
            Objects.equals(deletedDate, that.deletedDate) &&
            Objects.equals(stopedDate, that.stopedDate) &&
            Objects.equals(submissionDate, that.submissionDate) &&
            Objects.equals(currentTaskId, that.currentTaskId) &&
            Objects.equals(downgradingId, that.downgradingId) &&
            Objects.equals(rejectId, that.rejectId) &&
            Objects.equals(taskId, that.taskId) &&
            Objects.equals(noteId, that.noteId) &&
            Objects.equals(typeId, that.typeId) &&
            Objects.equals(statusId, that.statusId) &&
            Objects.equals(decisionId, that.decisionId) &&
            Objects.equals(createdById, that.createdById) &&
            Objects.equals(clientId, that.clientId) &&
            Objects.equals(compteId, that.compteId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            identifier,
            scoring,
            subject,
            amount,
            delay,
            payment,
            delayed,
            reimbursement,
            isStop,
            isDeleted,
            startDate,
            enDate,
            createdDate,
            updatedDate,
            deletedDate,
            stopedDate,
            submissionDate,
            currentTaskId,
            downgradingId,
            rejectId,
            taskId,
            noteId,
            typeId,
            statusId,
            decisionId,
            createdById,
            clientId,
            compteId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EngagementCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (identifier != null ? "identifier=" + identifier + ", " : "") +
                (scoring != null ? "scoring=" + scoring + ", " : "") +
                (subject != null ? "subject=" + subject + ", " : "") +
                (amount != null ? "amount=" + amount + ", " : "") +
                (delay != null ? "delay=" + delay + ", " : "") +
                (payment != null ? "payment=" + payment + ", " : "") +
                (delayed != null ? "delayed=" + delayed + ", " : "") +
                (reimbursement != null ? "reimbursement=" + reimbursement + ", " : "") +
                (isStop != null ? "isStop=" + isStop + ", " : "") +
                (isDeleted != null ? "isDeleted=" + isDeleted + ", " : "") +
                (startDate != null ? "startDate=" + startDate + ", " : "") +
                (enDate != null ? "enDate=" + enDate + ", " : "") +
                (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
                (updatedDate != null ? "updatedDate=" + updatedDate + ", " : "") +
                (deletedDate != null ? "deletedDate=" + deletedDate + ", " : "") +
                (stopedDate != null ? "stopedDate=" + stopedDate + ", " : "") +
                (submissionDate != null ? "submissionDate=" + submissionDate + ", " : "") +
                (currentTaskId != null ? "currentTaskId=" + currentTaskId + ", " : "") +
                (downgradingId != null ? "downgradingId=" + downgradingId + ", " : "") +
                (rejectId != null ? "rejectId=" + rejectId + ", " : "") +
                (taskId != null ? "taskId=" + taskId + ", " : "") +
                (noteId != null ? "noteId=" + noteId + ", " : "") +
                (typeId != null ? "typeId=" + typeId + ", " : "") +
                (statusId != null ? "statusId=" + statusId + ", " : "") +
                (decisionId != null ? "decisionId=" + decisionId + ", " : "") +
                (createdById != null ? "createdById=" + createdById + ", " : "") +
                (clientId != null ? "clientId=" + clientId + ", " : "") +
                (compteId != null ? "compteId=" + compteId + ", " : "") +
            "}";
    }
}
