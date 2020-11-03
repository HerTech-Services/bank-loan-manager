import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IFilelistEgagementType, FilelistEgagementType } from 'app/shared/model/filelist-egagement-type.model';
import { FilelistEgagementTypeService } from './filelist-egagement-type.service';
import { FilelistEgagementTypeComponent } from './filelist-egagement-type.component';
import { FilelistEgagementTypeDetailComponent } from './filelist-egagement-type-detail.component';
import { FilelistEgagementTypeUpdateComponent } from './filelist-egagement-type-update.component';

@Injectable({ providedIn: 'root' })
export class FilelistEgagementTypeResolve implements Resolve<IFilelistEgagementType> {
  constructor(private service: FilelistEgagementTypeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFilelistEgagementType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((filelistEgagementType: HttpResponse<FilelistEgagementType>) => {
          if (filelistEgagementType.body) {
            return of(filelistEgagementType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new FilelistEgagementType());
  }
}

export const filelistEgagementTypeRoute: Routes = [
  {
    path: '',
    component: FilelistEgagementTypeComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'bankLoanManagerApp.filelistEgagementType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FilelistEgagementTypeDetailComponent,
    resolve: {
      filelistEgagementType: FilelistEgagementTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bankLoanManagerApp.filelistEgagementType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FilelistEgagementTypeUpdateComponent,
    resolve: {
      filelistEgagementType: FilelistEgagementTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bankLoanManagerApp.filelistEgagementType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FilelistEgagementTypeUpdateComponent,
    resolve: {
      filelistEgagementType: FilelistEgagementTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bankLoanManagerApp.filelistEgagementType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
