import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAttachment } from 'app/shared/model/attachment.model';

type EntityResponseType = HttpResponse<IAttachment>;
type EntityArrayResponseType = HttpResponse<IAttachment[]>;

@Injectable({ providedIn: 'root' })
export class AttachmentService {
  public resourceUrl = SERVER_API_URL + 'api/attachments';

  constructor(protected http: HttpClient) {}

  create(attachment: IAttachment): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(attachment);
    return this.http
      .post<IAttachment>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(attachment: IAttachment): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(attachment);
    return this.http
      .put<IAttachment>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAttachment>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAttachment[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(attachment: IAttachment): IAttachment {
    const copy: IAttachment = Object.assign({}, attachment, {
      createdDate: attachment.createdDate && attachment.createdDate.isValid() ? attachment.createdDate.toJSON() : undefined,
      updatedDate: attachment.updatedDate && attachment.updatedDate.isValid() ? attachment.updatedDate.toJSON() : undefined,
      validationDate: attachment.validationDate && attachment.validationDate.isValid() ? attachment.validationDate.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createdDate = res.body.createdDate ? moment(res.body.createdDate) : undefined;
      res.body.updatedDate = res.body.updatedDate ? moment(res.body.updatedDate) : undefined;
      res.body.validationDate = res.body.validationDate ? moment(res.body.validationDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((attachment: IAttachment) => {
        attachment.createdDate = attachment.createdDate ? moment(attachment.createdDate) : undefined;
        attachment.updatedDate = attachment.updatedDate ? moment(attachment.updatedDate) : undefined;
        attachment.validationDate = attachment.validationDate ? moment(attachment.validationDate) : undefined;
      });
    }
    return res;
  }
}
