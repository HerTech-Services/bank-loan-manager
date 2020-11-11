import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEntite } from 'app/shared/model/entite.model';
import { EntiteService } from './entite.service';

@Component({
  templateUrl: './entite-delete-dialog.component.html',
})
export class EntiteDeleteDialogComponent {
  entite?: IEntite;

  constructor(protected entiteService: EntiteService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.entiteService.delete(id).subscribe(() => {
      this.eventManager.broadcast('entiteListModification');
      this.activeModal.close();
    });
  }
}
