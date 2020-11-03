import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFilelistEgagementType } from 'app/shared/model/filelist-egagement-type.model';
import { FilelistEgagementTypeService } from './filelist-egagement-type.service';

@Component({
  templateUrl: './filelist-egagement-type-delete-dialog.component.html',
})
export class FilelistEgagementTypeDeleteDialogComponent {
  filelistEgagementType?: IFilelistEgagementType;

  constructor(
    protected filelistEgagementTypeService: FilelistEgagementTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.filelistEgagementTypeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('filelistEgagementTypeListModification');
      this.activeModal.close();
    });
  }
}
