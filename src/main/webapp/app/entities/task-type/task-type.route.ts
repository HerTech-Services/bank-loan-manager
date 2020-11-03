import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITaskType, TaskType } from 'app/shared/model/task-type.model';
import { TaskTypeService } from './task-type.service';
import { TaskTypeComponent } from './task-type.component';
import { TaskTypeDetailComponent } from './task-type-detail.component';
import { TaskTypeUpdateComponent } from './task-type-update.component';

@Injectable({ providedIn: 'root' })
export class TaskTypeResolve implements Resolve<ITaskType> {
  constructor(private service: TaskTypeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITaskType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((taskType: HttpResponse<TaskType>) => {
          if (taskType.body) {
            return of(taskType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TaskType());
  }
}

export const taskTypeRoute: Routes = [
  {
    path: '',
    component: TaskTypeComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'bankLoanManagerApp.taskType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TaskTypeDetailComponent,
    resolve: {
      taskType: TaskTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bankLoanManagerApp.taskType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TaskTypeUpdateComponent,
    resolve: {
      taskType: TaskTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bankLoanManagerApp.taskType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TaskTypeUpdateComponent,
    resolve: {
      taskType: TaskTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bankLoanManagerApp.taskType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
