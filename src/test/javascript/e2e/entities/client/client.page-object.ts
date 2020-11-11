import { element, by, ElementFinder } from 'protractor';

export class ClientComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-client div table .btn-danger'));
  title = element.all(by.css('jhi-client div h2#page-heading span')).first();
  noResult = element(by.id('no-result'));
  entities = element(by.id('entities'));

  async clickOnCreateButton(): Promise<void> {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(): Promise<void> {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons(): Promise<number> {
    return this.deleteButtons.count();
  }

  async getTitle(): Promise<string> {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class ClientUpdatePage {
  pageTitle = element(by.id('jhi-client-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  codBnkInput = element(by.id('field_codBnk'));
  codCliInput = element(by.id('field_codCli'));
  nomCliInput = element(by.id('field_nomCli'));
  mendCliInput = element(by.id('field_mendCli'));
  sfCliSelect = element(by.id('field_sfCli'));
  titreInput = element(by.id('field_titre'));
  datNaiInput = element(by.id('field_datNai'));
  lieuNaiInput = element(by.id('field_lieuNai'));
  natCliInput = element(by.id('field_natCli'));
  lngCliInput = element(by.id('field_lngCli'));
  socCliInput = element(by.id('field_socCli'));
  emploiInput = element(by.id('field_emploi'));
  sexeSelect = element(by.id('field_sexe'));
  numCniInput = element(by.id('field_numCni'));
  datCniInput = element(by.id('field_datCni'));
  finCniInput = element(by.id('field_finCni'));
  lieuCniInput = element(by.id('field_lieuCni'));
  autoCniInput = element(by.id('field_autoCni'));
  adrInput = element(by.id('field_adr'));
  telInput = element(by.id('field_tel'));
  villeInput = element(by.id('field_ville'));
  siteInput = element(by.id('field_site'));
  locInput = element(by.id('field_loc'));
  faxInput = element(by.id('field_fax'));
  agnceInput = element(by.id('field_agnce'));
  mailInput = element(by.id('field_mail'));
  paysInput = element(by.id('field_pays'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setCodBnkInput(codBnk: string): Promise<void> {
    await this.codBnkInput.sendKeys(codBnk);
  }

  async getCodBnkInput(): Promise<string> {
    return await this.codBnkInput.getAttribute('value');
  }

  async setCodCliInput(codCli: string): Promise<void> {
    await this.codCliInput.sendKeys(codCli);
  }

  async getCodCliInput(): Promise<string> {
    return await this.codCliInput.getAttribute('value');
  }

  async setNomCliInput(nomCli: string): Promise<void> {
    await this.nomCliInput.sendKeys(nomCli);
  }

  async getNomCliInput(): Promise<string> {
    return await this.nomCliInput.getAttribute('value');
  }

  async setMendCliInput(mendCli: string): Promise<void> {
    await this.mendCliInput.sendKeys(mendCli);
  }

  async getMendCliInput(): Promise<string> {
    return await this.mendCliInput.getAttribute('value');
  }

  async setSfCliSelect(sfCli: string): Promise<void> {
    await this.sfCliSelect.sendKeys(sfCli);
  }

  async getSfCliSelect(): Promise<string> {
    return await this.sfCliSelect.element(by.css('option:checked')).getText();
  }

  async sfCliSelectLastOption(): Promise<void> {
    await this.sfCliSelect.all(by.tagName('option')).last().click();
  }

  async setTitreInput(titre: string): Promise<void> {
    await this.titreInput.sendKeys(titre);
  }

  async getTitreInput(): Promise<string> {
    return await this.titreInput.getAttribute('value');
  }

  async setDatNaiInput(datNai: string): Promise<void> {
    await this.datNaiInput.sendKeys(datNai);
  }

  async getDatNaiInput(): Promise<string> {
    return await this.datNaiInput.getAttribute('value');
  }

  async setLieuNaiInput(lieuNai: string): Promise<void> {
    await this.lieuNaiInput.sendKeys(lieuNai);
  }

  async getLieuNaiInput(): Promise<string> {
    return await this.lieuNaiInput.getAttribute('value');
  }

  async setNatCliInput(natCli: string): Promise<void> {
    await this.natCliInput.sendKeys(natCli);
  }

  async getNatCliInput(): Promise<string> {
    return await this.natCliInput.getAttribute('value');
  }

  async setLngCliInput(lngCli: string): Promise<void> {
    await this.lngCliInput.sendKeys(lngCli);
  }

  async getLngCliInput(): Promise<string> {
    return await this.lngCliInput.getAttribute('value');
  }

  async setSocCliInput(socCli: string): Promise<void> {
    await this.socCliInput.sendKeys(socCli);
  }

  async getSocCliInput(): Promise<string> {
    return await this.socCliInput.getAttribute('value');
  }

  async setEmploiInput(emploi: string): Promise<void> {
    await this.emploiInput.sendKeys(emploi);
  }

  async getEmploiInput(): Promise<string> {
    return await this.emploiInput.getAttribute('value');
  }

  async setSexeSelect(sexe: string): Promise<void> {
    await this.sexeSelect.sendKeys(sexe);
  }

  async getSexeSelect(): Promise<string> {
    return await this.sexeSelect.element(by.css('option:checked')).getText();
  }

  async sexeSelectLastOption(): Promise<void> {
    await this.sexeSelect.all(by.tagName('option')).last().click();
  }

  async setNumCniInput(numCni: string): Promise<void> {
    await this.numCniInput.sendKeys(numCni);
  }

  async getNumCniInput(): Promise<string> {
    return await this.numCniInput.getAttribute('value');
  }

  async setDatCniInput(datCni: string): Promise<void> {
    await this.datCniInput.sendKeys(datCni);
  }

  async getDatCniInput(): Promise<string> {
    return await this.datCniInput.getAttribute('value');
  }

  async setFinCniInput(finCni: string): Promise<void> {
    await this.finCniInput.sendKeys(finCni);
  }

  async getFinCniInput(): Promise<string> {
    return await this.finCniInput.getAttribute('value');
  }

  async setLieuCniInput(lieuCni: string): Promise<void> {
    await this.lieuCniInput.sendKeys(lieuCni);
  }

  async getLieuCniInput(): Promise<string> {
    return await this.lieuCniInput.getAttribute('value');
  }

  async setAutoCniInput(autoCni: string): Promise<void> {
    await this.autoCniInput.sendKeys(autoCni);
  }

  async getAutoCniInput(): Promise<string> {
    return await this.autoCniInput.getAttribute('value');
  }

  async setAdrInput(adr: string): Promise<void> {
    await this.adrInput.sendKeys(adr);
  }

  async getAdrInput(): Promise<string> {
    return await this.adrInput.getAttribute('value');
  }

  async setTelInput(tel: string): Promise<void> {
    await this.telInput.sendKeys(tel);
  }

  async getTelInput(): Promise<string> {
    return await this.telInput.getAttribute('value');
  }

  async setVilleInput(ville: string): Promise<void> {
    await this.villeInput.sendKeys(ville);
  }

  async getVilleInput(): Promise<string> {
    return await this.villeInput.getAttribute('value');
  }

  async setSiteInput(site: string): Promise<void> {
    await this.siteInput.sendKeys(site);
  }

  async getSiteInput(): Promise<string> {
    return await this.siteInput.getAttribute('value');
  }

  async setLocInput(loc: string): Promise<void> {
    await this.locInput.sendKeys(loc);
  }

  async getLocInput(): Promise<string> {
    return await this.locInput.getAttribute('value');
  }

  async setFaxInput(fax: string): Promise<void> {
    await this.faxInput.sendKeys(fax);
  }

  async getFaxInput(): Promise<string> {
    return await this.faxInput.getAttribute('value');
  }

  async setAgnceInput(agnce: string): Promise<void> {
    await this.agnceInput.sendKeys(agnce);
  }

  async getAgnceInput(): Promise<string> {
    return await this.agnceInput.getAttribute('value');
  }

  async setMailInput(mail: string): Promise<void> {
    await this.mailInput.sendKeys(mail);
  }

  async getMailInput(): Promise<string> {
    return await this.mailInput.getAttribute('value');
  }

  async setPaysInput(pays: string): Promise<void> {
    await this.paysInput.sendKeys(pays);
  }

  async getPaysInput(): Promise<string> {
    return await this.paysInput.getAttribute('value');
  }

  async save(): Promise<void> {
    await this.saveButton.click();
  }

  async cancel(): Promise<void> {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class ClientDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-client-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-client'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
