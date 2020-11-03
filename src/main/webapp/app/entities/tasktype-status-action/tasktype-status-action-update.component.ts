import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITasktypeStatusAction, TasktypeStatusAction } from 'app/shared/model/tasktype-status-action.model';
import { TasktypeStatusActionService } from './tasktype-status-action.service';
import { ITaskType } from 'app/shared/model/task-type.model';
import { TaskTypeService } from 'app/entities/task-type/task-type.service';
import { IAction } from 'app/shared/model/action.model';
import { ActionService } from 'app/entities/action/action.service';
import { IStatus } from 'app/shared/model/status.model';
import { StatusService } from 'app/entities/status/status.service';

type SelectableEntity = ITaskType | IAction | IStatus;

@Component({
  selector: 'jhi-tasktype-status-action-update',
  templateUrl: './tasktype-status-action-update.component.html',
})
export class TasktypeStatusActionUpdateComponent implements OnInit {
  isSaving = false;
  tasktypes: ITaskType[] = [];
  actions: IAction[] = [];
  statuses: IStatus[] = [];

  editForm = this.fb.group({
    id: [],
    tasktypeId: [],
    actionId: [],
    statusId: [],
  });

  constructor(
    protected tasktypeStatusActionService: TasktypeStatusActionService,
    protected taskTypeService: TaskTypeService,
    protected actionService: ActionService,
    protected statusService: StatusService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tasktypeStatusAction }) => {
      this.updateForm(tasktypeStatusAction);

      this.taskTypeService.query().subscribe((res: HttpResponse<ITaskType[]>) => (this.tasktypes = res.body || []));

      this.actionService.query().subscribe((res: HttpResponse<IAction[]>) => (this.actions = res.body || []));

      this.statusService.query().subscribe((res: HttpResponse<IStatus[]>) => (this.statuses = res.body || []));
    });
  }

  updateForm(tasktypeStatusAction: ITasktypeStatusAction): void {
    this.editForm.patchValue({
      id: tasktypeStatusAction.id,
      tasktypeId: tasktypeStatusAction.tasktypeId,
      actionId: tasktypeStatusAction.actionId,
      statusId: tasktypeStatusAction.statusId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tasktypeStatusAction = this.createFromForm();
    if (tasktypeStatusAction.id !== undefined) {
      this.subscribeToSaveResponse(this.tasktypeStatusActionService.update(tasktypeStatusAction));
    } else {
      this.subscribeToSaveResponse(this.tasktypeStatusActionService.create(tasktypeStatusAction));
    }
  }

  private createFromForm(): ITasktypeStatusAction {
    return {
      ...new TasktypeStatusAction(),
      id: this.editForm.get(['id'])!.value,
      tasktypeId: this.editForm.get(['tasktypeId'])!.value,
      actionId: this.editForm.get(['actionId'])!.value,
      statusId: this.editForm.get(['statusId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITasktypeStatusAction>>): void {
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
