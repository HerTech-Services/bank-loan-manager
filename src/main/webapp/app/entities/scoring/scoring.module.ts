import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BankLoanManagerSharedModule } from 'app/shared/shared.module';
import { ScoringComponent } from './scoring.component';
import { ScoringDetailComponent } from './scoring-detail.component';
import { ScoringUpdateComponent } from './scoring-update.component';
import { ScoringDeleteDialogComponent } from './scoring-delete-dialog.component';
import { scoringRoute } from './scoring.route';

@NgModule({
  imports: [BankLoanManagerSharedModule, RouterModule.forChild(scoringRoute)],
  declarations: [ScoringComponent, ScoringDetailComponent, ScoringUpdateComponent, ScoringDeleteDialogComponent],
  entryComponents: [ScoringDeleteDialogComponent],
})
export class BankLoanManagerScoringModule {}
