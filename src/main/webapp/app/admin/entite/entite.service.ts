import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEntite } from 'app/shared/model/entite.model';
import { LogService } from 'app/log.service';
import { CustomTreeviewItem } from 'app/shared/ui/custom/treeview.item';

type EntityResponseType = HttpResponse<IEntite>;
type EntityArrayResponseType = HttpResponse<IEntite[]>;

@Injectable({ providedIn: 'root' })
export class EntiteService {
  public resourceUrl = SERVER_API_URL + 'api/entites';

  constructor(protected http: HttpClient, protected logService: LogService) {}

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

  getEntiteAsTree(): Observable<any[]> {
    return this.http.get<IEntite[]>(SERVER_API_URL + 'api/entites/tree').pipe(
      map((data: IEntite[]) => {
        const entiteRootList: IEntite[] = data.filter(entite => entite.parent === null);
        const root: any[] = [];
        entiteRootList.forEach((entite: IEntite) => {
          root.push({
            name: entite.label!,
            id: entite.id!,
            children: this.getChildrenEntite(entite.id!, data),
          });
        });
        return root;
      })
    );
  }

  getChildrenEntite(parentId: number, entiteList: IEntite[]): CustomTreeviewItem[] {
    const entiteChildrenList: IEntite[] = entiteList.filter(entite => entite.parent === parentId);
    const entiteTree: any[] = [];
    entiteChildrenList.forEach((entite: IEntite) => {
      entiteTree.push({
        name: entite.label!,
        id: entite.id!,
        children: this.getChildrenEntite(entite.id!, entiteList),
      });
    });
    return entiteTree;
  }

  getBooks(): CustomTreeviewItem[] | null {
    const childrenCategory = new CustomTreeviewItem({
      text: 'Children',
      value: 1,
      collapsed: true,
      children: [
        { text: 'Baby 3-5', value: 11 },
        { text: 'Baby 6-8', value: 12 },
        { text: 'Baby 9-12', value: 13 },
      ],
    });
    const itCategory = new CustomTreeviewItem({
      text: 'IT',
      value: 9,
      children: [
        {
          text: 'Programming',
          value: 91,
          children: [
            {
              text: 'Frontend',
              value: 911,
              children: [
                { text: 'Angular 1', value: 9111 },
                { text: 'Angular 2', value: 9112 },
                { text: 'ReactJS', value: 9113, disabled: true },
              ],
            },
            {
              text: 'Backend',
              value: 912,
              children: [
                { text: 'C#', value: 9121 },
                { text: 'Java', value: 9122 },
                { text: 'Python', value: 9123, checked: false, disabled: true },
              ],
            },
          ],
        },
        {
          text: 'Networking',
          value: 92,
          children: [
            { text: 'Internet', value: 921 },
            { text: 'Security', value: 922 },
          ],
        },
      ],
    });
    const teenCategory = new CustomTreeviewItem({
      text: 'Teen',
      value: 2,
      collapsed: true,
      disabled: true,
      children: [
        { text: 'Adventure', value: 21 },
        { text: 'Science', value: 22 },
      ],
    });
    const othersCategory = new CustomTreeviewItem({ text: 'Others', value: 3, checked: false, disabled: true });
    return [childrenCategory, itCategory, teenCategory, othersCategory];
  }
}
