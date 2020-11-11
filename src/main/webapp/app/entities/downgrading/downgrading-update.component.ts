import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IDowngrading, Downgrading } from 'app/shared/model/downgrading.model';
import { DowngradingService } from './downgrading.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IDowngradingStep } from 'app/shared/model/downgrading-step.model';
import { DowngradingStepService } from 'app/entities/downgrading-step/downgrading-step.service';
import { IEngagement } from 'app/shared/model/engagement.model';
import { EngagementService } from 'app/entities/engagement/engagement.service';

type SelectableEntity = IDowngradingStep | IEngagement;

@Component({
  selector: 'jhi-downgrading-update',
  templateUrl: './downgrading-update.component.html',
})
export class DowngradingUpdateComponent implements OnInit {
  isSaving = false;
  downgradingsteps: IDowngradingStep[] = [];
  engagements: IEngagement[] = [];

  editForm = this.fb.group({
    id: [],
    createdDate: [null, [Validators.required]],
    capital: [null, [Validators.required, Validators.min(0)]],
    interests: [null, [Validators.required, Validators.min(0)]],
    penalties: [null, [Validators.required, Validators.min(0)]],
    accessories: [null, [Validators.required, Validators.min(0)]],
    description: [null, [Validators.required]],
    stepId: [null, Validators.required],
    engagementId: [null, Validators.required],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected downgradingService: DowngradingService,
    protected downgradingStepService: DowngradingStepService,
    protected engagementService: EngagementService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ downgrading }) => {
      if (!downgrading.id) {
        const today = moment().startOf('day');
        downgrading.createdDate = today;
      }

      this.updateForm(downgrading);

      this.downgradingStepService.query().subscribe((res: HttpResponse<IDowngradingStep[]>) => (this.downgradingsteps = res.body || []));

      this.engagementService.query().subscribe((res: HttpResponse<IEngagement[]>) => (this.engagements = res.body || []));
    });
  }

  updateForm(downgrading: IDowngrading): void {
    this.editForm.patchValue({
      id: downgrading.id,
      createdDate: downgrading.createdDate ? downgrading.createdDate.format(DATE_TIME_FORMAT) : null,
      capital: downgrading.capital,
      interests: downgrading.interests,
      penalties: downgrading.penalties,
      accessories: downgrading.accessories,
      description: downgrading.description,
      stepId: downgrading.stepId,
      engagementId: downgrading.engagementId,
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
    const downgrading = this.createFromForm();
    if (downgrading.id !== undefined) {
      this.subscribeToSaveResponse(this.downgradingService.update(downgrading));
    } else {
      this.subscribeToSaveResponse(this.downgradingService.create(downgrading));
    }
  }

  private createFromForm(): IDowngrading {
    return {
      ...new Downgrading(),
      id: this.editForm.get(['id'])!.value,
      createdDate: this.editForm.get(['createdDate'])!.value
        ? moment(this.editForm.get(['createdDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      capital: this.editForm.get(['capital'])!.value,
      interests: this.editForm.get(['interests'])!.value,
      penalties: this.editForm.get(['penalties'])!.value,
      accessories: this.editForm.get(['accessories'])!.value,
      description: this.editForm.get(['description'])!.value,
      stepId: this.editForm.get(['stepId'])!.value,
      engagementId: this.editForm.get(['engagementId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDowngrading>>): void {
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
