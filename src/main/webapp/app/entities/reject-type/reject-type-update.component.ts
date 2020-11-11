import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IRejectType, RejectType } from 'app/shared/model/reject-type.model';
import { RejectTypeService } from './reject-type.service';
import { IScoring } from 'app/shared/model/scoring.model';
import { ScoringService } from 'app/entities/scoring/scoring.service';

@Component({
  selector: 'jhi-reject-type-update',
  templateUrl: './reject-type-update.component.html',
})
export class RejectTypeUpdateComponent implements OnInit {
  isSaving = false;
  scorings: IScoring[] = [];

  editForm = this.fb.group({
    id: [],
    label: [null, [Validators.required]],
    identifier: [null, [Validators.maxLength(32)]],
    scoringId: [null, Validators.required],
  });

  constructor(
    protected rejectTypeService: RejectTypeService,
    protected scoringService: ScoringService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ rejectType }) => {
      this.updateForm(rejectType);

      this.scoringService.query().subscribe((res: HttpResponse<IScoring[]>) => (this.scorings = res.body || []));
    });
  }

  updateForm(rejectType: IRejectType): void {
    this.editForm.patchValue({
      id: rejectType.id,
      label: rejectType.label,
      identifier: rejectType.identifier,
      scoringId: rejectType.scoringId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const rejectType = this.createFromForm();
    if (rejectType.id !== undefined) {
      this.subscribeToSaveResponse(this.rejectTypeService.update(rejectType));
    } else {
      this.subscribeToSaveResponse(this.rejectTypeService.create(rejectType));
    }
  }

  private createFromForm(): IRejectType {
    return {
      ...new RejectType(),
      id: this.editForm.get(['id'])!.value,
      label: this.editForm.get(['label'])!.value,
      identifier: this.editForm.get(['identifier'])!.value,
      scoringId: this.editForm.get(['scoringId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRejectType>>): void {
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

  trackById(index: number, item: IScoring): any {
    return item.id;
  }
}
