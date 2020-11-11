import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITypeEntite, TypeEntite } from 'app/shared/model/type-entite.model';
import { TypeEntiteService } from './type-entite.service';
import { TypeEntiteComponent } from './type-entite.component';
import { TypeEntiteDetailComponent } from './type-entite-detail.component';
import { TypeEntiteUpdateComponent } from './type-entite-update.component';

@Injectable({ providedIn: 'root' })
export class TypeEntiteResolve implements Resolve<ITypeEntite> {
  constructor(private service: TypeEntiteService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITypeEntite> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((typeEntite: HttpResponse<TypeEntite>) => {
          if (typeEntite.body) {
            return of(typeEntite.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TypeEntite());
  }
}

export const typeEntiteRoute: Routes = [
  {
    path: '',
    component: TypeEntiteComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'bankLoanManagerApp.typeEntite.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TypeEntiteDetailComponent,
    resolve: {
      typeEntite: TypeEntiteResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bankLoanManagerApp.typeEntite.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TypeEntiteUpdateComponent,
    resolve: {
      typeEntite: TypeEntiteResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bankLoanManagerApp.typeEntite.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TypeEntiteUpdateComponent,
    resolve: {
      typeEntite: TypeEntiteResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bankLoanManagerApp.typeEntite.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
