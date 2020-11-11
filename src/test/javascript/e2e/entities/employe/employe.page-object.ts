import { element, by, ElementFinder } from 'protractor';

export class EmployeComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-employe div table .btn-danger'));
  title = element.all(by.css('jhi-employe div h2#page-heading span')).first();
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

export class EmployeUpdatePage {
  pageTitle = element(by.id('jhi-employe-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  codBnkInput = element(by.id('field_codBnk'));
  codEmpInput = element(by.id('field_codEmp'));
  rsEmpInput = element(by.id('field_rsEmp'));
  nomEmpInput = element(by.id('field_nomEmp'));
  prenomEmpInput = element(by.id('field_prenomEmp'));
  fctEmpInput = element(by.id('field_fctEmp'));
  adrEmpInput = element(by.id('field_adrEmp'));
  teEmpInput = element(by.id('field_teEmp'));
  typEnmpInput = element(by.id('field_typEnmp'));
  numMatInput = element(by.id('field_numMat'));

  userSelect = element(by.id('field_user'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setCodBnkInput(codBnk: string): Promise<void> {
    await this.codBnkInput.sendKeys(codBnk);
  }

  async getCodBnkInput(): Promise<string> {
    return await this.codBnkInput.getAttribute('value');
  }

  async setCodEmpInput(codEmp: string): Promise<void> {
    await this.codEmpInput.sendKeys(codEmp);
  }

  async getCodEmpInput(): Promise<string> {
    return await this.codEmpInput.getAttribute('value');
  }

  async setRsEmpInput(rsEmp: string): Promise<void> {
    await this.rsEmpInput.sendKeys(rsEmp);
  }

  async getRsEmpInput(): Promise<string> {
    return await this.rsEmpInput.getAttribute('value');
  }

  async setNomEmpInput(nomEmp: string): Promise<void> {
    await this.nomEmpInput.sendKeys(nomEmp);
  }

  async getNomEmpInput(): Promise<string> {
    return await this.nomEmpInput.getAttribute('value');
  }

  async setPrenomEmpInput(prenomEmp: string): Promise<void> {
    await this.prenomEmpInput.sendKeys(prenomEmp);
  }

  async getPrenomEmpInput(): Promise<string> {
    return await this.prenomEmpInput.getAttribute('value');
  }

  async setFctEmpInput(fctEmp: string): Promise<void> {
    await this.fctEmpInput.sendKeys(fctEmp);
  }

  async getFctEmpInput(): Promise<string> {
    return await this.fctEmpInput.getAttribute('value');
  }

  async setAdrEmpInput(adrEmp: string): Promise<void> {
    await this.adrEmpInput.sendKeys(adrEmp);
  }

  async getAdrEmpInput(): Promise<string> {
    return await this.adrEmpInput.getAttribute('value');
  }

  async setTeEmpInput(teEmp: string): Promise<void> {
    await this.teEmpInput.sendKeys(teEmp);
  }

  async getTeEmpInput(): Promise<string> {
    return await this.teEmpInput.getAttribute('value');
  }

  async setTypEnmpInput(typEnmp: string): Promise<void> {
    await this.typEnmpInput.sendKeys(typEnmp);
  }

  async getTypEnmpInput(): Promise<string> {
    return await this.typEnmpInput.getAttribute('value');
  }

  async setNumMatInput(numMat: string): Promise<void> {
    await this.numMatInput.sendKeys(numMat);
  }

  async getNumMatInput(): Promise<string> {
    return await this.numMatInput.getAttribute('value');
  }

  async userSelectLastOption(): Promise<void> {
    await this.userSelect.all(by.tagName('option')).last().click();
  }

  async userSelectOption(option: string): Promise<void> {
    await this.userSelect.sendKeys(option);
  }

  getUserSelect(): ElementFinder {
    return this.userSelect;
  }

  async getUserSelectedOption(): Promise<string> {
    return await this.userSelect.element(by.css('option:checked')).getText();
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

export class EmployeDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-employe-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-employe'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
