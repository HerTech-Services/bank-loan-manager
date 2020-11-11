import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IReject } from 'app/shared/model/reject.model';

type EntityResponseType = HttpResponse<IReject>;
type EntityArrayResponseType = HttpResponse<IReject[]>;

@Injectable({ providedIn: 'root' })
export class RejectService {
  public resourceUrl = SERVER_API_URL + 'api/rejects';

  constructor(protected http: HttpClient) {}

  create(reject: IReject): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(reject);
    return this.http
      .post<IReject>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(reject: IReject): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(reject);
    return this.http
      .put<IReject>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IReject>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IReject[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(reject: IReject): IReject {
    const copy: IReject = Object.assign({}, reject, {
      createdDate: reject.createdDate && reject.createdDate.isValid() ? reject.createdDate.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createdDate = res.body.createdDate ? moment(res.body.createdDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((reject: IReject) => {
        reject.createdDate = reject.createdDate ? moment(reject.createdDate) : undefined;
      });
    }
    return res;
  }
}
