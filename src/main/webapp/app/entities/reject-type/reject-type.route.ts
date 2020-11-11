import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IRejectType, RejectType } from 'app/shared/model/reject-type.model';
import { RejectTypeService } from './reject-type.service';
import { RejectTypeComponent } from './reject-type.component';
import { RejectTypeDetailComponent } from './reject-type-detail.component';
import { RejectTypeUpdateComponent } from './reject-type-update.component';

@Injectable({ providedIn: 'root' })
export class RejectTypeResolve implements Resolve<IRejectType> {
  constructor(private service: RejectTypeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRejectType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((rejectType: HttpResponse<RejectType>) => {
          if (rejectType.body) {
            return of(rejectType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new RejectType());
  }
}

export const rejectTypeRoute: Routes = [
  {
    path: '',
    component: RejectTypeComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'bankLoanManagerApp.rejectType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RejectTypeDetailComponent,
    resolve: {
      rejectType: RejectTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bankLoanManagerApp.rejectType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RejectTypeUpdateComponent,
    resolve: {
      rejectType: RejectTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bankLoanManagerApp.rejectType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RejectTypeUpdateComponent,
    resolve: {
      rejectType: RejectTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bankLoanManagerApp.rejectType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
