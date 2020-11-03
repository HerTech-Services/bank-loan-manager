import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BankLoanManagerSharedModule } from 'app/shared/shared.module';
import { FilelistComponent } from './filelist.component';
import { FilelistDetailComponent } from './filelist-detail.component';
import { FilelistUpdateComponent } from './filelist-update.component';
import { FilelistDeleteDialogComponent } from './filelist-delete-dialog.component';
import { filelistRoute } from './filelist.route';

@NgModule({
  imports: [BankLoanManagerSharedModule, RouterModule.forChild(filelistRoute)],
  declarations: [FilelistComponent, FilelistDetailComponent, FilelistUpdateComponent, FilelistDeleteDialogComponent],
  entryComponents: [FilelistDeleteDialogComponent],
})
export class BankLoanManagerFilelistModule {}
