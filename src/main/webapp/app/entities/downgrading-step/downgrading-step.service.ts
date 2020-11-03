import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDowngradingStep } from 'app/shared/model/downgrading-step.model';

type EntityResponseType = HttpResponse<IDowngradingStep>;
type EntityArrayResponseType = HttpResponse<IDowngradingStep[]>;

@Injectable({ providedIn: 'root' })
export class DowngradingStepService {
  public resourceUrl = SERVER_API_URL + 'api/downgrading-steps';

  constructor(protected http: HttpClient) {}

  create(downgradingStep: IDowngradingStep): Observable<EntityResponseType> {
    return this.http.post<IDowngradingStep>(this.resourceUrl, downgradingStep, { observe: 'response' });
  }

  update(downgradingStep: IDowngradingStep): Observable<EntityResponseType> {
    return this.http.put<IDowngradingStep>(this.resourceUrl, downgradingStep, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDowngradingStep>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDowngradingStep[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
