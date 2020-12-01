import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IEntite, Entite } from 'app/shared/model/entite.model';
import { EntiteService } from './entite.service';
import { AlertError } from 'app/shared/alert/alert-error.model';

@Component({
  selector: 'jhi-entite-update',
  templateUrl: './entite-update.component.html',
})
export class EntiteUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    identifier: [null, [Validators.required, Validators.maxLength(32)]],
    label: [null, [Validators.required]],
    shortLabel: [null, [Validators.maxLength(50)]],
    parent: [],
    isEnabled: [null, [Validators.required]],
    adrs1: [],
    adrs2: [],
    adrs3: [],
    zipcode: [],
    city: [],
    country: [],
    email: [],
    parameters: [],
    parentId: [null, [Validators.maxLength(32)]],
  });
  breadCrumbItems?: Array<{}>;
  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected entiteService: EntiteService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.breadCrumbItems = [
      { label: 'global.menu.admin.main' },
      { label: 'bankLoanManagerApp.entite.home.title' },
      { label: 'bankLoanManagerApp.entite.home.createOrEditLabel', active: true },
    ];
    this.activatedRoute.data.subscribe(({ entite }) => {
      this.updateForm(entite);
    });
  }

  updateForm(entite: IEntite): void {
    this.editForm.patchValue({
      id: entite.id,
      identifier: entite.identifier,
      label: entite.label,
      shortLabel: entite.shortLabel,
      parent: entite.parent,
      isEnabled: entite.isEnabled,
      adrs1: entite.adrs1,
      adrs2: entite.adrs2,
      adrs3: entite.adrs3,
      zipcode: entite.zipcode,
      city: entite.city,
      country: entite.country,
      email: entite.email,
      parameters: entite.parameters,
      parentId: entite.parentId,
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: any, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('bankLoanManagerApp.error', { ...err, key: 'error.file.' + err.key })
      );
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const entite = this.createFromForm();
    if (entite.id !== undefined) {
      this.subscribeToSaveResponse(this.entiteService.update(entite));
    } else {
      this.subscribeToSaveResponse(this.entiteService.create(entite));
    }
  }

  private createFromForm(): IEntite {
    return {
      ...new Entite(),
      id: this.editForm.get(['id'])!.value,
      identifier: this.editForm.get(['identifier'])!.value,
      label: this.editForm.get(['label'])!.value,
      shortLabel: this.editForm.get(['shortLabel'])!.value,
      parent: this.editForm.get(['parent'])!.value,
      isEnabled: this.editForm.get(['isEnabled'])!.value,
      adrs1: this.editForm.get(['adrs1'])!.value,
      adrs2: this.editForm.get(['adrs2'])!.value,
      adrs3: this.editForm.get(['adrs3'])!.value,
      zipcode: this.editForm.get(['zipcode'])!.value,
      city: this.editForm.get(['city'])!.value,
      country: this.editForm.get(['country'])!.value,
      email: this.editForm.get(['email'])!.value,
      parameters: this.editForm.get(['parameters'])!.value,
      parentId: this.editForm.get(['parentId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEntite>>): void {
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
