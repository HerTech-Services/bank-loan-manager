import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BankLoanManagerSharedModule } from 'app/shared/shared.module';
import { TaskLogComponent } from './task-log.component';
import { TaskLogDetailComponent } from './task-log-detail.component';
import { TaskLogUpdateComponent } from './task-log-update.component';
import { TaskLogDeleteDialogComponent } from './task-log-delete-dialog.component';
import { taskLogRoute } from './task-log.route';

@NgModule({
  imports: [BankLoanManagerSharedModule, RouterModule.forChild(taskLogRoute)],
  declarations: [TaskLogComponent, TaskLogDetailComponent, TaskLogUpdateComponent, TaskLogDeleteDialogComponent],
  entryComponents: [TaskLogDeleteDialogComponent],
})
export class BankLoanManagerTaskLogModule {}
