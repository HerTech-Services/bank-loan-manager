import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IScoring } from 'app/shared/model/scoring.model';
import { ScoringService } from './scoring.service';

@Component({
  templateUrl: './scoring-delete-dialog.component.html',
})
export class ScoringDeleteDialogComponent {
  scoring?: IScoring;

  constructor(protected scoringService: ScoringService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.scoringService.delete(id).subscribe(() => {
      this.eventManager.broadcast('scoringListModification');
      this.activeModal.close();
    });
  }
}
