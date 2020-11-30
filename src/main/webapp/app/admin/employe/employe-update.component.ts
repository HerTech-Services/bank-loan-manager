import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IEmploye, Employe } from 'app/shared/model/employe.model';
import { EmployeService } from './employe.service';
import { IUser, User } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { LogService } from 'app/log.service';
import { LANGUAGES } from 'app/core/language/language.constants';

@Component({
  selector: 'jhi-employe-update',
  templateUrl: './employe-update.component.html',
})
export class EmployeUpdateComponent implements OnInit {
  employe!: Employe;
  loading = false;
  isSaving = false;
  hasUser = false;
  createUser = true;
  creationMode = true;
  users: IUser[] = [];
  user!: User;
  languages = LANGUAGES;
  authorities: string[] = [];

  active = 1;
  editForm = this.fb.group({
    id: [],
    codBnk: [null, [Validators.maxLength(4)]],
    codEmp: [null, [Validators.maxLength(6)]],
    rsEmp: [null, [Validators.maxLength(50)]],
    nomEmp: [null, [Validators.required, Validators.maxLength(100)]],
    prenomEmp: [null, [Validators.maxLength(100)]],
    fctEmp: [null, [Validators.maxLength(50)]],
    adrEmp: [null, [Validators.maxLength(50)]],
    teEmp: [null, [Validators.maxLength(50)]],
    typEnmp: [null, [Validators.maxLength(50)]],
    numMat: [null, [Validators.maxLength(50)]],
  });

  userForm = this.fb.group({
    id: [],
    login: [
      '',
      [
        Validators.required,
        Validators.minLength(1),
        Validators.maxLength(50),
        Validators.pattern('^[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$|^[_.@A-Za-z0-9-]+$'),
      ],
    ],
    firstName: ['', [Validators.maxLength(50)]],
    lastName: ['', [Validators.maxLength(50)]],
    email: ['', [Validators.minLength(5), Validators.maxLength(254), Validators.email]],
    activated: [],
    langKey: [],
    authorities: [],
  });

  breadCrumbItems?: Array<{}>;

  constructor(
    protected employeService: EmployeService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    private logService: LogService
  ) {}

  ngOnInit(): void {
    this.loading = true;
    // TODO: ADD ON CREATION MODE
    this.breadCrumbItems = [
      { label: 'global.menu.admin.main' },
      { label: 'bankLoanManagerApp.employe.home.title' },
      { label: 'bankLoanManagerApp.employe.home.createOrEditLabel', active: true },
    ];
    this.activatedRoute.data.subscribe(({ employe }) => {
      this.employe = employe;
      this.logService.log(this.employe.id);
      if (this.employe.id === undefined) {
        this.creationMode = true;
      } else {
        this.creationMode = false;
      }

      if (this.employe.userId) {
        this.hasUser = true;
        this.createUser = false;
      } else {
        this.createUser = true;
        this.user = new User();
        this.user.activated = true;
        this.userForm.patchValue({
          lastName: this.employe.nomEmp,
          firstName: this.employe.prenomEmp,
          email: null,
        });
        this.logService.log('Utilisateur patché'); // TODO: DELETE THIS LINE
      }

      this.logService.log(employe); // TODO: DELETE THIS LINE
      this.updateForm(employe);
      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => {
        this.users = res.body || [];
        if (employe && this.hasUser) {
          const eUser: IUser[] = this.users.filter((user: IUser) => user.id === this.employe.userId);
          if (eUser.length > 0) {
            this.user = eUser[0];
            this.updateUserForm(this.user);
          }
        }
      });

      this.userService.authorities().subscribe(authorities => {
        this.authorities = authorities;
      });

      this.loading = false;
    });
  }

  updateForm(employe: IEmploye): void {
    this.editForm.patchValue({
      id: employe.id,
      codBnk: employe.codBnk,
      codEmp: employe.codEmp,
      rsEmp: employe.rsEmp,
      nomEmp: employe.nomEmp,
      prenomEmp: employe.prenomEmp,
      fctEmp: employe.fctEmp,
      adrEmp: employe.adrEmp,
      teEmp: employe.teEmp,
      typEnmp: employe.typEnmp,
      numMat: employe.numMat,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const employe = this.createFromForm();
    if (employe.id !== undefined) {
      this.subscribeToSaveResponse(this.employeService.update(employe));
    } else {
      this.subscribeToSaveResponse(this.employeService.create(employe));
    }
  }

  saveUser(): void {
    this.isSaving = true;
    this.updateUser(this.user);
    if (this.user.id !== undefined) {
      this.employeService.updateEmployeUser(this.employe, this.user).subscribe(
        (EntityResponseType: HttpResponse<IEmploye>) => this.onUpdateUserSuccess(EntityResponseType),
        () => this.onSaveError()
      );
    } else {
      this.logService.log(this.user);
      this.employeService.createEmployeUser(this.employe, this.user).subscribe(
        (EntityResponseType: HttpResponse<IEmploye>) => this.onSaveUserSuccess(EntityResponseType),
        () => this.onSaveError()
      );
    }
  }

  private createFromForm(): IEmploye {
    return {
      ...new Employe(),
      id: this.editForm.get(['id'])!.value,
      codBnk: this.editForm.get(['codBnk'])!.value,
      codEmp: this.editForm.get(['codEmp'])!.value,
      rsEmp: this.editForm.get(['rsEmp'])!.value,
      nomEmp: this.editForm.get(['nomEmp'])!.value,
      prenomEmp: this.editForm.get(['prenomEmp'])!.value,
      fctEmp: this.editForm.get(['fctEmp'])!.value,
      adrEmp: this.editForm.get(['adrEmp'])!.value,
      teEmp: this.editForm.get(['teEmp'])!.value,
      typEnmp: this.editForm.get(['typEnmp'])!.value,
      numMat: this.editForm.get(['numMat'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmploye>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveUserSuccess(EntityResponseType: HttpResponse<IEmploye>): void {
    this.logService.log(EntityResponseType);
    this.employe = EntityResponseType.body || {};
    this.userService.query().subscribe((res: HttpResponse<IUser[]>) => {
      this.users = res.body || [];
      const eUser: IUser[] = this.users.filter((user: IUser) => user.id === this.employe.userId);
      if (eUser.length > 0) {
        this.user = eUser[0];
        this.updateUserForm(this.user);
        this.hasUser = true;
        this.createUser = false;
        this.creationMode = false;
      }
    });
    this.isSaving = false;
  }

  protected onUpdateUserSuccess(EntityResponseType: HttpResponse<IEmploye>): void {
    this.logService.log(EntityResponseType);
    this.isSaving = false;
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IUser): any {
    return item.id;
  }

  toggleCreateUser(): void {
    this.createUser = !this.createUser;
  }

  private updateUserForm(user: User): void {
    this.userForm.patchValue({
      id: user.id,
      login: user.login,
      firstName: user.firstName,
      lastName: user.lastName,
      email: user.email,
      activated: user.activated,
      langKey: user.langKey,
      authorities: user.authorities,
    });
  }

  private updateUser(user: User): void {
    user.login = this.userForm.get(['login'])!.value;
    user.firstName = this.userForm.get(['firstName'])!.value;
    user.lastName = this.userForm.get(['lastName'])!.value;
    user.email = this.userForm.get(['email'])!.value;
    user.activated = this.userForm.get(['activated'])!.value;
    user.langKey = this.userForm.get(['langKey'])!.value;
    user.authorities = this.userForm.get(['authorities'])!.value;
  }
}
