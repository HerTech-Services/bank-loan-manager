package tect.her.ccm.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import javax.persistence.Lob;
import javax.validation.constraints.*;
import tect.her.ccm.domain.enumeration.Echeance;
import tect.her.ccm.domain.enumeration.ModeRembourssement;

/**
 * A DTO for the {@link tect.her.ccm.domain.Engagement} entity.
 */
public class EngagementDTO implements Serializable {
    private Long id;

    @Size(max = 32)
    private String identifier;

    @NotNull
    private String scoring;

    @NotNull
    private String subject;

    @NotNull
    private BigDecimal amount;

    @NotNull
    @Min(value = 1)
    private Integer delay;

    @NotNull
    private Echeance payment;

    @NotNull
    @Min(value = 0)
    private Integer delayed;

    @NotNull
    private ModeRembourssement reimbursement;

    @Lob
    private String analyse;

    private Boolean isStop;

    private Boolean isDeleted;

    private LocalDate startDate;

    private LocalDate enDate;

    private Instant createdDate;

    private Instant updatedDate;

    private Instant deletedDate;

    private Instant stopedDate;

    private LocalDate submissionDate;

    private Long currentTaskId;

    private Long typeId;

    private String typeLabel;

    private Long statusId;

    private String statusLabel;

    private Long decisionId;

    private String decisionLabel;

    private Long createdById;

    private String createdByFirstName;

    private Long clientId;

    private String clientNomCli;

    private Long compteId;

    private String compteLibCpt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getScoring() {
        return scoring;
    }

    public void setScoring(String scoring) {
        this.scoring = scoring;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getDelay() {
        return delay;
    }

    public void setDelay(Integer delay) {
        this.delay = delay;
    }

    public Echeance getPayment() {
        return payment;
    }

    public void setPayment(Echeance payment) {
        this.payment = payment;
    }

    public Integer getDelayed() {
        return delayed;
    }

    public void setDelayed(Integer delayed) {
        this.delayed = delayed;
    }

    public ModeRembourssement getReimbursement() {
        return reimbursement;
    }

    public void setReimbursement(ModeRembourssement reimbursement) {
        this.reimbursement = reimbursement;
    }

    public String getAnalyse() {
        return analyse;
    }

    public void setAnalyse(String analyse) {
        this.analyse = analyse;
    }

    public Boolean isIsStop() {
        return isStop;
    }

    public void setIsStop(Boolean isStop) {
        this.isStop = isStop;
    }

    public Boolean isIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEnDate() {
        return enDate;
    }

    public void setEnDate(LocalDate enDate) {
        this.enDate = enDate;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Instant getDeletedDate() {
        return deletedDate;
    }

    public void setDeletedDate(Instant deletedDate) {
        this.deletedDate = deletedDate;
    }

    public Instant getStopedDate() {
        return stopedDate;
    }

    public void setStopedDate(Instant stopedDate) {
        this.stopedDate = stopedDate;
    }

    public LocalDate getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(LocalDate submissionDate) {
        this.submissionDate = submissionDate;
    }

    public Long getCurrentTaskId() {
        return currentTaskId;
    }

    public void setCurrentTaskId(Long taskId) {
        this.currentTaskId = taskId;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long engagementTypeId) {
        this.typeId = engagementTypeId;
    }

    public String getTypeLabel() {
        return typeLabel;
    }

    public void setTypeLabel(String engagementTypeLabel) {
        this.typeLabel = engagementTypeLabel;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public String getStatusLabel() {
        return statusLabel;
    }

    public void setStatusLabel(String statusLabel) {
        this.statusLabel = statusLabel;
    }

    public Long getDecisionId() {
        return decisionId;
    }

    public void setDecisionId(Long statusId) {
        this.decisionId = statusId;
    }

    public String getDecisionLabel() {
        return decisionLabel;
    }

    public void setDecisionLabel(String statusLabel) {
        this.decisionLabel = statusLabel;
    }

    public Long getCreatedById() {
        return createdById;
    }

    public void setCreatedById(Long userId) {
        this.createdById = userId;
    }

    public String getCreatedByFirstName() {
        return createdByFirstName;
    }

    public void setCreatedByFirstName(String userFirstName) {
        this.createdByFirstName = userFirstName;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getClientNomCli() {
        return clientNomCli;
    }

    public void setClientNomCli(String clientNomCli) {
        this.clientNomCli = clientNomCli;
    }

    public Long getCompteId() {
        return compteId;
    }

    public void setCompteId(Long compteId) {
        this.compteId = compteId;
    }

    public String getCompteLibCpt() {
        return compteLibCpt;
    }

    public void setCompteLibCpt(String compteLibCpt) {
        this.compteLibCpt = compteLibCpt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EngagementDTO)) {
            return false;
        }

        return id != null && id.equals(((EngagementDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EngagementDTO{" +
            "id=" + getId() +
            ", identifier='" + getIdentifier() + "'" +
            ", scoring='" + getScoring() + "'" +
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
            ", submissionDate='" + getSubmissionDate() + "'" +
            ", currentTaskId=" + getCurrentTaskId() +
            ", typeId=" + getTypeId() +
            ", typeLabel='" + getTypeLabel() + "'" +
            ", statusId=" + getStatusId() +
            ", statusLabel='" + getStatusLabel() + "'" +
            ", decisionId=" + getDecisionId() +
            ", decisionLabel='" + getDecisionLabel() + "'" +
            ", createdById=" + getCreatedById() +
            ", createdByFirstName='" + getCreatedByFirstName() + "'" +
            ", clientId=" + getClientId() +
            ", clientNomCli='" + getClientNomCli() + "'" +
            ", compteId=" + getCompteId() +
            ", compteLibCpt='" + getCompteLibCpt() + "'" +
            "}";
    }
}
