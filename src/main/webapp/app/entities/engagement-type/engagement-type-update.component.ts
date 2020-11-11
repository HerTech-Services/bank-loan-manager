import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IEngagementType, EngagementType } from 'app/shared/model/engagement-type.model';
import { EngagementTypeService } from './engagement-type.service';

@Component({
  selector: 'jhi-engagement-type-update',
  templateUrl: './engagement-type-update.component.html',
})
export class EngagementTypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    label: [null, [Validators.required]],
    isEnabled: [null, [Validators.required]],
    processDelay: [null, [Validators.required]],
    delay1: [null, [Validators.required]],
    delay2: [null, [Validators.required]],
  });

  constructor(protected engagementTypeService: EngagementTypeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ engagementType }) => {
      this.updateForm(engagementType);
    });
  }

  updateForm(engagementType: IEngagementType): void {
    this.editForm.patchValue({
      id: engagementType.id,
      label: engagementType.label,
      isEnabled: engagementType.isEnabled,
      processDelay: engagementType.processDelay,
      delay1: engagementType.delay1,
      delay2: engagementType.delay2,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const engagementType = this.createFromForm();
    if (engagementType.id !== undefined) {
      this.subscribeToSaveResponse(this.engagementTypeService.update(engagementType));
    } else {
      this.subscribeToSaveResponse(this.engagementTypeService.create(engagementType));
    }
  }

  private createFromForm(): IEngagementType {
    return {
      ...new EngagementType(),
      id: this.editForm.get(['id'])!.value,
      label: this.editForm.get(['label'])!.value,
      isEnabled: this.editForm.get(['isEnabled'])!.value,
      processDelay: this.editForm.get(['processDelay'])!.value,
      delay1: this.editForm.get(['delay1'])!.value,
      delay2: this.editForm.get(['delay2'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEngagementType>>): void {
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
