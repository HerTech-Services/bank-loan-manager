import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IFilelist } from 'app/shared/model/filelist.model';

type EntityResponseType = HttpResponse<IFilelist>;
type EntityArrayResponseType = HttpResponse<IFilelist[]>;

@Injectable({ providedIn: 'root' })
export class FilelistService {
  public resourceUrl = SERVER_API_URL + 'api/filelists';

  constructor(protected http: HttpClient) {}

  create(filelist: IFilelist): Observable<EntityResponseType> {
    return this.http.post<IFilelist>(this.resourceUrl, filelist, { observe: 'response' });
  }

  update(filelist: IFilelist): Observable<EntityResponseType> {
    return this.http.put<IFilelist>(this.resourceUrl, filelist, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFilelist>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFilelist[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
