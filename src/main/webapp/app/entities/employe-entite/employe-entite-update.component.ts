import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IEmployeEntite, EmployeEntite } from 'app/shared/model/employe-entite.model';
import { EmployeEntiteService } from './employe-entite.service';
import { IEmploye } from 'app/shared/model/employe.model';
import { EmployeService } from 'app/admin/employe/employe.service';
import { IEntite } from 'app/shared/model/entite.model';
import { EntiteService } from 'app/admin/entite/entite.service';

type SelectableEntity = IEmploye | IEntite;

@Component({
  selector: 'jhi-employe-entite-update',
  templateUrl: './employe-entite-update.component.html',
})
export class EmployeEntiteUpdateComponent implements OnInit {
  isSaving = false;
  employes: IEmploye[] = [];
  entites: IEntite[] = [];

  editForm = this.fb.group({
    id: [],
    role: [],
    isPrimary: [null, [Validators.required]],
    isChief: [null, [Validators.required]],
    employeId: [null, Validators.required],
    entiteId: [null, Validators.required],
  });

  constructor(
    protected employeEntiteService: EmployeEntiteService,
    protected employeService: EmployeService,
    protected entiteService: EntiteService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ employeEntite }) => {
      this.updateForm(employeEntite);

      this.employeService.query().subscribe((res: HttpResponse<IEmploye[]>) => (this.employes = res.body || []));

      this.entiteService.query().subscribe((res: HttpResponse<IEntite[]>) => (this.entites = res.body || []));
    });
  }

  updateForm(employeEntite: IEmployeEntite): void {
    this.editForm.patchValue({
      id: employeEntite.id,
      role: employeEntite.role,
      isPrimary: employeEntite.isPrimary,
      isChief: employeEntite.isChief,
      employeId: employeEntite.employeId,
      entiteId: employeEntite.entiteId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const employeEntite = this.createFromForm();
    if (employeEntite.id !== undefined) {
      this.subscribeToSaveResponse(this.employeEntiteService.update(employeEntite));
    } else {
      this.subscribeToSaveResponse(this.employeEntiteService.create(employeEntite));
    }
  }

  private createFromForm(): IEmployeEntite {
    return {
      ...new EmployeEntite(),
      id: this.editForm.get(['id'])!.value,
      role: this.editForm.get(['role'])!.value,
      isPrimary: this.editForm.get(['isPrimary'])!.value,
      isChief: this.editForm.get(['isChief'])!.value,
      employeId: this.editForm.get(['employeId'])!.value,
      entiteId: this.editForm.get(['entiteId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmployeEntite>>): void {
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
