import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDowngrading } from 'app/shared/model/downgrading.model';

type EntityResponseType = HttpResponse<IDowngrading>;
type EntityArrayResponseType = HttpResponse<IDowngrading[]>;

@Injectable({ providedIn: 'root' })
export class DowngradingService {
  public resourceUrl = SERVER_API_URL + 'api/downgradings';

  constructor(protected http: HttpClient) {}

  create(downgrading: IDowngrading): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(downgrading);
    return this.http
      .post<IDowngrading>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(downgrading: IDowngrading): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(downgrading);
    return this.http
      .put<IDowngrading>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDowngrading>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDowngrading[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(downgrading: IDowngrading): IDowngrading {
    const copy: IDowngrading = Object.assign({}, downgrading, {
      createdDate: downgrading.createdDate && downgrading.createdDate.isValid() ? downgrading.createdDate.toJSON() : undefined,
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
      res.body.forEach((downgrading: IDowngrading) => {
        downgrading.createdDate = downgrading.createdDate ? moment(downgrading.createdDate) : undefined;
      });
    }
    return res;
  }
}
