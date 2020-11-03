import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICompte, Compte } from 'app/shared/model/compte.model';
import { CompteService } from './compte.service';
import { IClient } from 'app/shared/model/client.model';
import { ClientService } from 'app/entities/client/client.service';

@Component({
  selector: 'jhi-compte-update',
  templateUrl: './compte-update.component.html',
})
export class CompteUpdateComponent implements OnInit {
  isSaving = false;
  clients: IClient[] = [];

  editForm = this.fb.group({
    id: [],
    codBnk: [null, [Validators.maxLength(4)]],
    codCpt: [null, [Validators.maxLength(15)]],
    numCpt: [null, [Validators.maxLength(15)]],
    libCpt: [null, [Validators.maxLength(20)]],
    numCpta: [null, [Validators.maxLength(15)]],
    numAgc: [null, [Validators.maxLength(3)]],
    typCpt: [null, [Validators.maxLength(4)]],
    etat: [null, [Validators.maxLength(4)]],
    codCli: [null, [Validators.maxLength(7)]],
    numCtr: [null, [Validators.maxLength(50)]],
    codeFor: [null, [Validators.maxLength(50)]],
    clientId: [null, Validators.required],
  });

  constructor(
    protected compteService: CompteService,
    protected clientService: ClientService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ compte }) => {
      this.updateForm(compte);

      this.clientService.query().subscribe((res: HttpResponse<IClient[]>) => (this.clients = res.body || []));
    });
  }

  updateForm(compte: ICompte): void {
    this.editForm.patchValue({
      id: compte.id,
      codBnk: compte.codBnk,
      codCpt: compte.codCpt,
      numCpt: compte.numCpt,
      libCpt: compte.libCpt,
      numCpta: compte.numCpta,
      numAgc: compte.numAgc,
      typCpt: compte.typCpt,
      etat: compte.etat,
      codCli: compte.codCli,
      numCtr: compte.numCtr,
      codeFor: compte.codeFor,
      clientId: compte.clientId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const compte = this.createFromForm();
    if (compte.id !== undefined) {
      this.subscribeToSaveResponse(this.compteService.update(compte));
    } else {
      this.subscribeToSaveResponse(this.compteService.create(compte));
    }
  }

  private createFromForm(): ICompte {
    return {
      ...new Compte(),
      id: this.editForm.get(['id'])!.value,
      codBnk: this.editForm.get(['codBnk'])!.value,
      codCpt: this.editForm.get(['codCpt'])!.value,
      numCpt: this.editForm.get(['numCpt'])!.value,
      libCpt: this.editForm.get(['libCpt'])!.value,
      numCpta: this.editForm.get(['numCpta'])!.value,
      numAgc: this.editForm.get(['numAgc'])!.value,
      typCpt: this.editForm.get(['typCpt'])!.value,
      etat: this.editForm.get(['etat'])!.value,
      codCli: this.editForm.get(['codCli'])!.value,
      numCtr: this.editForm.get(['numCtr'])!.value,
      codeFor: this.editForm.get(['codeFor'])!.value,
      clientId: this.editForm.get(['clientId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICompte>>): void {
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

  trackById(index: number, item: IClient): any {
    return item.id;
  }
}
