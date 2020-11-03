import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IHistory, History } from 'app/shared/model/history.model';
import { HistoryService } from './history.service';
import { AlertError } from 'app/shared/alert/alert-error.model';

@Component({
  selector: 'jhi-history-update',
  templateUrl: './history-update.component.html',
})
export class HistoryUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    tableName: [null, [Validators.required, Validators.maxLength(50)]],
    recordId: [null, [Validators.required]],
    eventType: [],
    eventDate: [],
    info: [],
    host: [null, [Validators.required, Validators.maxLength(50)]],
    properties: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected historyService: HistoryService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ history }) => {
      if (!history.id) {
        const today = moment().startOf('day');
        history.eventDate = today;
      }

      this.updateForm(history);
    });
  }

  updateForm(history: IHistory): void {
    this.editForm.patchValue({
      id: history.id,
      tableName: history.tableName,
      recordId: history.recordId,
      eventType: history.eventType,
      eventDate: history.eventDate ? history.eventDate.format(DATE_TIME_FORMAT) : null,
      info: history.info,
      host: history.host,
      properties: history.properties,
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
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
    const history = this.createFromForm();
    if (history.id !== undefined) {
      this.subscribeToSaveResponse(this.historyService.update(history));
    } else {
      this.subscribeToSaveResponse(this.historyService.create(history));
    }
  }

  private createFromForm(): IHistory {
    return {
      ...new History(),
      id: this.editForm.get(['id'])!.value,
      tableName: this.editForm.get(['tableName'])!.value,
      recordId: this.editForm.get(['recordId'])!.value,
      eventType: this.editForm.get(['eventType'])!.value,
      eventDate: this.editForm.get(['eventDate'])!.value ? moment(this.editForm.get(['eventDate'])!.value, DATE_TIME_FORMAT) : undefined,
      info: this.editForm.get(['info'])!.value,
      host: this.editForm.get(['host'])!.value,
      properties: this.editForm.get(['properties'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IHistory>>): void {
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
