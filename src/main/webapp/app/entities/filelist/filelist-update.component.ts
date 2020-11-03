import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IFilelist, Filelist } from 'app/shared/model/filelist.model';
import { FilelistService } from './filelist.service';

@Component({
  selector: 'jhi-filelist-update',
  templateUrl: './filelist-update.component.html',
})
export class FilelistUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    label: [null, [Validators.required, Validators.maxLength(50)]],
    identifier: [null, [Validators.maxLength(10)]],
    description: [],
  });

  constructor(protected filelistService: FilelistService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ filelist }) => {
      this.updateForm(filelist);
    });
  }

  updateForm(filelist: IFilelist): void {
    this.editForm.patchValue({
      id: filelist.id,
      label: filelist.label,
      identifier: filelist.identifier,
      description: filelist.description,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const filelist = this.createFromForm();
    if (filelist.id !== undefined) {
      this.subscribeToSaveResponse(this.filelistService.update(filelist));
    } else {
      this.subscribeToSaveResponse(this.filelistService.create(filelist));
    }
  }

  private createFromForm(): IFilelist {
    return {
      ...new Filelist(),
      id: this.editForm.get(['id'])!.value,
      label: this.editForm.get(['label'])!.value,
      identifier: this.editForm.get(['identifier'])!.value,
      description: this.editForm.get(['description'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFilelist>>): void {
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
