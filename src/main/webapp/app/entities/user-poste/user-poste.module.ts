import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BankLoanManagerSharedModule } from 'app/shared/shared.module';
import { UserPosteComponent } from './user-poste.component';
import { UserPosteDetailComponent } from './user-poste-detail.component';
import { UserPosteUpdateComponent } from './user-poste-update.component';
import { UserPosteDeleteDialogComponent } from './user-poste-delete-dialog.component';
import { userPosteRoute } from './user-poste.route';

@NgModule({
  imports: [BankLoanManagerSharedModule, RouterModule.forChild(userPosteRoute)],
  declarations: [UserPosteComponent, UserPosteDetailComponent, UserPosteUpdateComponent, UserPosteDeleteDialogComponent],
  entryComponents: [UserPosteDeleteDialogComponent],
})
export class BankLoanManagerUserPosteModule {}
