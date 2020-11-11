import { NgModule } from '@angular/core';
import { LayoutComponent } from './layout.component';
import { FooterComponent } from './footer/footer.component';
import { NavbarComponent } from './navbar/navbar.component';
import { BankLoanManagerSharedModule } from 'app/shared/shared.module';
import { BankLoanManagerCoreModule } from 'app/core/core.module';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';
import { ActiveMenuDirective } from 'app/layouts/navbar/active-menu.directive';

@NgModule({
  imports: [BrowserModule, RouterModule, BankLoanManagerSharedModule, BankLoanManagerCoreModule],
  declarations: [LayoutComponent, ActiveMenuDirective, FooterComponent, NavbarComponent],
})
export class BankLoanManagerLayoutsModule {}
