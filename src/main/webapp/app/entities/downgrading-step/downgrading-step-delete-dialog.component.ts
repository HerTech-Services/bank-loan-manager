import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDowngradingStep } from 'app/shared/model/downgrading-step.model';
import { DowngradingStepService } from './downgrading-step.service';

@Component({
  templateUrl: './downgrading-step-delete-dialog.component.html',
})
export class DowngradingStepDeleteDialogComponent {
  downgradingStep?: IDowngradingStep;

  constructor(
    protected downgradingStepService: DowngradingStepService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.downgradingStepService.delete(id).subscribe(() => {
      this.eventManager.broadcast('downgradingStepListModification');
      this.activeModal.close();
    });
  }
}
