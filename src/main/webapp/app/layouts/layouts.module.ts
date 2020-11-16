import { NgModule } from '@angular/core';
import { LayoutComponent } from './layout.component';
import { FooterComponent } from './footer/footer.component';
import { BankLoanManagerSharedModule } from 'app/shared/shared.module';
import { BankLoanManagerCoreModule } from 'app/core/core.module';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';
import { ActiveMenuDirective } from 'app/layouts/navbar/active-menu.directive';

// STOKE
import { PerfectScrollbarModule } from 'ngx-perfect-scrollbar';
import { NgbDropdownModule } from '@ng-bootstrap/ng-bootstrap';
import { ClickOutsideModule } from 'ng-click-outside';
import { SidebarComponent } from 'app/layouts/sidebar/sidebar.component';
import { TopbarComponent } from 'app/layouts/topbar/topbar.component';
import { RightsidebarComponent } from 'app/layouts/rightsidebar/rightsidebar.component';
import { VerticalComponent } from 'app/layouts/vertical/vertical.component';
import { NavbarComponent } from 'app/layouts/navbar/navbar.component';

@NgModule({
  imports: [
    BrowserModule,
    RouterModule,
    BankLoanManagerSharedModule,
    BankLoanManagerCoreModule,
    PerfectScrollbarModule,
    NgbDropdownModule,
    ClickOutsideModule,
  ],
  declarations: [
    LayoutComponent,
    SidebarComponent,
    TopbarComponent,
    RightsidebarComponent,
    VerticalComponent,
    ActiveMenuDirective,
    FooterComponent,
    NavbarComponent,
  ],
})
export class BankLoanManagerLayoutsModule {}
