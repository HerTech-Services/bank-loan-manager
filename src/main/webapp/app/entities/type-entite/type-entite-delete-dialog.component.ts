import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITypeEntite } from 'app/shared/model/type-entite.model';
import { TypeEntiteService } from './type-entite.service';

@Component({
  templateUrl: './type-entite-delete-dialog.component.html',
})
export class TypeEntiteDeleteDialogComponent {
  typeEntite?: ITypeEntite;

  constructor(
    protected typeEntiteService: TypeEntiteService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.typeEntiteService.delete(id).subscribe(() => {
      this.eventManager.broadcast('typeEntiteListModification');
      this.activeModal.close();
    });
  }
}
