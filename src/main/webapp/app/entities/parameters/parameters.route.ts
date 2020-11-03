import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IParameters, Parameters } from 'app/shared/model/parameters.model';
import { ParametersService } from './parameters.service';
import { ParametersComponent } from './parameters.component';
import { ParametersDetailComponent } from './parameters-detail.component';
import { ParametersUpdateComponent } from './parameters-update.component';

@Injectable({ providedIn: 'root' })
export class ParametersResolve implements Resolve<IParameters> {
  constructor(private service: ParametersService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IParameters> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((parameters: HttpResponse<Parameters>) => {
          if (parameters.body) {
            return of(parameters.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Parameters());
  }
}

export const parametersRoute: Routes = [
  {
    path: '',
    component: ParametersComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'bankLoanManagerApp.parameters.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ParametersDetailComponent,
    resolve: {
      parameters: ParametersResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bankLoanManagerApp.parameters.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ParametersUpdateComponent,
    resolve: {
      parameters: ParametersResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bankLoanManagerApp.parameters.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ParametersUpdateComponent,
    resolve: {
      parameters: ParametersResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bankLoanManagerApp.parameters.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
