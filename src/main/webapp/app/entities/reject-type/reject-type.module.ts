import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BankLoanManagerSharedModule } from 'app/shared/shared.module';
import { RejectTypeComponent } from './reject-type.component';
import { RejectTypeDetailComponent } from './reject-type-detail.component';
import { RejectTypeUpdateComponent } from './reject-type-update.component';
import { RejectTypeDeleteDialogComponent } from './reject-type-delete-dialog.component';
import { rejectTypeRoute } from './reject-type.route';

@NgModule({
  imports: [BankLoanManagerSharedModule, RouterModule.forChild(rejectTypeRoute)],
  declarations: [RejectTypeComponent, RejectTypeDetailComponent, RejectTypeUpdateComponent, RejectTypeDeleteDialogComponent],
  entryComponents: [RejectTypeDeleteDialogComponent],
})
export class BankLoanManagerRejectTypeModule {}
