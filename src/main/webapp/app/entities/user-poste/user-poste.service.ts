import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IUserPoste } from 'app/shared/model/user-poste.model';

type EntityResponseType = HttpResponse<IUserPoste>;
type EntityArrayResponseType = HttpResponse<IUserPoste[]>;

@Injectable({ providedIn: 'root' })
export class UserPosteService {
  public resourceUrl = SERVER_API_URL + 'api/user-postes';

  constructor(protected http: HttpClient) {}

  create(userPoste: IUserPoste): Observable<EntityResponseType> {
    return this.http.post<IUserPoste>(this.resourceUrl, userPoste, { observe: 'response' });
  }

  update(userPoste: IUserPoste): Observable<EntityResponseType> {
    return this.http.put<IUserPoste>(this.resourceUrl, userPoste, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IUserPoste>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IUserPoste[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
