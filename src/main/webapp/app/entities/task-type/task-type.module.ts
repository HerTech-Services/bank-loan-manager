import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BankLoanManagerSharedModule } from 'app/shared/shared.module';
import { TaskTypeComponent } from './task-type.component';
import { TaskTypeDetailComponent } from './task-type-detail.component';
import { TaskTypeUpdateComponent } from './task-type-update.component';
import { TaskTypeDeleteDialogComponent } from './task-type-delete-dialog.component';
import { taskTypeRoute } from './task-type.route';

@NgModule({
  imports: [BankLoanManagerSharedModule, RouterModule.forChild(taskTypeRoute)],
  declarations: [TaskTypeComponent, TaskTypeDetailComponent, TaskTypeUpdateComponent, TaskTypeDeleteDialogComponent],
  entryComponents: [TaskTypeDeleteDialogComponent],
})
export class BankLoanManagerTaskTypeModule {}
