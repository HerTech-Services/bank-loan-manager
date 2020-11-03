import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEngagementType } from 'app/shared/model/engagement-type.model';
import { EngagementTypeService } from './engagement-type.service';

@Component({
  templateUrl: './engagement-type-delete-dialog.component.html',
})
export class EngagementTypeDeleteDialogComponent {
  engagementType?: IEngagementType;

  constructor(
    protected engagementTypeService: EngagementTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.engagementTypeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('engagementTypeListModification');
      this.activeModal.close();
    });
  }
}
