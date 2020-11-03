import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IAttachment, Attachment } from 'app/shared/model/attachment.model';
import { AttachmentService } from './attachment.service';
import { IEngagement } from 'app/shared/model/engagement.model';
import { EngagementService } from 'app/entities/engagement/engagement.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IStatus } from 'app/shared/model/status.model';
import { StatusService } from 'app/entities/status/status.service';

type SelectableEntity = IEngagement | IUser | IStatus | IAttachment;

@Component({
  selector: 'jhi-attachment-update',
  templateUrl: './attachment-update.component.html',
})
export class AttachmentUpdateComponent implements OnInit {
  isSaving = false;
  engagements: IEngagement[] = [];
  users: IUser[] = [];
  statuses: IStatus[] = [];
  attachments: IAttachment[] = [];

  editForm = this.fb.group({
    id: [],
    label: [],
    format: [null, [Validators.required, Validators.maxLength(32)]],
    createdDate: [],
    updatedDate: [],
    version: [],
    path: [],
    filename: [],
    filesize: [],
    validationDate: [],
    engagementId: [null, Validators.required],
    userId: [],
    updatedById: [],
    statusId: [],
    origins: [],
  });

  constructor(
    protected attachmentService: AttachmentService,
    protected engagementService: EngagementService,
    protected userService: UserService,
    protected statusService: StatusService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ attachment }) => {
      if (!attachment.id) {
        const today = moment().startOf('day');
        attachment.createdDate = today;
        attachment.updatedDate = today;
        attachment.validationDate = today;
      }

      this.updateForm(attachment);

      this.engagementService
        .query({ 'attachmentId.specified': 'false' })
        .pipe(
          map((res: HttpResponse<IEngagement[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IEngagement[]) => {
          if (!attachment.engagementId) {
            this.engagements = resBody;
          } else {
            this.engagementService
              .find(attachment.engagementId)
              .pipe(
                map((subRes: HttpResponse<IEngagement>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IEngagement[]) => (this.engagements = concatRes));
          }
        });

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.statusService.query().subscribe((res: HttpResponse<IStatus[]>) => (this.statuses = res.body || []));

      this.attachmentService.query().subscribe((res: HttpResponse<IAttachment[]>) => (this.attachments = res.body || []));
    });
  }

  updateForm(attachment: IAttachment): void {
    this.editForm.patchValue({
      id: attachment.id,
      label: attachment.label,
      format: attachment.format,
      createdDate: attachment.createdDate ? attachment.createdDate.format(DATE_TIME_FORMAT) : null,
      updatedDate: attachment.updatedDate ? attachment.updatedDate.format(DATE_TIME_FORMAT) : null,
      version: attachment.version,
      path: attachment.path,
      filename: attachment.filename,
      filesize: attachment.filesize,
      validationDate: attachment.validationDate ? attachment.validationDate.format(DATE_TIME_FORMAT) : null,
      engagementId: attachment.engagementId,
      userId: attachment.userId,
      updatedById: attachment.updatedById,
      statusId: attachment.statusId,
      origins: attachment.origins,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const attachment = this.createFromForm();
    if (attachment.id !== undefined) {
      this.subscribeToSaveResponse(this.attachmentService.update(attachment));
    } else {
      this.subscribeToSaveResponse(this.attachmentService.create(attachment));
    }
  }

  private createFromForm(): IAttachment {
    return {
      ...new Attachment(),
      id: this.editForm.get(['id'])!.value,
      label: this.editForm.get(['label'])!.value,
      format: this.editForm.get(['format'])!.value,
      createdDate: this.editForm.get(['createdDate'])!.value
        ? moment(this.editForm.get(['createdDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      updatedDate: this.editForm.get(['updatedDate'])!.value
        ? moment(this.editForm.get(['updatedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      version: this.editForm.get(['version'])!.value,
      path: this.editForm.get(['path'])!.value,
      filename: this.editForm.get(['filename'])!.value,
      filesize: this.editForm.get(['filesize'])!.value,
      validationDate: this.editForm.get(['validationDate'])!.value
        ? moment(this.editForm.get(['validationDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      engagementId: this.editForm.get(['engagementId'])!.value,
      userId: this.editForm.get(['userId'])!.value,
      updatedById: this.editForm.get(['updatedById'])!.value,
      statusId: this.editForm.get(['statusId'])!.value,
      origins: this.editForm.get(['origins'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAttachment>>): void {
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

  getSelected(selectedVals: IAttachment[], option: IAttachment): IAttachment {
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
