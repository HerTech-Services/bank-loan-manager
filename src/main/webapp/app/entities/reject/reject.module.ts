import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BankLoanManagerSharedModule } from 'app/shared/shared.module';
import { RejectComponent } from './reject.component';
import { RejectDetailComponent } from './reject-detail.component';
import { RejectUpdateComponent } from './reject-update.component';
import { RejectDeleteDialogComponent } from './reject-delete-dialog.component';
import { rejectRoute } from './reject.route';

@NgModule({
  imports: [BankLoanManagerSharedModule, RouterModule.forChild(rejectRoute)],
  declarations: [RejectComponent, RejectDetailComponent, RejectUpdateComponent, RejectDeleteDialogComponent],
  entryComponents: [RejectDeleteDialogComponent],
})
export class BankLoanManagerRejectModule {}
