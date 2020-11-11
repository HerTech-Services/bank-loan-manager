import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEngagementType, EngagementType } from 'app/shared/model/engagement-type.model';
import { EngagementTypeService } from './engagement-type.service';
import { EngagementTypeComponent } from './engagement-type.component';
import { EngagementTypeDetailComponent } from './engagement-type-detail.component';
import { EngagementTypeUpdateComponent } from './engagement-type-update.component';

@Injectable({ providedIn: 'root' })
export class EngagementTypeResolve implements Resolve<IEngagementType> {
  constructor(private service: EngagementTypeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEngagementType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((engagementType: HttpResponse<EngagementType>) => {
          if (engagementType.body) {
            return of(engagementType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EngagementType());
  }
}

export const engagementTypeRoute: Routes = [
  {
    path: '',
    component: EngagementTypeComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'bankLoanManagerApp.engagementType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EngagementTypeDetailComponent,
    resolve: {
      engagementType: EngagementTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bankLoanManagerApp.engagementType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EngagementTypeUpdateComponent,
    resolve: {
      engagementType: EngagementTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bankLoanManagerApp.engagementType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EngagementTypeUpdateComponent,
    resolve: {
      engagementType: EngagementTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bankLoanManagerApp.engagementType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
