import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BankLoanManagerSharedModule } from 'app/shared/shared.module';
import { DowngradingComponent } from './downgrading.component';
import { DowngradingDetailComponent } from './downgrading-detail.component';
import { DowngradingUpdateComponent } from './downgrading-update.component';
import { DowngradingDeleteDialogComponent } from './downgrading-delete-dialog.component';
import { downgradingRoute } from './downgrading.route';

@NgModule({
  imports: [BankLoanManagerSharedModule, RouterModule.forChild(downgradingRoute)],
  declarations: [DowngradingComponent, DowngradingDetailComponent, DowngradingUpdateComponent, DowngradingDeleteDialogComponent],
  entryComponents: [DowngradingDeleteDialogComponent],
})
export class BankLoanManagerDowngradingModule {}
