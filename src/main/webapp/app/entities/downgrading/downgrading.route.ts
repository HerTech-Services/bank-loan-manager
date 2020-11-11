import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDowngrading, Downgrading } from 'app/shared/model/downgrading.model';
import { DowngradingService } from './downgrading.service';
import { DowngradingComponent } from './downgrading.component';
import { DowngradingDetailComponent } from './downgrading-detail.component';
import { DowngradingUpdateComponent } from './downgrading-update.component';

@Injectable({ providedIn: 'root' })
export class DowngradingResolve implements Resolve<IDowngrading> {
  constructor(private service: DowngradingService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDowngrading> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((downgrading: HttpResponse<Downgrading>) => {
          if (downgrading.body) {
            return of(downgrading.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Downgrading());
  }
}

export const downgradingRoute: Routes = [
  {
    path: '',
    component: DowngradingComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'bankLoanManagerApp.downgrading.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DowngradingDetailComponent,
    resolve: {
      downgrading: DowngradingResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bankLoanManagerApp.downgrading.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DowngradingUpdateComponent,
    resolve: {
      downgrading: DowngradingResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bankLoanManagerApp.downgrading.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DowngradingUpdateComponent,
    resolve: {
      downgrading: DowngradingResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bankLoanManagerApp.downgrading.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
