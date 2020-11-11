import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRejectType } from 'app/shared/model/reject-type.model';
import { RejectTypeService } from './reject-type.service';

@Component({
  templateUrl: './reject-type-delete-dialog.component.html',
})
export class RejectTypeDeleteDialogComponent {
  rejectType?: IRejectType;

  constructor(
    protected rejectTypeService: RejectTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.rejectTypeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('rejectTypeListModification');
      this.activeModal.close();
    });
  }
}
