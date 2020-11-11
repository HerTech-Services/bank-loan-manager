package tect.her.ccm.service.dto;

import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BigDecimalFilter;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.InstantFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import java.io.Serializable;
import java.util.Objects;

/**
 * Criteria class for the {@link tect.her.ccm.domain.Reject} entity. This class is used
 * in {@link tect.her.ccm.web.rest.RejectResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /rejects?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class RejectCriteria implements Serializable, Criteria {
    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter createdDate;

    private BigDecimalFilter capital;

    private BigDecimalFilter interests;

    private BigDecimalFilter penalties;

    private BigDecimalFilter accessories;

    private LongFilter typeId;

    private LongFilter engagementId;

    public RejectCriteria() {}

    public RejectCriteria(RejectCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.createdDate = other.createdDate == null ? null : other.createdDate.copy();
        this.capital = other.capital == null ? null : other.capital.copy();
        this.interests = other.interests == null ? null : other.interests.copy();
        this.penalties = other.penalties == null ? null : other.penalties.copy();
        this.accessories = other.accessories == null ? null : other.accessories.copy();
        this.typeId = other.typeId == null ? null : other.typeId.copy();
        this.engagementId = other.engagementId == null ? null : other.engagementId.copy();
    }

    @Override
    public RejectCriteria copy() {
        return new RejectCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public InstantFilter getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(InstantFilter createdDate) {
        this.createdDate = createdDate;
    }

    public BigDecimalFilter getCapital() {
        return capital;
    }

    public void setCapital(BigDecimalFilter capital) {
        this.capital = capital;
    }

    public BigDecimalFilter getInterests() {
        return interests;
    }

    public void setInterests(BigDecimalFilter interests) {
        this.interests = interests;
    }

    public BigDecimalFilter getPenalties() {
        return penalties;
    }

    public void setPenalties(BigDecimalFilter penalties) {
        this.penalties = penalties;
    }

    public BigDecimalFilter getAccessories() {
        return accessories;
    }

    public void setAccessories(BigDecimalFilter accessories) {
        this.accessories = accessories;
    }

    public LongFilter getTypeId() {
        return typeId;
    }

    public void setTypeId(LongFilter typeId) {
        this.typeId = typeId;
    }

    public LongFilter getEngagementId() {
        return engagementId;
    }

    public void setEngagementId(LongFilter engagementId) {
        this.engagementId = engagementId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final RejectCriteria that = (RejectCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(capital, that.capital) &&
            Objects.equals(interests, that.interests) &&
            Objects.equals(penalties, that.penalties) &&
            Objects.equals(accessories, that.accessories) &&
            Objects.equals(typeId, that.typeId) &&
            Objects.equals(engagementId, that.engagementId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createdDate, capital, interests, penalties, accessories, typeId, engagementId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RejectCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
                (capital != null ? "capital=" + capital + ", " : "") +
                (interests != null ? "interests=" + interests + ", " : "") +
                (penalties != null ? "penalties=" + penalties + ", " : "") +
                (accessories != null ? "accessories=" + accessories + ", " : "") +
                (typeId != null ? "typeId=" + typeId + ", " : "") +
                (engagementId != null ? "engagementId=" + engagementId + ", " : "") +
            "}";
    }
}
