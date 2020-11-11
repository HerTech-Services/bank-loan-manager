import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEngagement } from 'app/shared/model/engagement.model';

type EntityResponseType = HttpResponse<IEngagement>;
type EntityArrayResponseType = HttpResponse<IEngagement[]>;

@Injectable({ providedIn: 'root' })
export class EngagementService {
  public resourceUrl = SERVER_API_URL + 'api/engagements';

  constructor(protected http: HttpClient) {}

  create(engagement: IEngagement): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(engagement);
    return this.http
      .post<IEngagement>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(engagement: IEngagement): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(engagement);
    return this.http
      .put<IEngagement>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IEngagement>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IEngagement[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(engagement: IEngagement): IEngagement {
    const copy: IEngagement = Object.assign({}, engagement, {
      startDate: engagement.startDate && engagement.startDate.isValid() ? engagement.startDate.format(DATE_FORMAT) : undefined,
      enDate: engagement.enDate && engagement.enDate.isValid() ? engagement.enDate.format(DATE_FORMAT) : undefined,
      createdDate: engagement.createdDate && engagement.createdDate.isValid() ? engagement.createdDate.toJSON() : undefined,
      updatedDate: engagement.updatedDate && engagement.updatedDate.isValid() ? engagement.updatedDate.toJSON() : undefined,
      deletedDate: engagement.deletedDate && engagement.deletedDate.isValid() ? engagement.deletedDate.toJSON() : undefined,
      stopedDate: engagement.stopedDate && engagement.stopedDate.isValid() ? engagement.stopedDate.toJSON() : undefined,
      submissionDate:
        engagement.submissionDate && engagement.submissionDate.isValid() ? engagement.submissionDate.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.startDate = res.body.startDate ? moment(res.body.startDate) : undefined;
      res.body.enDate = res.body.enDate ? moment(res.body.enDate) : undefined;
      res.body.createdDate = res.body.createdDate ? moment(res.body.createdDate) : undefined;
      res.body.updatedDate = res.body.updatedDate ? moment(res.body.updatedDate) : undefined;
      res.body.deletedDate = res.body.deletedDate ? moment(res.body.deletedDate) : undefined;
      res.body.stopedDate = res.body.stopedDate ? moment(res.body.stopedDate) : undefined;
      res.body.submissionDate = res.body.submissionDate ? moment(res.body.submissionDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((engagement: IEngagement) => {
        engagement.startDate = engagement.startDate ? moment(engagement.startDate) : undefined;
        engagement.enDate = engagement.enDate ? moment(engagement.enDate) : undefined;
        engagement.createdDate = engagement.createdDate ? moment(engagement.createdDate) : undefined;
        engagement.updatedDate = engagement.updatedDate ? moment(engagement.updatedDate) : undefined;
        engagement.deletedDate = engagement.deletedDate ? moment(engagement.deletedDate) : undefined;
        engagement.stopedDate = engagement.stopedDate ? moment(engagement.stopedDate) : undefined;
        engagement.submissionDate = engagement.submissionDate ? moment(engagement.submissionDate) : undefined;
      });
    }
    return res;
  }
}
