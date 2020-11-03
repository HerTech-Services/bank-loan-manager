import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITasktypeStatusAction, TasktypeStatusAction } from 'app/shared/model/tasktype-status-action.model';
import { TasktypeStatusActionService } from './tasktype-status-action.service';
import { TasktypeStatusActionComponent } from './tasktype-status-action.component';
import { TasktypeStatusActionDetailComponent } from './tasktype-status-action-detail.component';
import { TasktypeStatusActionUpdateComponent } from './tasktype-status-action-update.component';

@Injectable({ providedIn: 'root' })
export class TasktypeStatusActionResolve implements Resolve<ITasktypeStatusAction> {
  constructor(private service: TasktypeStatusActionService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITasktypeStatusAction> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((tasktypeStatusAction: HttpResponse<TasktypeStatusAction>) => {
          if (tasktypeStatusAction.body) {
            return of(tasktypeStatusAction.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TasktypeStatusAction());
  }
}

export const tasktypeStatusActionRoute: Routes = [
  {
    path: '',
    component: TasktypeStatusActionComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'bankLoanManagerApp.tasktypeStatusAction.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TasktypeStatusActionDetailComponent,
    resolve: {
      tasktypeStatusAction: TasktypeStatusActionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bankLoanManagerApp.tasktypeStatusAction.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TasktypeStatusActionUpdateComponent,
    resolve: {
      tasktypeStatusAction: TasktypeStatusActionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bankLoanManagerApp.tasktypeStatusAction.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TasktypeStatusActionUpdateComponent,
    resolve: {
      tasktypeStatusAction: TasktypeStatusActionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bankLoanManagerApp.tasktypeStatusAction.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
