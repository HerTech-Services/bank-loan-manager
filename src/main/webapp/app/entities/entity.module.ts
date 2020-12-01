import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { DashboardComponent } from 'app/entities/dashboard/dashboard.component';
import { navbarRoute } from 'app/layouts/navbar/navbar.route';
import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
const LAYOUT_ROUTES = [navbarRoute];
@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: '',
        redirectTo: 'dashboard',
      },
      {
        path: 'dashboard',
        component: DashboardComponent,
        data: {
          authorities: [Authority.USER],
          pageTitle: 'home.title',
        },
        canActivate: [UserRouteAccessService],
      },
      {
        path: 'scoring',
        loadChildren: () => import('./scoring/scoring.module').then(m => m.BankLoanManagerScoringModule),
      },
      {
        path: 'sector',
        loadChildren: () => import('./sector/sector.module').then(m => m.BankLoanManagerSectorModule),
      },
      {
        path: 'downgrading-step',
        loadChildren: () => import('./downgrading-step/downgrading-step.module').then(m => m.BankLoanManagerDowngradingStepModule),
      },
      {
        path: 'downgrading',
        loadChildren: () => import('./downgrading/downgrading.module').then(m => m.BankLoanManagerDowngradingModule),
      },
      {
        path: 'reject-type',
        loadChildren: () => import('./reject-type/reject-type.module').then(m => m.BankLoanManagerRejectTypeModule),
      },
      {
        path: 'reject',
        loadChildren: () => import('./reject/reject.module').then(m => m.BankLoanManagerRejectModule),
      },
      {
        path: 'client',
        loadChildren: () => import('./client/client.module').then(m => m.BankLoanManagerClientModule),
      },
      {
        path: 'compte',
        loadChildren: () => import('./compte/compte.module').then(m => m.BankLoanManagerCompteModule),
      },
      {
        path: 'engagement',
        loadChildren: () => import('./engagement/engagement.module').then(m => m.BankLoanManagerEngagementModule),
      },
      {
        path: 'engagement-type',
        loadChildren: () => import('./engagement-type/engagement-type.module').then(m => m.BankLoanManagerEngagementTypeModule),
      },
      {
        path: 'status',
        loadChildren: () => import('./status/status.module').then(m => m.BankLoanManagerStatusModule),
      },
      {
        path: 'task',
        loadChildren: () => import('./task/task.module').then(m => m.BankLoanManagerTaskModule),
      },
      {
        path: 'task-type',
        loadChildren: () => import('./task-type/task-type.module').then(m => m.BankLoanManagerTaskTypeModule),
      },
      {
        path: 'action',
        loadChildren: () => import('./action/action.module').then(m => m.BankLoanManagerActionModule),
      },
      {
        path: 'tasktype-status-action',
        loadChildren: () =>
          import('./tasktype-status-action/tasktype-status-action.module').then(m => m.BankLoanManagerTasktypeStatusActionModule),
      },
      {
        path: 'task-log',
        loadChildren: () => import('./task-log/task-log.module').then(m => m.BankLoanManagerTaskLogModule),
      },
      {
        path: 'employe-entite',
        loadChildren: () => import('./employe-entite/employe-entite.module').then(m => m.BankLoanManagerEmployeEntiteModule),
      },
      {
        path: 'notes',
        loadChildren: () => import('./notes/notes.module').then(m => m.BankLoanManagerNotesModule),
      },
      {
        path: 'parameters',
        loadChildren: () => import('./parameters/parameters.module').then(m => m.BankLoanManagerParametersModule),
      },
      {
        path: 'filelist',
        loadChildren: () => import('./filelist/filelist.module').then(m => m.BankLoanManagerFilelistModule),
      },
      {
        path: 'filelist-egagement-type',
        loadChildren: () =>
          import('./filelist-egagement-type/filelist-egagement-type.module').then(m => m.BankLoanManagerFilelistEgagementTypeModule),
      },
      {
        path: 'attachment',
        loadChildren: () => import('./attachment/attachment.module').then(m => m.BankLoanManagerAttachmentModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
      ...LAYOUT_ROUTES,
    ]),
  ],
})
export class BankLoanManagerEntityModule {}
