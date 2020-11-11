import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IReject } from 'app/shared/model/reject.model';
import { RejectService } from './reject.service';

@Component({
  templateUrl: './reject-delete-dialog.component.html',
})
export class RejectDeleteDialogComponent {
  reject?: IReject;

  constructor(protected rejectService: RejectService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.rejectService.delete(id).subscribe(() => {
      this.eventManager.broadcast('rejectListModification');
      this.activeModal.close();
    });
  }
}
