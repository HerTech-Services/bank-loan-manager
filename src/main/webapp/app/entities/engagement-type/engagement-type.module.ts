import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BankLoanManagerSharedModule } from 'app/shared/shared.module';
import { EngagementTypeComponent } from './engagement-type.component';
import { EngagementTypeDetailComponent } from './engagement-type-detail.component';
import { EngagementTypeUpdateComponent } from './engagement-type-update.component';
import { EngagementTypeDeleteDialogComponent } from './engagement-type-delete-dialog.component';
import { engagementTypeRoute } from './engagement-type.route';

@NgModule({
  imports: [BankLoanManagerSharedModule, RouterModule.forChild(engagementTypeRoute)],
  declarations: [
    EngagementTypeComponent,
    EngagementTypeDetailComponent,
    EngagementTypeUpdateComponent,
    EngagementTypeDeleteDialogComponent,
  ],
  entryComponents: [EngagementTypeDeleteDialogComponent],
})
export class BankLoanManagerEngagementTypeModule {}
