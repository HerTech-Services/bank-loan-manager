import { Authority } from 'app/shared/constants/authority.constants';
import { Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { DashboardComponent } from 'app/entities/dashboard/dashboard.component';

export const routeDashboard: Routes = [
  {
    path: '',
    component: DashboardComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
