import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEntite, Entite } from 'app/shared/model/entite.model';
import { EntiteService } from './entite.service';
import { EntiteComponent } from './entite.component';
import { EntiteDetailComponent } from './entite-detail.component';
import { EntiteUpdateComponent } from './entite-update.component';

@Injectable({ providedIn: 'root' })
export class EntiteResolve implements Resolve<IEntite> {
  constructor(private service: EntiteService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEntite> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((entite: HttpResponse<Entite>) => {
          if (entite.body) {
            return of(entite.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Entite());
  }
}

export const entiteRoute: Routes = [
  {
    path: '',
    component: EntiteComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'bankLoanManagerApp.entite.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EntiteDetailComponent,
    resolve: {
      entite: EntiteResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bankLoanManagerApp.entite.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EntiteUpdateComponent,
    resolve: {
      entite: EntiteResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bankLoanManagerApp.entite.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EntiteUpdateComponent,
    resolve: {
      entite: EntiteResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bankLoanManagerApp.entite.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
