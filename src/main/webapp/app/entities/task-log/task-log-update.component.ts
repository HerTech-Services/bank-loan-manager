import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { ITaskLog, TaskLog } from 'app/shared/model/task-log.model';
import { TaskLogService } from './task-log.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IAction } from 'app/shared/model/action.model';
import { ActionService } from 'app/entities/action/action.service';
import { ITask } from 'app/shared/model/task.model';
import { TaskService } from 'app/entities/task/task.service';
import { IEngagement } from 'app/shared/model/engagement.model';
import { EngagementService } from 'app/entities/engagement/engagement.service';

type SelectableEntity = IUser | IAction | ITask | IEngagement;

@Component({
  selector: 'jhi-task-log-update',
  templateUrl: './task-log-update.component.html',
})
export class TaskLogUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  actions: IAction[] = [];
  tasks: ITask[] = [];
  engagements: IEngagement[] = [];

  editForm = this.fb.group({
    id: [],
    comment: [],
    createdDate: [],
    taskProperties: [],
    engagementProperties: [],
    userId: [],
    actionId: [],
    taskId: [],
    engagementId: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected taskLogService: TaskLogService,
    protected userService: UserService,
    protected actionService: ActionService,
    protected taskService: TaskService,
    protected engagementService: EngagementService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ taskLog }) => {
      if (!taskLog.id) {
        const today = moment().startOf('day');
        taskLog.createdDate = today;
      }

      this.updateForm(taskLog);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.actionService.query().subscribe((res: HttpResponse<IAction[]>) => (this.actions = res.body || []));

      this.taskService.query().subscribe((res: HttpResponse<ITask[]>) => (this.tasks = res.body || []));

      this.engagementService.query().subscribe((res: HttpResponse<IEngagement[]>) => (this.engagements = res.body || []));
    });
  }

  updateForm(taskLog: ITaskLog): void {
    this.editForm.patchValue({
      id: taskLog.id,
      comment: taskLog.comment,
      createdDate: taskLog.createdDate ? taskLog.createdDate.format(DATE_TIME_FORMAT) : null,
      taskProperties: taskLog.taskProperties,
      engagementProperties: taskLog.engagementProperties,
      userId: taskLog.userId,
      actionId: taskLog.actionId,
      taskId: taskLog.taskId,
      engagementId: taskLog.engagementId,
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: any, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('bankLoanManagerApp.error', { ...err, key: 'error.file.' + err.key })
      );
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const taskLog = this.createFromForm();
    if (taskLog.id !== undefined) {
      this.subscribeToSaveResponse(this.taskLogService.update(taskLog));
    } else {
      this.subscribeToSaveResponse(this.taskLogService.create(taskLog));
    }
  }

  private createFromForm(): ITaskLog {
    return {
      ...new TaskLog(),
      id: this.editForm.get(['id'])!.value,
      comment: this.editForm.get(['comment'])!.value,
      createdDate: this.editForm.get(['createdDate'])!.value
        ? moment(this.editForm.get(['createdDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      taskProperties: this.editForm.get(['taskProperties'])!.value,
      engagementProperties: this.editForm.get(['engagementProperties'])!.value,
      userId: this.editForm.get(['userId'])!.value,
      actionId: this.editForm.get(['actionId'])!.value,
      taskId: this.editForm.get(['taskId'])!.value,
      engagementId: this.editForm.get(['engagementId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITaskLog>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
