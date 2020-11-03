import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ITaskLog } from 'app/shared/model/task-log.model';

@Component({
  selector: 'jhi-task-log-detail',
  templateUrl: './task-log-detail.component.html',
})
export class TaskLogDetailComponent implements OnInit {
  taskLog: ITaskLog | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ taskLog }) => (this.taskLog = taskLog));
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    window.history.back();
  }
}
