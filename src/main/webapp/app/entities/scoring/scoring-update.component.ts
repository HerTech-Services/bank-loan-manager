import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IScoring, Scoring } from 'app/shared/model/scoring.model';
import { ScoringService } from './scoring.service';

@Component({
  selector: 'jhi-scoring-update',
  templateUrl: './scoring-update.component.html',
})
export class ScoringUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    label: [null, [Validators.required]],
    score: [null, [Validators.required, Validators.min(0)]],
  });

  constructor(protected scoringService: ScoringService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ scoring }) => {
      this.updateForm(scoring);
    });
  }

  updateForm(scoring: IScoring): void {
    this.editForm.patchValue({
      id: scoring.id,
      label: scoring.label,
      score: scoring.score,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const scoring = this.createFromForm();
    if (scoring.id !== undefined) {
      this.subscribeToSaveResponse(this.scoringService.update(scoring));
    } else {
      this.subscribeToSaveResponse(this.scoringService.create(scoring));
    }
  }

  private createFromForm(): IScoring {
    return {
      ...new Scoring(),
      id: this.editForm.get(['id'])!.value,
      label: this.editForm.get(['label'])!.value,
      score: this.editForm.get(['score'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IScoring>>): void {
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
