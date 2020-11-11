import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IEmploye, Employe } from 'app/shared/model/employe.model';
import { EmployeService } from './employe.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-employe-update',
  templateUrl: './employe-update.component.html',
})
export class EmployeUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    codBnk: [null, [Validators.maxLength(4)]],
    codEmp: [null, [Validators.maxLength(6)]],
    rsEmp: [null, [Validators.maxLength(50)]],
    nomEmp: [],
    prenomEmp: [],
    fctEmp: [null, [Validators.maxLength(50)]],
    adrEmp: [null, [Validators.maxLength(50)]],
    teEmp: [null, [Validators.maxLength(50)]],
    typEnmp: [null, [Validators.maxLength(50)]],
    numMat: [null, [Validators.maxLength(50)]],
    userId: [],
  });

  constructor(
    protected employeService: EmployeService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ employe }) => {
      this.updateForm(employe);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
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
      userId: employe.userId,
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
      userId: this.editForm.get(['userId'])!.value,
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

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IUser): any {
    return item.id;
  }
}
