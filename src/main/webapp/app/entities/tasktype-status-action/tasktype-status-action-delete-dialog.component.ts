import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITasktypeStatusAction } from 'app/shared/model/tasktype-status-action.model';
import { TasktypeStatusActionService } from './tasktype-status-action.service';

@Component({
  templateUrl: './tasktype-status-action-delete-dialog.component.html',
})
export class TasktypeStatusActionDeleteDialogComponent {
  tasktypeStatusAction?: ITasktypeStatusAction;

  constructor(
    protected tasktypeStatusActionService: TasktypeStatusActionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tasktypeStatusActionService.delete(id).subscribe(() => {
      this.eventManager.broadcast('tasktypeStatusActionListModification');
      this.activeModal.close();
    });
  }
}
