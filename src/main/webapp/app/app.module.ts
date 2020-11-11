import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { BankLoanManagerSharedModule } from 'app/shared/shared.module';
import { BankLoanManagerCoreModule } from 'app/core/core.module';
import { BankLoanManagerAppRoutingModule } from './app-routing.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { ErrorComponent } from './layouts/error/error.component';
import { AppComponent } from './app.component';
import { BankLoanManagerLayoutsModule } from 'app/layouts/layouts.module';

@NgModule({
  imports: [
    BrowserModule,
    BankLoanManagerSharedModule,
    BankLoanManagerCoreModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    BankLoanManagerAppRoutingModule,
    BankLoanManagerLayoutsModule,
  ],
  declarations: [ErrorComponent, AppComponent],
  bootstrap: [AppComponent],
})
export class BankLoanManagerAppModule {}
