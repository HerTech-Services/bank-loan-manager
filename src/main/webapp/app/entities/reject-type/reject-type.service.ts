import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IRejectType } from 'app/shared/model/reject-type.model';

type EntityResponseType = HttpResponse<IRejectType>;
type EntityArrayResponseType = HttpResponse<IRejectType[]>;

@Injectable({ providedIn: 'root' })
export class RejectTypeService {
  public resourceUrl = SERVER_API_URL + 'api/reject-types';

  constructor(protected http: HttpClient) {}

  create(rejectType: IRejectType): Observable<EntityResponseType> {
    return this.http.post<IRejectType>(this.resourceUrl, rejectType, { observe: 'response' });
  }

  update(rejectType: IRejectType): Observable<EntityResponseType> {
    return this.http.put<IRejectType>(this.resourceUrl, rejectType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRejectType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRejectType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
