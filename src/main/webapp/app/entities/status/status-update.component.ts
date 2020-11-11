import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IStatus, Status } from 'app/shared/model/status.model';
import { StatusService } from './status.service';

@Component({
  selector: 'jhi-status-update',
  templateUrl: './status-update.component.html',
})
export class StatusUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    label: [null, [Validators.required, Validators.maxLength(50)]],
    isSystem: [null, [Validators.required]],
    imgFilename: [],
    canBeSearched: [null, [Validators.required]],
    canBeModified: [null, [Validators.required]],
    isEnabled: [null, [Validators.required]],
  });

  constructor(protected statusService: StatusService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ status }) => {
      this.updateForm(status);
    });
  }

  updateForm(status: IStatus): void {
    this.editForm.patchValue({
      id: status.id,
      label: status.label,
      isSystem: status.isSystem,
      imgFilename: status.imgFilename,
      canBeSearched: status.canBeSearched,
      canBeModified: status.canBeModified,
      isEnabled: status.isEnabled,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const status = this.createFromForm();
    if (status.id !== undefined) {
      this.subscribeToSaveResponse(this.statusService.update(status));
    } else {
      this.subscribeToSaveResponse(this.statusService.create(status));
    }
  }

  private createFromForm(): IStatus {
    return {
      ...new Status(),
      id: this.editForm.get(['id'])!.value,
      label: this.editForm.get(['label'])!.value,
      isSystem: this.editForm.get(['isSystem'])!.value,
      imgFilename: this.editForm.get(['imgFilename'])!.value,
      canBeSearched: this.editForm.get(['canBeSearched'])!.value,
      canBeModified: this.editForm.get(['canBeModified'])!.value,
      isEnabled: this.editForm.get(['isEnabled'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStatus>>): void {
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
