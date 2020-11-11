import { NgModule } from '@angular/core';

import { BankLoanManagerSharedModule } from 'app/shared/shared.module';
import { HomeComponent } from './home.component';
import { RouterModule } from '@angular/router';
import { HOME_ROUTE } from 'app/entities/home/home.route';

@NgModule({
  imports: [BankLoanManagerSharedModule, RouterModule.forChild(HOME_ROUTE)],
  declarations: [HomeComponent],
})
export class BankLoanManagerHomeModule {}
