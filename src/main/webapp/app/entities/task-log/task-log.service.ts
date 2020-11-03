import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITaskLog } from 'app/shared/model/task-log.model';

type EntityResponseType = HttpResponse<ITaskLog>;
type EntityArrayResponseType = HttpResponse<ITaskLog[]>;

@Injectable({ providedIn: 'root' })
export class TaskLogService {
  public resourceUrl = SERVER_API_URL + 'api/task-logs';

  constructor(protected http: HttpClient) {}

  create(taskLog: ITaskLog): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(taskLog);
    return this.http
      .post<ITaskLog>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(taskLog: ITaskLog): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(taskLog);
    return this.http
      .put<ITaskLog>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITaskLog>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITaskLog[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(taskLog: ITaskLog): ITaskLog {
    const copy: ITaskLog = Object.assign({}, taskLog, {
      createdDate: taskLog.createdDate && taskLog.createdDate.isValid() ? taskLog.createdDate.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createdDate = res.body.createdDate ? moment(res.body.createdDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((taskLog: ITaskLog) => {
        taskLog.createdDate = taskLog.createdDate ? moment(taskLog.createdDate) : undefined;
      });
    }
    return res;
  }
}
