import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BankLoanManagerSharedModule } from 'app/shared/shared.module';
import { DowngradingStepComponent } from './downgrading-step.component';
import { DowngradingStepDetailComponent } from './downgrading-step-detail.component';
import { DowngradingStepUpdateComponent } from './downgrading-step-update.component';
import { DowngradingStepDeleteDialogComponent } from './downgrading-step-delete-dialog.component';
import { downgradingStepRoute } from './downgrading-step.route';

@NgModule({
  imports: [BankLoanManagerSharedModule, RouterModule.forChild(downgradingStepRoute)],
  declarations: [
    DowngradingStepComponent,
    DowngradingStepDetailComponent,
    DowngradingStepUpdateComponent,
    DowngradingStepDeleteDialogComponent,
  ],
  entryComponents: [DowngradingStepDeleteDialogComponent],
})
export class BankLoanManagerDowngradingStepModule {}
