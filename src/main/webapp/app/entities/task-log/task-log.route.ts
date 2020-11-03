import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITaskLog, TaskLog } from 'app/shared/model/task-log.model';
import { TaskLogService } from './task-log.service';
import { TaskLogComponent } from './task-log.component';
import { TaskLogDetailComponent } from './task-log-detail.component';
import { TaskLogUpdateComponent } from './task-log-update.component';

@Injectable({ providedIn: 'root' })
export class TaskLogResolve implements Resolve<ITaskLog> {
  constructor(private service: TaskLogService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITaskLog> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((taskLog: HttpResponse<TaskLog>) => {
          if (taskLog.body) {
            return of(taskLog.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TaskLog());
  }
}

export const taskLogRoute: Routes = [
  {
    path: '',
    component: TaskLogComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'bankLoanManagerApp.taskLog.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TaskLogDetailComponent,
    resolve: {
      taskLog: TaskLogResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bankLoanManagerApp.taskLog.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TaskLogUpdateComponent,
    resolve: {
      taskLog: TaskLogResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bankLoanManagerApp.taskLog.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TaskLogUpdateComponent,
    resolve: {
      taskLog: TaskLogResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bankLoanManagerApp.taskLog.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
