import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFilelist } from 'app/shared/model/filelist.model';
import { FilelistService } from './filelist.service';

@Component({
  templateUrl: './filelist-delete-dialog.component.html',
})
export class FilelistDeleteDialogComponent {
  filelist?: IFilelist;

  constructor(protected filelistService: FilelistService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.filelistService.delete(id).subscribe(() => {
      this.eventManager.broadcast('filelistListModification');
      this.activeModal.close();
    });
  }
}
