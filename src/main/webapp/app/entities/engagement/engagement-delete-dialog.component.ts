import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEngagement } from 'app/shared/model/engagement.model';
import { EngagementService } from './engagement.service';

@Component({
  templateUrl: './engagement-delete-dialog.component.html',
})
export class EngagementDeleteDialogComponent {
  engagement?: IEngagement;

  constructor(
    protected engagementService: EngagementService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.engagementService.delete(id).subscribe(() => {
      this.eventManager.broadcast('engagementListModification');
      this.activeModal.close();
    });
  }
}
