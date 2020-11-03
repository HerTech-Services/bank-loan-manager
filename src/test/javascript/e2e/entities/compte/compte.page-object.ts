import { element, by, ElementFinder } from 'protractor';

export class CompteComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-compte div table .btn-danger'));
  title = element.all(by.css('jhi-compte div h2#page-heading span')).first();
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

export class CompteUpdatePage {
  pageTitle = element(by.id('jhi-compte-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  codBnkInput = element(by.id('field_codBnk'));
  codCptInput = element(by.id('field_codCpt'));
  numCptInput = element(by.id('field_numCpt'));
  libCptInput = element(by.id('field_libCpt'));
  numCptaInput = element(by.id('field_numCpta'));
  numAgcInput = element(by.id('field_numAgc'));
  typCptInput = element(by.id('field_typCpt'));
  etatInput = element(by.id('field_etat'));
  codCliInput = element(by.id('field_codCli'));
  numCtrInput = element(by.id('field_numCtr'));
  codeForInput = element(by.id('field_codeFor'));

  clientSelect = element(by.id('field_client'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setCodBnkInput(codBnk: string): Promise<void> {
    await this.codBnkInput.sendKeys(codBnk);
  }

  async getCodBnkInput(): Promise<string> {
    return await this.codBnkInput.getAttribute('value');
  }

  async setCodCptInput(codCpt: string): Promise<void> {
    await this.codCptInput.sendKeys(codCpt);
  }

  async getCodCptInput(): Promise<string> {
    return await this.codCptInput.getAttribute('value');
  }

  async setNumCptInput(numCpt: string): Promise<void> {
    await this.numCptInput.sendKeys(numCpt);
  }

  async getNumCptInput(): Promise<string> {
    return await this.numCptInput.getAttribute('value');
  }

  async setLibCptInput(libCpt: string): Promise<void> {
    await this.libCptInput.sendKeys(libCpt);
  }

  async getLibCptInput(): Promise<string> {
    return await this.libCptInput.getAttribute('value');
  }

  async setNumCptaInput(numCpta: string): Promise<void> {
    await this.numCptaInput.sendKeys(numCpta);
  }

  async getNumCptaInput(): Promise<string> {
    return await this.numCptaInput.getAttribute('value');
  }

  async setNumAgcInput(numAgc: string): Promise<void> {
    await this.numAgcInput.sendKeys(numAgc);
  }

  async getNumAgcInput(): Promise<string> {
    return await this.numAgcInput.getAttribute('value');
  }

  async setTypCptInput(typCpt: string): Promise<void> {
    await this.typCptInput.sendKeys(typCpt);
  }

  async getTypCptInput(): Promise<string> {
    return await this.typCptInput.getAttribute('value');
  }

  async setEtatInput(etat: string): Promise<void> {
    await this.etatInput.sendKeys(etat);
  }

  async getEtatInput(): Promise<string> {
    return await this.etatInput.getAttribute('value');
  }

  async setCodCliInput(codCli: string): Promise<void> {
    await this.codCliInput.sendKeys(codCli);
  }

  async getCodCliInput(): Promise<string> {
    return await this.codCliInput.getAttribute('value');
  }

  async setNumCtrInput(numCtr: string): Promise<void> {
    await this.numCtrInput.sendKeys(numCtr);
  }

  async getNumCtrInput(): Promise<string> {
    return await this.numCtrInput.getAttribute('value');
  }

  async setCodeForInput(codeFor: string): Promise<void> {
    await this.codeForInput.sendKeys(codeFor);
  }

  async getCodeForInput(): Promise<string> {
    return await this.codeForInput.getAttribute('value');
  }

  async clientSelectLastOption(): Promise<void> {
    await this.clientSelect.all(by.tagName('option')).last().click();
  }

  async clientSelectOption(option: string): Promise<void> {
    await this.clientSelect.sendKeys(option);
  }

  getClientSelect(): ElementFinder {
    return this.clientSelect;
  }

  async getClientSelectedOption(): Promise<string> {
    return await this.clientSelect.element(by.css('option:checked')).getText();
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

export class CompteDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-compte-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-compte'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
