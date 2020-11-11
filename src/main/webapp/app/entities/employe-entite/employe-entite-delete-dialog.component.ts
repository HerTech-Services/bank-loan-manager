import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEmployeEntite } from 'app/shared/model/employe-entite.model';
import { EmployeEntiteService } from './employe-entite.service';

@Component({
  templateUrl: './employe-entite-delete-dialog.component.html',
})
export class EmployeEntiteDeleteDialogComponent {
  employeEntite?: IEmployeEntite;

  constructor(
    protected employeEntiteService: EmployeEntiteService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.employeEntiteService.delete(id).subscribe(() => {
      this.eventManager.broadcast('employeEntiteListModification');
      this.activeModal.close();
    });
  }
}
