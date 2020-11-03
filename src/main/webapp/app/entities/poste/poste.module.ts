import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BankLoanManagerSharedModule } from 'app/shared/shared.module';
import { PosteComponent } from './poste.component';
import { PosteDetailComponent } from './poste-detail.component';
import { PosteUpdateComponent } from './poste-update.component';
import { PosteDeleteDialogComponent } from './poste-delete-dialog.component';
import { posteRoute } from './poste.route';

@NgModule({
  imports: [BankLoanManagerSharedModule, RouterModule.forChild(posteRoute)],
  declarations: [PosteComponent, PosteDetailComponent, PosteUpdateComponent, PosteDeleteDialogComponent],
  entryComponents: [PosteDeleteDialogComponent],
})
export class BankLoanManagerPosteModule {}
