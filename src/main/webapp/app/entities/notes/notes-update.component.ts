import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { INotes, Notes } from 'app/shared/model/notes.model';
import { NotesService } from './notes.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IEngagement } from 'app/shared/model/engagement.model';
import { EngagementService } from 'app/entities/engagement/engagement.service';

type SelectableEntity = IUser | IEngagement;

@Component({
  selector: 'jhi-notes-update',
  templateUrl: './notes-update.component.html',
})
export class NotesUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  engagements: IEngagement[] = [];

  editForm = this.fb.group({
    id: [],
    description: [],
    createdDate: [],
    userId: [null, Validators.required],
    engagementId: [null, Validators.required],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected notesService: NotesService,
    protected userService: UserService,
    protected engagementService: EngagementService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ notes }) => {
      if (!notes.id) {
        const today = moment().startOf('day');
        notes.createdDate = today;
      }

      this.updateForm(notes);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.engagementService.query().subscribe((res: HttpResponse<IEngagement[]>) => (this.engagements = res.body || []));
    });
  }

  updateForm(notes: INotes): void {
    this.editForm.patchValue({
      id: notes.id,
      description: notes.description,
      createdDate: notes.createdDate ? notes.createdDate.format(DATE_TIME_FORMAT) : null,
      userId: notes.userId,
      engagementId: notes.engagementId,
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
    const notes = this.createFromForm();
    if (notes.id !== undefined) {
      this.subscribeToSaveResponse(this.notesService.update(notes));
    } else {
      this.subscribeToSaveResponse(this.notesService.create(notes));
    }
  }

  private createFromForm(): INotes {
    return {
      ...new Notes(),
      id: this.editForm.get(['id'])!.value,
      description: this.editForm.get(['description'])!.value,
      createdDate: this.editForm.get(['createdDate'])!.value
        ? moment(this.editForm.get(['createdDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      userId: this.editForm.get(['userId'])!.value,
      engagementId: this.editForm.get(['engagementId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INotes>>): void {
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
