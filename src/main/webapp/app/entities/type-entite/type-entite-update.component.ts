import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITypeEntite, TypeEntite } from 'app/shared/model/type-entite.model';
import { TypeEntiteService } from './type-entite.service';

@Component({
  selector: 'jhi-type-entite-update',
  templateUrl: './type-entite-update.component.html',
})
export class TypeEntiteUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    identifier: [null, [Validators.required, Validators.maxLength(32)]],
    label: [null, [Validators.required]],
    shortLabel: [null, [Validators.maxLength(50)]],
  });

  constructor(protected typeEntiteService: TypeEntiteService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeEntite }) => {
      this.updateForm(typeEntite);
    });
  }

  updateForm(typeEntite: ITypeEntite): void {
    this.editForm.patchValue({
      id: typeEntite.id,
      identifier: typeEntite.identifier,
      label: typeEntite.label,
      shortLabel: typeEntite.shortLabel,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const typeEntite = this.createFromForm();
    if (typeEntite.id !== undefined) {
      this.subscribeToSaveResponse(this.typeEntiteService.update(typeEntite));
    } else {
      this.subscribeToSaveResponse(this.typeEntiteService.create(typeEntite));
    }
  }

  private createFromForm(): ITypeEntite {
    return {
      ...new TypeEntite(),
      id: this.editForm.get(['id'])!.value,
      identifier: this.editForm.get(['identifier'])!.value,
      label: this.editForm.get(['label'])!.value,
      shortLabel: this.editForm.get(['shortLabel'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITypeEntite>>): void {
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
