import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITaskType } from 'app/shared/model/task-type.model';
import { TaskTypeService } from './task-type.service';

@Component({
  templateUrl: './task-type-delete-dialog.component.html',
})
export class TaskTypeDeleteDialogComponent {
  taskType?: ITaskType;

  constructor(protected taskTypeService: TaskTypeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.taskTypeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('taskTypeListModification');
      this.activeModal.close();
    });
  }
}
