import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IParameters } from 'app/shared/model/parameters.model';
import { ParametersService } from './parameters.service';

@Component({
  templateUrl: './parameters-delete-dialog.component.html',
})
export class ParametersDeleteDialogComponent {
  parameters?: IParameters;

  constructor(
    protected parametersService: ParametersService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.parametersService.delete(id).subscribe(() => {
      this.eventManager.broadcast('parametersListModification');
      this.activeModal.close();
    });
  }
}
