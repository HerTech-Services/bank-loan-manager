import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITaskType, TaskType } from 'app/shared/model/task-type.model';
import { TaskTypeService } from './task-type.service';

@Component({
  selector: 'jhi-task-type-update',
  templateUrl: './task-type-update.component.html',
})
export class TaskTypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    label: [null, [Validators.required]],
    identifier: [null, [Validators.required, Validators.maxLength(50)]],
    delay: [],
    isEnabled: [],
  });

  constructor(protected taskTypeService: TaskTypeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ taskType }) => {
      this.updateForm(taskType);
    });
  }

  updateForm(taskType: ITaskType): void {
    this.editForm.patchValue({
      id: taskType.id,
      label: taskType.label,
      identifier: taskType.identifier,
      delay: taskType.delay,
      isEnabled: taskType.isEnabled,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const taskType = this.createFromForm();
    if (taskType.id !== undefined) {
      this.subscribeToSaveResponse(this.taskTypeService.update(taskType));
    } else {
      this.subscribeToSaveResponse(this.taskTypeService.create(taskType));
    }
  }

  private createFromForm(): ITaskType {
    return {
      ...new TaskType(),
      id: this.editForm.get(['id'])!.value,
      label: this.editForm.get(['label'])!.value,
      identifier: this.editForm.get(['identifier'])!.value,
      delay: this.editForm.get(['delay'])!.value,
      isEnabled: this.editForm.get(['isEnabled'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITaskType>>): void {
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
}
