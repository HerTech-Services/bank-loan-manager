import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IParameters, Parameters } from 'app/shared/model/parameters.model';
import { ParametersService } from './parameters.service';
import { AlertError } from 'app/shared/alert/alert-error.model';

@Component({
  selector: 'jhi-parameters-update',
  templateUrl: './parameters-update.component.html',
})
export class ParametersUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    identifier: [null, [Validators.required, Validators.maxLength(50)]],
    description: [],
    paramValueString: [],
    paramValueInt: [],
    paramValueDate: [],
    paramValueJson: [],
    updatedDate: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected parametersService: ParametersService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ parameters }) => {
      if (!parameters.id) {
        const today = moment().startOf('day');
        parameters.paramValueDate = today;
        parameters.updatedDate = today;
      }

      this.updateForm(parameters);
    });
  }

  updateForm(parameters: IParameters): void {
    this.editForm.patchValue({
      id: parameters.id,
      identifier: parameters.identifier,
      description: parameters.description,
      paramValueString: parameters.paramValueString,
      paramValueInt: parameters.paramValueInt,
      paramValueDate: parameters.paramValueDate ? parameters.paramValueDate.format(DATE_TIME_FORMAT) : null,
      paramValueJson: parameters.paramValueJson,
      updatedDate: parameters.updatedDate ? parameters.updatedDate.format(DATE_TIME_FORMAT) : null,
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
    const parameters = this.createFromForm();
    if (parameters.id !== undefined) {
      this.subscribeToSaveResponse(this.parametersService.update(parameters));
    } else {
      this.subscribeToSaveResponse(this.parametersService.create(parameters));
    }
  }

  private createFromForm(): IParameters {
    return {
      ...new Parameters(),
      id: this.editForm.get(['id'])!.value,
      identifier: this.editForm.get(['identifier'])!.value,
      description: this.editForm.get(['description'])!.value,
      paramValueString: this.editForm.get(['paramValueString'])!.value,
      paramValueInt: this.editForm.get(['paramValueInt'])!.value,
      paramValueDate: this.editForm.get(['paramValueDate'])!.value
        ? moment(this.editForm.get(['paramValueDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      paramValueJson: this.editForm.get(['paramValueJson'])!.value,
      updatedDate: this.editForm.get(['updatedDate'])!.value
        ? moment(this.editForm.get(['updatedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IParameters>>): void {
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
