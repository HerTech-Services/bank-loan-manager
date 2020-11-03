import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IUserPoste, UserPoste } from 'app/shared/model/user-poste.model';
import { UserPosteService } from './user-poste.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IPoste } from 'app/shared/model/poste.model';
import { PosteService } from 'app/entities/poste/poste.service';

type SelectableEntity = IUser | IPoste;

@Component({
  selector: 'jhi-user-poste-update',
  templateUrl: './user-poste-update.component.html',
})
export class UserPosteUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  postes: IPoste[] = [];

  editForm = this.fb.group({
    id: [],
    role: [],
    isPrimary: [null, [Validators.required]],
    userId: [null, Validators.required],
    entitiesId: [null, Validators.required],
  });

  constructor(
    protected userPosteService: UserPosteService,
    protected userService: UserService,
    protected posteService: PosteService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userPoste }) => {
      this.updateForm(userPoste);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.posteService.query().subscribe((res: HttpResponse<IPoste[]>) => (this.postes = res.body || []));
    });
  }

  updateForm(userPoste: IUserPoste): void {
    this.editForm.patchValue({
      id: userPoste.id,
      role: userPoste.role,
      isPrimary: userPoste.isPrimary,
      userId: userPoste.userId,
      entitiesId: userPoste.entitiesId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const userPoste = this.createFromForm();
    if (userPoste.id !== undefined) {
      this.subscribeToSaveResponse(this.userPosteService.update(userPoste));
    } else {
      this.subscribeToSaveResponse(this.userPosteService.create(userPoste));
    }
  }

  private createFromForm(): IUserPoste {
    return {
      ...new UserPoste(),
      id: this.editForm.get(['id'])!.value,
      role: this.editForm.get(['role'])!.value,
      isPrimary: this.editForm.get(['isPrimary'])!.value,
      userId: this.editForm.get(['userId'])!.value,
      entitiesId: this.editForm.get(['entitiesId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserPoste>>): void {
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
