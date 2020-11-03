import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPoste, Poste } from 'app/shared/model/poste.model';
import { PosteService } from './poste.service';

@Component({
  selector: 'jhi-poste-update',
  templateUrl: './poste-update.component.html',
})
export class PosteUpdateComponent implements OnInit {
  isSaving = false;
  postes: IPoste[] = [];

  editForm = this.fb.group({
    id: [],
    code: [null, [Validators.required, Validators.maxLength(32)]],
    label: [null, [Validators.required]],
    shortLabel: [null, [Validators.maxLength(50)]],
    entity: [],
    enabled: [null, [Validators.required]],
    adrs1: [],
    adrs2: [],
    adrs3: [],
    city: [],
    parentPostes: [],
  });

  constructor(protected posteService: PosteService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ poste }) => {
      this.updateForm(poste);

      this.posteService.query().subscribe((res: HttpResponse<IPoste[]>) => (this.postes = res.body || []));
    });
  }

  updateForm(poste: IPoste): void {
    this.editForm.patchValue({
      id: poste.id,
      code: poste.code,
      label: poste.label,
      shortLabel: poste.shortLabel,
      entity: poste.entity,
      enabled: poste.enabled,
      adrs1: poste.adrs1,
      adrs2: poste.adrs2,
      adrs3: poste.adrs3,
      city: poste.city,
      parentPostes: poste.parentPostes,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const poste = this.createFromForm();
    if (poste.id !== undefined) {
      this.subscribeToSaveResponse(this.posteService.update(poste));
    } else {
      this.subscribeToSaveResponse(this.posteService.create(poste));
    }
  }

  private createFromForm(): IPoste {
    return {
      ...new Poste(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      label: this.editForm.get(['label'])!.value,
      shortLabel: this.editForm.get(['shortLabel'])!.value,
      entity: this.editForm.get(['entity'])!.value,
      enabled: this.editForm.get(['enabled'])!.value,
      adrs1: this.editForm.get(['adrs1'])!.value,
      adrs2: this.editForm.get(['adrs2'])!.value,
      adrs3: this.editForm.get(['adrs3'])!.value,
      city: this.editForm.get(['city'])!.value,
      parentPostes: this.editForm.get(['parentPostes'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPoste>>): void {
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

  trackById(index: number, item: IPoste): any {
    return item.id;
  }

  getSelected(selectedVals: IPoste[], option: IPoste): IPoste {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
