import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BankLoanManagerSharedModule } from 'app/shared/shared.module';
import { EngagementComponent } from './engagement.component';
import { EngagementDetailComponent } from './engagement-detail.component';
import { EngagementUpdateComponent } from './engagement-update.component';
import { EngagementDeleteDialogComponent } from './engagement-delete-dialog.component';
import { engagementRoute } from './engagement.route';

@NgModule({
  imports: [BankLoanManagerSharedModule, RouterModule.forChild(engagementRoute)],
  declarations: [EngagementComponent, EngagementDetailComponent, EngagementUpdateComponent, EngagementDeleteDialogComponent],
  entryComponents: [EngagementDeleteDialogComponent],
})
export class BankLoanManagerEngagementModule {}
