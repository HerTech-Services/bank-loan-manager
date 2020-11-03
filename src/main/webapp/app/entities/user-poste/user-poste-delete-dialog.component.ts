import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUserPoste } from 'app/shared/model/user-poste.model';
import { UserPosteService } from './user-poste.service';

@Component({
  templateUrl: './user-poste-delete-dialog.component.html',
})
export class UserPosteDeleteDialogComponent {
  userPoste?: IUserPoste;

  constructor(protected userPosteService: UserPosteService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.userPosteService.delete(id).subscribe(() => {
      this.eventManager.broadcast('userPosteListModification');
      this.activeModal.close();
    });
  }
}
