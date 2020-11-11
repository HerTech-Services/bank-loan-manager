import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEntite } from 'app/shared/model/entite.model';

type EntityResponseType = HttpResponse<IEntite>;
type EntityArrayResponseType = HttpResponse<IEntite[]>;

@Injectable({ providedIn: 'root' })
export class EntiteService {
  public resourceUrl = SERVER_API_URL + 'api/entites';

  constructor(protected http: HttpClient) {}

  create(entite: IEntite): Observable<EntityResponseType> {
    return this.http.post<IEntite>(this.resourceUrl, entite, { observe: 'response' });
  }

  update(entite: IEntite): Observable<EntityResponseType> {
    return this.http.put<IEntite>(this.resourceUrl, entite, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEntite>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEntite[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
