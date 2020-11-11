import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BankLoanManagerSharedModule } from 'app/shared/shared.module';
import { ParametersComponent } from './parameters.component';
import { ParametersDetailComponent } from './parameters-detail.component';
import { ParametersUpdateComponent } from './parameters-update.component';
import { ParametersDeleteDialogComponent } from './parameters-delete-dialog.component';
import { parametersRoute } from './parameters.route';

@NgModule({
  imports: [BankLoanManagerSharedModule, RouterModule.forChild(parametersRoute)],
  declarations: [ParametersComponent, ParametersDetailComponent, ParametersUpdateComponent, ParametersDeleteDialogComponent],
  entryComponents: [ParametersDeleteDialogComponent],
})
export class BankLoanManagerParametersModule {}
