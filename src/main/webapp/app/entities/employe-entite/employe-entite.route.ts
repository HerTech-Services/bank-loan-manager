import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEmployeEntite, EmployeEntite } from 'app/shared/model/employe-entite.model';
import { EmployeEntiteService } from './employe-entite.service';
import { EmployeEntiteComponent } from './employe-entite.component';
import { EmployeEntiteDetailComponent } from './employe-entite-detail.component';
import { EmployeEntiteUpdateComponent } from './employe-entite-update.component';

@Injectable({ providedIn: 'root' })
export class EmployeEntiteResolve implements Resolve<IEmployeEntite> {
  constructor(private service: EmployeEntiteService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEmployeEntite> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((employeEntite: HttpResponse<EmployeEntite>) => {
          if (employeEntite.body) {
            return of(employeEntite.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EmployeEntite());
  }
}

export const employeEntiteRoute: Routes = [
  {
    path: '',
    component: EmployeEntiteComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'bankLoanManagerApp.employeEntite.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EmployeEntiteDetailComponent,
    resolve: {
      employeEntite: EmployeEntiteResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bankLoanManagerApp.employeEntite.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EmployeEntiteUpdateComponent,
    resolve: {
      employeEntite: EmployeEntiteResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bankLoanManagerApp.employeEntite.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EmployeEntiteUpdateComponent,
    resolve: {
      employeEntite: EmployeEntiteResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bankLoanManagerApp.employeEntite.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
