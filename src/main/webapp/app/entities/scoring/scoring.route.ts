import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IScoring, Scoring } from 'app/shared/model/scoring.model';
import { ScoringService } from './scoring.service';
import { ScoringComponent } from './scoring.component';
import { ScoringDetailComponent } from './scoring-detail.component';
import { ScoringUpdateComponent } from './scoring-update.component';

@Injectable({ providedIn: 'root' })
export class ScoringResolve implements Resolve<IScoring> {
  constructor(private service: ScoringService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IScoring> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((scoring: HttpResponse<Scoring>) => {
          if (scoring.body) {
            return of(scoring.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Scoring());
  }
}

export const scoringRoute: Routes = [
  {
    path: '',
    component: ScoringComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'bankLoanManagerApp.scoring.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ScoringDetailComponent,
    resolve: {
      scoring: ScoringResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bankLoanManagerApp.scoring.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ScoringUpdateComponent,
    resolve: {
      scoring: ScoringResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bankLoanManagerApp.scoring.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ScoringUpdateComponent,
    resolve: {
      scoring: ScoringResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bankLoanManagerApp.scoring.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
