import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDowngradingStep, DowngradingStep } from 'app/shared/model/downgrading-step.model';
import { DowngradingStepService } from './downgrading-step.service';
import { DowngradingStepComponent } from './downgrading-step.component';
import { DowngradingStepDetailComponent } from './downgrading-step-detail.component';
import { DowngradingStepUpdateComponent } from './downgrading-step-update.component';

@Injectable({ providedIn: 'root' })
export class DowngradingStepResolve implements Resolve<IDowngradingStep> {
  constructor(private service: DowngradingStepService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDowngradingStep> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((downgradingStep: HttpResponse<DowngradingStep>) => {
          if (downgradingStep.body) {
            return of(downgradingStep.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DowngradingStep());
  }
}

export const downgradingStepRoute: Routes = [
  {
    path: '',
    component: DowngradingStepComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'bankLoanManagerApp.downgradingStep.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DowngradingStepDetailComponent,
    resolve: {
      downgradingStep: DowngradingStepResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bankLoanManagerApp.downgradingStep.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DowngradingStepUpdateComponent,
    resolve: {
      downgradingStep: DowngradingStepResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bankLoanManagerApp.downgradingStep.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DowngradingStepUpdateComponent,
    resolve: {
      downgradingStep: DowngradingStepResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bankLoanManagerApp.downgradingStep.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
