import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { ITask, Task } from 'app/shared/model/task.model';
import { TaskService } from './task.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { ITaskType } from 'app/shared/model/task-type.model';
import { TaskTypeService } from 'app/entities/task-type/task-type.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IEngagement } from 'app/shared/model/engagement.model';
import { EngagementService } from 'app/entities/engagement/engagement.service';

type SelectableEntity = ITaskType | IUser | IEngagement;

@Component({
  selector: 'jhi-task-update',
  templateUrl: './task-update.component.html',
})
export class TaskUpdateComponent implements OnInit {
  isSaving = false;
  tasktypes: ITaskType[] = [];
  users: IUser[] = [];
  engagements: IEngagement[] = [];

  editForm = this.fb.group({
    id: [],
    sequence: [null, [Validators.required, Validators.min(1)]],
    isSystem: [null, [Validators.required]],
    processDelay: [null, [Validators.required]],
    delay1: [],
    delay2: [],
    viewed: [],
    createdDate: [],
    processDate: [],
    processComment: [],
    typeId: [null, Validators.required],
    assigneeId: [null, Validators.required],
    engagementId: [null, Validators.required],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected taskService: TaskService,
    protected taskTypeService: TaskTypeService,
    protected userService: UserService,
    protected engagementService: EngagementService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ task }) => {
      if (!task.id) {
        const today = moment().startOf('day');
        task.createdDate = today;
        task.processDate = today;
      }

      this.updateForm(task);

      this.taskTypeService.query().subscribe((res: HttpResponse<ITaskType[]>) => (this.tasktypes = res.body || []));

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.engagementService.query().subscribe((res: HttpResponse<IEngagement[]>) => (this.engagements = res.body || []));
    });
  }

  updateForm(task: ITask): void {
    this.editForm.patchValue({
      id: task.id,
      sequence: task.sequence,
      isSystem: task.isSystem,
      processDelay: task.processDelay,
      delay1: task.delay1,
      delay2: task.delay2,
      viewed: task.viewed,
      createdDate: task.createdDate ? task.createdDate.format(DATE_TIME_FORMAT) : null,
      processDate: task.processDate ? task.processDate.format(DATE_TIME_FORMAT) : null,
      processComment: task.processComment,
      typeId: task.typeId,
      assigneeId: task.assigneeId,
      engagementId: task.engagementId,
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
    const task = this.createFromForm();
    if (task.id !== undefined) {
      this.subscribeToSaveResponse(this.taskService.update(task));
    } else {
      this.subscribeToSaveResponse(this.taskService.create(task));
    }
  }

  private createFromForm(): ITask {
    return {
      ...new Task(),
      id: this.editForm.get(['id'])!.value,
      sequence: this.editForm.get(['sequence'])!.value,
      isSystem: this.editForm.get(['isSystem'])!.value,
      processDelay: this.editForm.get(['processDelay'])!.value,
      delay1: this.editForm.get(['delay1'])!.value,
      delay2: this.editForm.get(['delay2'])!.value,
      viewed: this.editForm.get(['viewed'])!.value,
      createdDate: this.editForm.get(['createdDate'])!.value
        ? moment(this.editForm.get(['createdDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      processDate: this.editForm.get(['processDate'])!.value
        ? moment(this.editForm.get(['processDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      processComment: this.editForm.get(['processComment'])!.value,
      typeId: this.editForm.get(['typeId'])!.value,
      assigneeId: this.editForm.get(['assigneeId'])!.value,
      engagementId: this.editForm.get(['engagementId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITask>>): void {
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
