import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IReject, Reject } from 'app/shared/model/reject.model';
import { RejectService } from './reject.service';
import { RejectComponent } from './reject.component';
import { RejectDetailComponent } from './reject-detail.component';
import { RejectUpdateComponent } from './reject-update.component';

@Injectable({ providedIn: 'root' })
export class RejectResolve implements Resolve<IReject> {
  constructor(private service: RejectService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IReject> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((reject: HttpResponse<Reject>) => {
          if (reject.body) {
            return of(reject.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Reject());
  }
}

export const rejectRoute: Routes = [
  {
    path: '',
    component: RejectComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'bankLoanManagerApp.reject.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RejectDetailComponent,
    resolve: {
      reject: RejectResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bankLoanManagerApp.reject.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RejectUpdateComponent,
    resolve: {
      reject: RejectResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bankLoanManagerApp.reject.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RejectUpdateComponent,
    resolve: {
      reject: RejectResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bankLoanManagerApp.reject.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
