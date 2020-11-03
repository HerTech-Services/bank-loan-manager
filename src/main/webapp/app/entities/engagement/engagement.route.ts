import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEngagement, Engagement } from 'app/shared/model/engagement.model';
import { EngagementService } from './engagement.service';
import { EngagementComponent } from './engagement.component';
import { EngagementDetailComponent } from './engagement-detail.component';
import { EngagementUpdateComponent } from './engagement-update.component';

@Injectable({ providedIn: 'root' })
export class EngagementResolve implements Resolve<IEngagement> {
  constructor(private service: EngagementService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEngagement> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((engagement: HttpResponse<Engagement>) => {
          if (engagement.body) {
            return of(engagement.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Engagement());
  }
}

export const engagementRoute: Routes = [
  {
    path: '',
    component: EngagementComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'bankLoanManagerApp.engagement.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EngagementDetailComponent,
    resolve: {
      engagement: EngagementResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bankLoanManagerApp.engagement.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EngagementUpdateComponent,
    resolve: {
      engagement: EngagementResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bankLoanManagerApp.engagement.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EngagementUpdateComponent,
    resolve: {
      engagement: EngagementResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bankLoanManagerApp.engagement.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
