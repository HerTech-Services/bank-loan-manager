import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IReject, Reject } from 'app/shared/model/reject.model';
import { RejectService } from './reject.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IRejectType } from 'app/shared/model/reject-type.model';
import { RejectTypeService } from 'app/entities/reject-type/reject-type.service';
import { IEngagement } from 'app/shared/model/engagement.model';
import { EngagementService } from 'app/entities/engagement/engagement.service';

type SelectableEntity = IRejectType | IEngagement;

@Component({
  selector: 'jhi-reject-update',
  templateUrl: './reject-update.component.html',
})
export class RejectUpdateComponent implements OnInit {
  isSaving = false;
  rejecttypes: IRejectType[] = [];
  engagements: IEngagement[] = [];

  editForm = this.fb.group({
    id: [],
    createdDate: [null, [Validators.required]],
    capital: [null, [Validators.required, Validators.min(0)]],
    interests: [null, [Validators.required, Validators.min(0)]],
    penalties: [null, [Validators.required, Validators.min(0)]],
    accessories: [null, [Validators.required, Validators.min(0)]],
    description: [],
    typeId: [null, Validators.required],
    engagementId: [null, Validators.required],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected rejectService: RejectService,
    protected rejectTypeService: RejectTypeService,
    protected engagementService: EngagementService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ reject }) => {
      if (!reject.id) {
        const today = moment().startOf('day');
        reject.createdDate = today;
      }

      this.updateForm(reject);

      this.rejectTypeService.query().subscribe((res: HttpResponse<IRejectType[]>) => (this.rejecttypes = res.body || []));

      this.engagementService.query().subscribe((res: HttpResponse<IEngagement[]>) => (this.engagements = res.body || []));
    });
  }

  updateForm(reject: IReject): void {
    this.editForm.patchValue({
      id: reject.id,
      createdDate: reject.createdDate ? reject.createdDate.format(DATE_TIME_FORMAT) : null,
      capital: reject.capital,
      interests: reject.interests,
      penalties: reject.penalties,
      accessories: reject.accessories,
      description: reject.description,
      typeId: reject.typeId,
      engagementId: reject.engagementId,
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
    const reject = this.createFromForm();
    if (reject.id !== undefined) {
      this.subscribeToSaveResponse(this.rejectService.update(reject));
    } else {
      this.subscribeToSaveResponse(this.rejectService.create(reject));
    }
  }

  private createFromForm(): IReject {
    return {
      ...new Reject(),
      id: this.editForm.get(['id'])!.value,
      createdDate: this.editForm.get(['createdDate'])!.value
        ? moment(this.editForm.get(['createdDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      capital: this.editForm.get(['capital'])!.value,
      interests: this.editForm.get(['interests'])!.value,
      penalties: this.editForm.get(['penalties'])!.value,
      accessories: this.editForm.get(['accessories'])!.value,
      description: this.editForm.get(['description'])!.value,
      typeId: this.editForm.get(['typeId'])!.value,
      engagementId: this.editForm.get(['engagementId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IReject>>): void {
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
