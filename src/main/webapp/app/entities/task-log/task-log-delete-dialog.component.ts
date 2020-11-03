import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITaskLog } from 'app/shared/model/task-log.model';
import { TaskLogService } from './task-log.service';

@Component({
  templateUrl: './task-log-delete-dialog.component.html',
})
export class TaskLogDeleteDialogComponent {
  taskLog?: ITaskLog;

  constructor(protected taskLogService: TaskLogService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.taskLogService.delete(id).subscribe(() => {
      this.eventManager.broadcast('taskLogListModification');
      this.activeModal.close();
    });
  }
}
