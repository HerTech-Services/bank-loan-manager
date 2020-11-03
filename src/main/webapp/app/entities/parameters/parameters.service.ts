import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IParameters } from 'app/shared/model/parameters.model';

type EntityResponseType = HttpResponse<IParameters>;
type EntityArrayResponseType = HttpResponse<IParameters[]>;

@Injectable({ providedIn: 'root' })
export class ParametersService {
  public resourceUrl = SERVER_API_URL + 'api/parameters';

  constructor(protected http: HttpClient) {}

  create(parameters: IParameters): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(parameters);
    return this.http
      .post<IParameters>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(parameters: IParameters): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(parameters);
    return this.http
      .put<IParameters>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IParameters>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IParameters[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(parameters: IParameters): IParameters {
    const copy: IParameters = Object.assign({}, parameters, {
      paramValueDate: parameters.paramValueDate && parameters.paramValueDate.isValid() ? parameters.paramValueDate.toJSON() : undefined,
      updatedDate: parameters.updatedDate && parameters.updatedDate.isValid() ? parameters.updatedDate.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.paramValueDate = res.body.paramValueDate ? moment(res.body.paramValueDate) : undefined;
      res.body.updatedDate = res.body.updatedDate ? moment(res.body.updatedDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((parameters: IParameters) => {
        parameters.paramValueDate = parameters.paramValueDate ? moment(parameters.paramValueDate) : undefined;
        parameters.updatedDate = parameters.updatedDate ? moment(parameters.updatedDate) : undefined;
      });
    }
    return res;
  }
}
