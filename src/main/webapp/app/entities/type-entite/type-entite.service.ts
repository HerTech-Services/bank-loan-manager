import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITypeEntite } from 'app/shared/model/type-entite.model';

type EntityResponseType = HttpResponse<ITypeEntite>;
type EntityArrayResponseType = HttpResponse<ITypeEntite[]>;

@Injectable({ providedIn: 'root' })
export class TypeEntiteService {
  public resourceUrl = SERVER_API_URL + 'api/type-entites';

  constructor(protected http: HttpClient) {}

  create(typeEntite: ITypeEntite): Observable<EntityResponseType> {
    return this.http.post<ITypeEntite>(this.resourceUrl, typeEntite, { observe: 'response' });
  }

  update(typeEntite: ITypeEntite): Observable<EntityResponseType> {
    return this.http.put<ITypeEntite>(this.resourceUrl, typeEntite, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITypeEntite>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITypeEntite[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
