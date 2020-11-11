import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BankLoanManagerSharedModule } from 'app/shared/shared.module';
import { EmployeComponent } from './employe.component';
import { EmployeDetailComponent } from './employe-detail.component';
import { EmployeUpdateComponent } from './employe-update.component';
import { EmployeDeleteDialogComponent } from './employe-delete-dialog.component';
import { employeRoute } from './employe.route';

@NgModule({
  imports: [BankLoanManagerSharedModule, RouterModule.forChild(employeRoute)],
  declarations: [EmployeComponent, EmployeDetailComponent, EmployeUpdateComponent, EmployeDeleteDialogComponent],
  entryComponents: [EmployeDeleteDialogComponent],
})
export class BankLoanManagerEmployeModule {}
