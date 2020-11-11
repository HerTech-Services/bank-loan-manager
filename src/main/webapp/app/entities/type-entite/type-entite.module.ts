import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BankLoanManagerSharedModule } from 'app/shared/shared.module';
import { TypeEntiteComponent } from './type-entite.component';
import { TypeEntiteDetailComponent } from './type-entite-detail.component';
import { TypeEntiteUpdateComponent } from './type-entite-update.component';
import { TypeEntiteDeleteDialogComponent } from './type-entite-delete-dialog.component';
import { typeEntiteRoute } from './type-entite.route';

@NgModule({
  imports: [BankLoanManagerSharedModule, RouterModule.forChild(typeEntiteRoute)],
  declarations: [TypeEntiteComponent, TypeEntiteDetailComponent, TypeEntiteUpdateComponent, TypeEntiteDeleteDialogComponent],
  entryComponents: [TypeEntiteDeleteDialogComponent],
})
export class BankLoanManagerTypeEntiteModule {}
