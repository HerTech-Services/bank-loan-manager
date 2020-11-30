import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEmploye } from 'app/shared/model/employe.model';
import { IUser } from 'app/core/user/user.model';
import { LogService } from 'app/log.service';

type EntityResponseType = HttpResponse<IEmploye>;
type EntityArrayResponseType = HttpResponse<IEmploye[]>;

@Injectable({ providedIn: 'root' })
export class EmployeService {
  public resourceUrl = SERVER_API_URL + 'api/employes';

  constructor(protected http: HttpClient, protected logService: LogService) {}

  create(employe: IEmploye): Observable<EntityResponseType> {
    return this.http.post<IEmploye>(this.resourceUrl, employe, { observe: 'response' });
  }

  createEmployeUser(employe: IEmploye, user: IUser): Observable<EntityResponseType> {
    const resourceUserUrl = this.resourceUrl + '/' + employe.id + '/user';
    this.logService.log(resourceUserUrl);
    this.logService.log(user);
    return this.http.post<IEmploye>(resourceUserUrl, user, { observe: 'response' });
  }

  update(employe: IEmploye): Observable<EntityResponseType> {
    return this.http.put<IEmploye>(this.resourceUrl, employe, { observe: 'response' });
  }

  updateEmployeUser(employe: IEmploye, user: IUser): Observable<EntityResponseType> {
    const resourceUserUrl = this.resourceUrl + '/' + employe.id + '/user';
    this.logService.log(resourceUserUrl);
    this.logService.log(user);
    return this.http.put<IEmploye>(resourceUserUrl, user, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEmploye>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEmploye[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
