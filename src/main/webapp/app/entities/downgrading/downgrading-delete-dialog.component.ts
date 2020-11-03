import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDowngrading } from 'app/shared/model/downgrading.model';
import { DowngradingService } from './downgrading.service';

@Component({
  templateUrl: './downgrading-delete-dialog.component.html',
})
export class DowngradingDeleteDialogComponent {
  downgrading?: IDowngrading;

  constructor(
    protected downgradingService: DowngradingService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.downgradingService.delete(id).subscribe(() => {
      this.eventManager.broadcast('downgradingListModification');
      this.activeModal.close();
    });
  }
}
