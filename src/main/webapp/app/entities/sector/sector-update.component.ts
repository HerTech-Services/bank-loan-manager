import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ISector, Sector } from 'app/shared/model/sector.model';
import { SectorService } from './sector.service';
import { IScoring } from 'app/shared/model/scoring.model';
import { ScoringService } from 'app/entities/scoring/scoring.service';

@Component({
  selector: 'jhi-sector-update',
  templateUrl: './sector-update.component.html',
})
export class SectorUpdateComponent implements OnInit {
  isSaving = false;
  scorings: IScoring[] = [];

  editForm = this.fb.group({
    id: [],
    label: [null, [Validators.required]],
    identifier: [null, [Validators.maxLength(32)]],
    scoringId: [null, Validators.required],
  });

  constructor(
    protected sectorService: SectorService,
    protected scoringService: ScoringService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sector }) => {
      this.updateForm(sector);

      this.scoringService.query().subscribe((res: HttpResponse<IScoring[]>) => (this.scorings = res.body || []));
    });
  }

  updateForm(sector: ISector): void {
    this.editForm.patchValue({
      id: sector.id,
      label: sector.label,
      identifier: sector.identifier,
      scoringId: sector.scoringId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const sector = this.createFromForm();
    if (sector.id !== undefined) {
      this.subscribeToSaveResponse(this.sectorService.update(sector));
    } else {
      this.subscribeToSaveResponse(this.sectorService.create(sector));
    }
  }

  private createFromForm(): ISector {
    return {
      ...new Sector(),
      id: this.editForm.get(['id'])!.value,
      label: this.editForm.get(['label'])!.value,
      identifier: this.editForm.get(['identifier'])!.value,
      scoringId: this.editForm.get(['scoringId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISector>>): void {
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
