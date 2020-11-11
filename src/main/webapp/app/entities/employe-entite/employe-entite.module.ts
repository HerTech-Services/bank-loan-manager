import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BankLoanManagerSharedModule } from 'app/shared/shared.module';
import { EmployeEntiteComponent } from './employe-entite.component';
import { EmployeEntiteDetailComponent } from './employe-entite-detail.component';
import { EmployeEntiteUpdateComponent } from './employe-entite-update.component';
import { EmployeEntiteDeleteDialogComponent } from './employe-entite-delete-dialog.component';
import { employeEntiteRoute } from './employe-entite.route';

@NgModule({
  imports: [BankLoanManagerSharedModule, RouterModule.forChild(employeEntiteRoute)],
  declarations: [EmployeEntiteComponent, EmployeEntiteDetailComponent, EmployeEntiteUpdateComponent, EmployeEntiteDeleteDialogComponent],
  entryComponents: [EmployeEntiteDeleteDialogComponent],
})
export class BankLoanManagerEmployeEntiteModule {}
