import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BankLoanManagerSharedModule } from 'app/shared/shared.module';
import { EntiteComponent } from './entite.component';
import { EntiteDetailComponent } from './entite-detail.component';
import { EntiteUpdateComponent } from './entite-update.component';
import { EntiteDeleteDialogComponent } from './entite-delete-dialog.component';
import { entiteRoute } from './entite.route';

@NgModule({
  imports: [BankLoanManagerSharedModule, RouterModule.forChild(entiteRoute)],
  declarations: [EntiteComponent, EntiteDetailComponent, EntiteUpdateComponent, EntiteDeleteDialogComponent],
  entryComponents: [EntiteDeleteDialogComponent],
})
export class BankLoanManagerEntiteModule {}
