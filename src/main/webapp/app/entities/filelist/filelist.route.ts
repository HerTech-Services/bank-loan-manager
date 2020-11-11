import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IFilelist, Filelist } from 'app/shared/model/filelist.model';
import { FilelistService } from './filelist.service';
import { FilelistComponent } from './filelist.component';
import { FilelistDetailComponent } from './filelist-detail.component';
import { FilelistUpdateComponent } from './filelist-update.component';

@Injectable({ providedIn: 'root' })
export class FilelistResolve implements Resolve<IFilelist> {
  constructor(private service: FilelistService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFilelist> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((filelist: HttpResponse<Filelist>) => {
          if (filelist.body) {
            return of(filelist.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Filelist());
  }
}

export const filelistRoute: Routes = [
  {
    path: '',
    component: FilelistComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'bankLoanManagerApp.filelist.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FilelistDetailComponent,
    resolve: {
      filelist: FilelistResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bankLoanManagerApp.filelist.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FilelistUpdateComponent,
    resolve: {
      filelist: FilelistResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bankLoanManagerApp.filelist.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FilelistUpdateComponent,
    resolve: {
      filelist: FilelistResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bankLoanManagerApp.filelist.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
