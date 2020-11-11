import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEmployeEntite } from 'app/shared/model/employe-entite.model';

type EntityResponseType = HttpResponse<IEmployeEntite>;
type EntityArrayResponseType = HttpResponse<IEmployeEntite[]>;

@Injectable({ providedIn: 'root' })
export class EmployeEntiteService {
  public resourceUrl = SERVER_API_URL + 'api/employe-entites';

  constructor(protected http: HttpClient) {}

  create(employeEntite: IEmployeEntite): Observable<EntityResponseType> {
    return this.http.post<IEmployeEntite>(this.resourceUrl, employeEntite, { observe: 'response' });
  }

  update(employeEntite: IEmployeEntite): Observable<EntityResponseType> {
    return this.http.put<IEmployeEntite>(this.resourceUrl, employeEntite, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEmployeEntite>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEmployeEntite[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
