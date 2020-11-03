import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITasktypeStatusAction } from 'app/shared/model/tasktype-status-action.model';

type EntityResponseType = HttpResponse<ITasktypeStatusAction>;
type EntityArrayResponseType = HttpResponse<ITasktypeStatusAction[]>;

@Injectable({ providedIn: 'root' })
export class TasktypeStatusActionService {
  public resourceUrl = SERVER_API_URL + 'api/tasktype-status-actions';

  constructor(protected http: HttpClient) {}

  create(tasktypeStatusAction: ITasktypeStatusAction): Observable<EntityResponseType> {
    return this.http.post<ITasktypeStatusAction>(this.resourceUrl, tasktypeStatusAction, { observe: 'response' });
  }

  update(tasktypeStatusAction: ITasktypeStatusAction): Observable<EntityResponseType> {
    return this.http.put<ITasktypeStatusAction>(this.resourceUrl, tasktypeStatusAction, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITasktypeStatusAction>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITasktypeStatusAction[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
