import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IUserPoste, UserPoste } from 'app/shared/model/user-poste.model';
import { UserPosteService } from './user-poste.service';
import { UserPosteComponent } from './user-poste.component';
import { UserPosteDetailComponent } from './user-poste-detail.component';
import { UserPosteUpdateComponent } from './user-poste-update.component';

@Injectable({ providedIn: 'root' })
export class UserPosteResolve implements Resolve<IUserPoste> {
  constructor(private service: UserPosteService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUserPoste> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((userPoste: HttpResponse<UserPoste>) => {
          if (userPoste.body) {
            return of(userPoste.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new UserPoste());
  }
}

export const userPosteRoute: Routes = [
  {
    path: '',
    component: UserPosteComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'bankLoanManagerApp.userPoste.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: UserPosteDetailComponent,
    resolve: {
      userPoste: UserPosteResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bankLoanManagerApp.userPoste.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: UserPosteUpdateComponent,
    resolve: {
      userPoste: UserPosteResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bankLoanManagerApp.userPoste.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: UserPosteUpdateComponent,
    resolve: {
      userPoste: UserPosteResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bankLoanManagerApp.userPoste.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
