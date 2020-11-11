import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BankLoanManagerSharedModule } from 'app/shared/shared.module';
import { TasktypeStatusActionComponent } from './tasktype-status-action.component';
import { TasktypeStatusActionDetailComponent } from './tasktype-status-action-detail.component';
import { TasktypeStatusActionUpdateComponent } from './tasktype-status-action-update.component';
import { TasktypeStatusActionDeleteDialogComponent } from './tasktype-status-action-delete-dialog.component';
import { tasktypeStatusActionRoute } from './tasktype-status-action.route';

@NgModule({
  imports: [BankLoanManagerSharedModule, RouterModule.forChild(tasktypeStatusActionRoute)],
  declarations: [
    TasktypeStatusActionComponent,
    TasktypeStatusActionDetailComponent,
    TasktypeStatusActionUpdateComponent,
    TasktypeStatusActionDeleteDialogComponent,
  ],
  entryComponents: [TasktypeStatusActionDeleteDialogComponent],
})
export class BankLoanManagerTasktypeStatusActionModule {}
