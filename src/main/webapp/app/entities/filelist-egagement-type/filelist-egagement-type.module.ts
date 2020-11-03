import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BankLoanManagerSharedModule } from 'app/shared/shared.module';
import { FilelistEgagementTypeComponent } from './filelist-egagement-type.component';
import { FilelistEgagementTypeDetailComponent } from './filelist-egagement-type-detail.component';
import { FilelistEgagementTypeUpdateComponent } from './filelist-egagement-type-update.component';
import { FilelistEgagementTypeDeleteDialogComponent } from './filelist-egagement-type-delete-dialog.component';
import { filelistEgagementTypeRoute } from './filelist-egagement-type.route';

@NgModule({
  imports: [BankLoanManagerSharedModule, RouterModule.forChild(filelistEgagementTypeRoute)],
  declarations: [
    FilelistEgagementTypeComponent,
    FilelistEgagementTypeDetailComponent,
    FilelistEgagementTypeUpdateComponent,
    FilelistEgagementTypeDeleteDialogComponent,
  ],
  entryComponents: [FilelistEgagementTypeDeleteDialogComponent],
})
export class BankLoanManagerFilelistEgagementTypeModule {}
