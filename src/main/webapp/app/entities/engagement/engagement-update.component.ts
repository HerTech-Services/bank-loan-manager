import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IEngagement, Engagement } from 'app/shared/model/engagement.model';
import { EngagementService } from './engagement.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { ITask } from 'app/shared/model/task.model';
import { TaskService } from 'app/entities/task/task.service';
import { IEngagementType } from 'app/shared/model/engagement-type.model';
import { EngagementTypeService } from 'app/entities/engagement-type/engagement-type.service';
import { IStatus } from 'app/shared/model/status.model';
import { StatusService } from 'app/entities/status/status.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IClient } from 'app/shared/model/client.model';
import { ClientService } from 'app/entities/client/client.service';
import { ICompte } from 'app/shared/model/compte.model';
import { CompteService } from 'app/entities/compte/compte.service';

type SelectableEntity = ITask | IEngagementType | IStatus | IUser | IClient | ICompte;

@Component({
  selector: 'jhi-engagement-update',
  templateUrl: './engagement-update.component.html',
})
export class EngagementUpdateComponent implements OnInit {
  isSaving = false;
  currenttasks: ITask[] = [];
  engagementtypes: IEngagementType[] = [];
  statuses: IStatus[] = [];
  users: IUser[] = [];
  clients: IClient[] = [];
  comptes: ICompte[] = [];
  startDateDp: any;
  enDateDp: any;
  submissionDateDp: any;

  editForm = this.fb.group({
    id: [],
    identifier: [null, [Validators.maxLength(32)]],
    scoring: [null, [Validators.required]],
    subject: [null, [Validators.required]],
    amount: [null, [Validators.required]],
    delay: [null, [Validators.required, Validators.min(1)]],
    payment: [null, [Validators.required]],
    delayed: [null, [Validators.required, Validators.min(0)]],
    reimbursement: [null, [Validators.required]],
    analyse: [],
    isStop: [],
    isDeleted: [],
    startDate: [],
    enDate: [],
    createdDate: [],
    updatedDate: [],
    deletedDate: [],
    stopedDate: [],
    submissionDate: [],
    currentTaskId: [],
    typeId: [null, Validators.required],
    statusId: [null, Validators.required],
    decisionId: [],
    createdById: [],
    clientId: [null, Validators.required],
    compteId: [null, Validators.required],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected engagementService: EngagementService,
    protected taskService: TaskService,
    protected engagementTypeService: EngagementTypeService,
    protected statusService: StatusService,
    protected userService: UserService,
    protected clientService: ClientService,
    protected compteService: CompteService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ engagement }) => {
      if (!engagement.id) {
        const today = moment().startOf('day');
        engagement.createdDate = today;
        engagement.updatedDate = today;
        engagement.deletedDate = today;
        engagement.stopedDate = today;
      }

      this.updateForm(engagement);

      this.taskService
        .query({ filter: 'engagement-is-null' })
        .pipe(
          map((res: HttpResponse<ITask[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: ITask[]) => {
          if (!engagement.currentTaskId) {
            this.currenttasks = resBody;
          } else {
            this.taskService
              .find(engagement.currentTaskId)
              .pipe(
                map((subRes: HttpResponse<ITask>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: ITask[]) => (this.currenttasks = concatRes));
          }
        });

      this.engagementTypeService.query().subscribe((res: HttpResponse<IEngagementType[]>) => (this.engagementtypes = res.body || []));

      this.statusService.query().subscribe((res: HttpResponse<IStatus[]>) => (this.statuses = res.body || []));

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.clientService.query().subscribe((res: HttpResponse<IClient[]>) => (this.clients = res.body || []));

      this.compteService.query().subscribe((res: HttpResponse<ICompte[]>) => (this.comptes = res.body || []));
    });
  }

  updateForm(engagement: IEngagement): void {
    this.editForm.patchValue({
      id: engagement.id,
      identifier: engagement.identifier,
      scoring: engagement.scoring,
      subject: engagement.subject,
      amount: engagement.amount,
      delay: engagement.delay,
      payment: engagement.payment,
      delayed: engagement.delayed,
      reimbursement: engagement.reimbursement,
      analyse: engagement.analyse,
      isStop: engagement.isStop,
      isDeleted: engagement.isDeleted,
      startDate: engagement.startDate,
      enDate: engagement.enDate,
      createdDate: engagement.createdDate ? engagement.createdDate.format(DATE_TIME_FORMAT) : null,
      updatedDate: engagement.updatedDate ? engagement.updatedDate.format(DATE_TIME_FORMAT) : null,
      deletedDate: engagement.deletedDate ? engagement.deletedDate.format(DATE_TIME_FORMAT) : null,
      stopedDate: engagement.stopedDate ? engagement.stopedDate.format(DATE_TIME_FORMAT) : null,
      submissionDate: engagement.submissionDate,
      currentTaskId: engagement.currentTaskId,
      typeId: engagement.typeId,
      statusId: engagement.statusId,
      decisionId: engagement.decisionId,
      createdById: engagement.createdById,
      clientId: engagement.clientId,
      compteId: engagement.compteId,
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
    const engagement = this.createFromForm();
    if (engagement.id !== undefined) {
      this.subscribeToSaveResponse(this.engagementService.update(engagement));
    } else {
      this.subscribeToSaveResponse(this.engagementService.create(engagement));
    }
  }

  private createFromForm(): IEngagement {
    return {
      ...new Engagement(),
      id: this.editForm.get(['id'])!.value,
      identifier: this.editForm.get(['identifier'])!.value,
      scoring: this.editForm.get(['scoring'])!.value,
      subject: this.editForm.get(['subject'])!.value,
      amount: this.editForm.get(['amount'])!.value,
      delay: this.editForm.get(['delay'])!.value,
      payment: this.editForm.get(['payment'])!.value,
      delayed: this.editForm.get(['delayed'])!.value,
      reimbursement: this.editForm.get(['reimbursement'])!.value,
      analyse: this.editForm.get(['analyse'])!.value,
      isStop: this.editForm.get(['isStop'])!.value,
      isDeleted: this.editForm.get(['isDeleted'])!.value,
      startDate: this.editForm.get(['startDate'])!.value,
      enDate: this.editForm.get(['enDate'])!.value,
      createdDate: this.editForm.get(['createdDate'])!.value
        ? moment(this.editForm.get(['createdDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      updatedDate: this.editForm.get(['updatedDate'])!.value
        ? moment(this.editForm.get(['updatedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      deletedDate: this.editForm.get(['deletedDate'])!.value
        ? moment(this.editForm.get(['deletedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      stopedDate: this.editForm.get(['stopedDate'])!.value ? moment(this.editForm.get(['stopedDate'])!.value, DATE_TIME_FORMAT) : undefined,
      submissionDate: this.editForm.get(['submissionDate'])!.value,
      currentTaskId: this.editForm.get(['currentTaskId'])!.value,
      typeId: this.editForm.get(['typeId'])!.value,
      statusId: this.editForm.get(['statusId'])!.value,
      decisionId: this.editForm.get(['decisionId'])!.value,
      createdById: this.editForm.get(['createdById'])!.value,
      clientId: this.editForm.get(['clientId'])!.value,
      compteId: this.editForm.get(['compteId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEngagement>>): void {
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
