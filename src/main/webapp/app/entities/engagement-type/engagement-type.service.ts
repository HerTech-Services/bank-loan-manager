import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEngagementType } from 'app/shared/model/engagement-type.model';

type EntityResponseType = HttpResponse<IEngagementType>;
type EntityArrayResponseType = HttpResponse<IEngagementType[]>;

@Injectable({ providedIn: 'root' })
export class EngagementTypeService {
  public resourceUrl = SERVER_API_URL + 'api/engagement-types';

  constructor(protected http: HttpClient) {}

  create(engagementType: IEngagementType): Observable<EntityResponseType> {
    return this.http.post<IEngagementType>(this.resourceUrl, engagementType, { observe: 'response' });
  }

  update(engagementType: IEngagementType): Observable<EntityResponseType> {
    return this.http.put<IEngagementType>(this.resourceUrl, engagementType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEngagementType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEngagementType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
