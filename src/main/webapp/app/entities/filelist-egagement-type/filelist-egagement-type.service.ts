import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IFilelistEgagementType } from 'app/shared/model/filelist-egagement-type.model';

type EntityResponseType = HttpResponse<IFilelistEgagementType>;
type EntityArrayResponseType = HttpResponse<IFilelistEgagementType[]>;

@Injectable({ providedIn: 'root' })
export class FilelistEgagementTypeService {
  public resourceUrl = SERVER_API_URL + 'api/filelist-egagement-types';

  constructor(protected http: HttpClient) {}

  create(filelistEgagementType: IFilelistEgagementType): Observable<EntityResponseType> {
    return this.http.post<IFilelistEgagementType>(this.resourceUrl, filelistEgagementType, { observe: 'response' });
  }

  update(filelistEgagementType: IFilelistEgagementType): Observable<EntityResponseType> {
    return this.http.put<IFilelistEgagementType>(this.resourceUrl, filelistEgagementType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFilelistEgagementType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFilelistEgagementType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
