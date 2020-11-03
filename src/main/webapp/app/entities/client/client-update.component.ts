import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IClient, Client } from 'app/shared/model/client.model';
import { ClientService } from './client.service';

@Component({
  selector: 'jhi-client-update',
  templateUrl: './client-update.component.html',
})
export class ClientUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    codBnk: [null, [Validators.maxLength(4)]],
    codCli: [null, [Validators.maxLength(7)]],
    nomCli: [],
    mendCli: [],
    sfCli: [],
    titre: [null, [Validators.maxLength(15)]],
    datNai: [null, [Validators.maxLength(10)]],
    lieuNai: [null, [Validators.maxLength(50)]],
    natCli: [null, [Validators.maxLength(20)]],
    lngCli: [null, [Validators.maxLength(20)]],
    socCli: [null, [Validators.maxLength(20)]],
    emploi: [null, [Validators.maxLength(20)]],
    sexe: [],
    numCni: [null, [Validators.maxLength(20)]],
    datCni: [null, [Validators.maxLength(10)]],
    finCni: [null, [Validators.maxLength(10)]],
    lieuCni: [null, [Validators.maxLength(20)]],
    autoCni: [null, [Validators.maxLength(50)]],
    adr: [null, [Validators.maxLength(50)]],
    tel: [null, [Validators.maxLength(50)]],
    ville: [null, [Validators.maxLength(50)]],
    site: [null, [Validators.maxLength(50)]],
    loc: [null, [Validators.maxLength(50)]],
    fax: [null, [Validators.maxLength(50)]],
    agnce: [null, [Validators.maxLength(50)]],
    mail: [null, [Validators.maxLength(50)]],
    pays: [null, [Validators.maxLength(50)]],
  });

  constructor(protected clientService: ClientService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ client }) => {
      this.updateForm(client);
    });
  }

  updateForm(client: IClient): void {
    this.editForm.patchValue({
      id: client.id,
      codBnk: client.codBnk,
      codCli: client.codCli,
      nomCli: client.nomCli,
      mendCli: client.mendCli,
      sfCli: client.sfCli,
      titre: client.titre,
      datNai: client.datNai,
      lieuNai: client.lieuNai,
      natCli: client.natCli,
      lngCli: client.lngCli,
      socCli: client.socCli,
      emploi: client.emploi,
      sexe: client.sexe,
      numCni: client.numCni,
      datCni: client.datCni,
      finCni: client.finCni,
      lieuCni: client.lieuCni,
      autoCni: client.autoCni,
      adr: client.adr,
      tel: client.tel,
      ville: client.ville,
      site: client.site,
      loc: client.loc,
      fax: client.fax,
      agnce: client.agnce,
      mail: client.mail,
      pays: client.pays,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const client = this.createFromForm();
    if (client.id !== undefined) {
      this.subscribeToSaveResponse(this.clientService.update(client));
    } else {
      this.subscribeToSaveResponse(this.clientService.create(client));
    }
  }

  private createFromForm(): IClient {
    return {
      ...new Client(),
      id: this.editForm.get(['id'])!.value,
      codBnk: this.editForm.get(['codBnk'])!.value,
      codCli: this.editForm.get(['codCli'])!.value,
      nomCli: this.editForm.get(['nomCli'])!.value,
      mendCli: this.editForm.get(['mendCli'])!.value,
      sfCli: this.editForm.get(['sfCli'])!.value,
      titre: this.editForm.get(['titre'])!.value,
      datNai: this.editForm.get(['datNai'])!.value,
      lieuNai: this.editForm.get(['lieuNai'])!.value,
      natCli: this.editForm.get(['natCli'])!.value,
      lngCli: this.editForm.get(['lngCli'])!.value,
      socCli: this.editForm.get(['socCli'])!.value,
      emploi: this.editForm.get(['emploi'])!.value,
      sexe: this.editForm.get(['sexe'])!.value,
      numCni: this.editForm.get(['numCni'])!.value,
      datCni: this.editForm.get(['datCni'])!.value,
      finCni: this.editForm.get(['finCni'])!.value,
      lieuCni: this.editForm.get(['lieuCni'])!.value,
      autoCni: this.editForm.get(['autoCni'])!.value,
      adr: this.editForm.get(['adr'])!.value,
      tel: this.editForm.get(['tel'])!.value,
      ville: this.editForm.get(['ville'])!.value,
      site: this.editForm.get(['site'])!.value,
      loc: this.editForm.get(['loc'])!.value,
      fax: this.editForm.get(['fax'])!.value,
      agnce: this.editForm.get(['agnce'])!.value,
      mail: this.editForm.get(['mail'])!.value,
      pays: this.editForm.get(['pays'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IClient>>): void {
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
}
