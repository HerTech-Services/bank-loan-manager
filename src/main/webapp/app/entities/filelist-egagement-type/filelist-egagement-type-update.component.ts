import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IFilelistEgagementType, FilelistEgagementType } from 'app/shared/model/filelist-egagement-type.model';
import { FilelistEgagementTypeService } from './filelist-egagement-type.service';
import { IEngagementType } from 'app/shared/model/engagement-type.model';
import { EngagementTypeService } from 'app/entities/engagement-type/engagement-type.service';
import { IFilelist } from 'app/shared/model/filelist.model';
import { FilelistService } from 'app/entities/filelist/filelist.service';

type SelectableEntity = IEngagementType | IFilelist;

@Component({
  selector: 'jhi-filelist-egagement-type-update',
  templateUrl: './filelist-egagement-type-update.component.html',
})
export class FilelistEgagementTypeUpdateComponent implements OnInit {
  isSaving = false;
  engagementtypes: IEngagementType[] = [];
  filelists: IFilelist[] = [];

  editForm = this.fb.group({
    id: [],
    mandatory: [],
    engagementTypeId: [],
    filelistId: [],
  });

  constructor(
    protected filelistEgagementTypeService: FilelistEgagementTypeService,
    protected engagementTypeService: EngagementTypeService,
    protected filelistService: FilelistService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ filelistEgagementType }) => {
      this.updateForm(filelistEgagementType);

      this.engagementTypeService.query().subscribe((res: HttpResponse<IEngagementType[]>) => (this.engagementtypes = res.body || []));

      this.filelistService.query().subscribe((res: HttpResponse<IFilelist[]>) => (this.filelists = res.body || []));
    });
  }

  updateForm(filelistEgagementType: IFilelistEgagementType): void {
    this.editForm.patchValue({
      id: filelistEgagementType.id,
      mandatory: filelistEgagementType.mandatory,
      engagementTypeId: filelistEgagementType.engagementTypeId,
      filelistId: filelistEgagementType.filelistId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const filelistEgagementType = this.createFromForm();
    if (filelistEgagementType.id !== undefined) {
      this.subscribeToSaveResponse(this.filelistEgagementTypeService.update(filelistEgagementType));
    } else {
      this.subscribeToSaveResponse(this.filelistEgagementTypeService.create(filelistEgagementType));
    }
  }

  private createFromForm(): IFilelistEgagementType {
    return {
      ...new FilelistEgagementType(),
      id: this.editForm.get(['id'])!.value,
      mandatory: this.editForm.get(['mandatory'])!.value,
      engagementTypeId: this.editForm.get(['engagementTypeId'])!.value,
      filelistId: this.editForm.get(['filelistId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFilelistEgagementType>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
