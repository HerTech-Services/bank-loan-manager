import { NgModule } from '@angular/core';
import { BankLoanManagerSharedLibsModule } from './shared-libs.module';
import { FindLanguageFromKeyPipe } from './language/find-language-from-key.pipe';
import { AlertComponent } from './alert/alert.component';
import { AlertErrorComponent } from './alert/alert-error.component';
import { LoginModalComponent } from './login/login.component';
import { HasAnyAuthorityDirective } from './auth/has-any-authority.directive';
import { UIModule } from 'app/shared/ui/ui.module';
import { TreeviewModule } from 'ngx-treeview';

@NgModule({
  imports: [BankLoanManagerSharedLibsModule, UIModule, TreeviewModule.forRoot()],
  declarations: [FindLanguageFromKeyPipe, AlertComponent, AlertErrorComponent, LoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [LoginModalComponent],
  exports: [
    BankLoanManagerSharedLibsModule,
    UIModule,
    TreeviewModule,
    FindLanguageFromKeyPipe,
    AlertComponent,
    AlertErrorComponent,
    LoginModalComponent,
    HasAnyAuthorityDirective,
  ],
})
export class BankLoanManagerSharedModule {}
