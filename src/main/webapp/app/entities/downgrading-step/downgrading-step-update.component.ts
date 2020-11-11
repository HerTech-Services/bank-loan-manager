import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IDowngradingStep, DowngradingStep } from 'app/shared/model/downgrading-step.model';
import { DowngradingStepService } from './downgrading-step.service';
import { IScoring } from 'app/shared/model/scoring.model';
import { ScoringService } from 'app/entities/scoring/scoring.service';

@Component({
  selector: 'jhi-downgrading-step-update',
  templateUrl: './downgrading-step-update.component.html',
})
export class DowngradingStepUpdateComponent implements OnInit {
  isSaving = false;
  scorings: IScoring[] = [];

  editForm = this.fb.group({
    id: [],
    label: [null, [Validators.required]],
    identifier: [null, [Validators.maxLength(32)]],
    scoringId: [null, Validators.required],
  });

  constructor(
    protected downgradingStepService: DowngradingStepService,
    protected scoringService: ScoringService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ downgradingStep }) => {
      this.updateForm(downgradingStep);

      this.scoringService.query().subscribe((res: HttpResponse<IScoring[]>) => (this.scorings = res.body || []));
    });
  }

  updateForm(downgradingStep: IDowngradingStep): void {
    this.editForm.patchValue({
      id: downgradingStep.id,
      label: downgradingStep.label,
      identifier: downgradingStep.identifier,
      scoringId: downgradingStep.scoringId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const downgradingStep = this.createFromForm();
    if (downgradingStep.id !== undefined) {
      this.subscribeToSaveResponse(this.downgradingStepService.update(downgradingStep));
    } else {
      this.subscribeToSaveResponse(this.downgradingStepService.create(downgradingStep));
    }
  }

  private createFromForm(): IDowngradingStep {
    return {
      ...new DowngradingStep(),
      id: this.editForm.get(['id'])!.value,
      label: this.editForm.get(['label'])!.value,
      identifier: this.editForm.get(['identifier'])!.value,
      scoringId: this.editForm.get(['scoringId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDowngradingStep>>): void {
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
