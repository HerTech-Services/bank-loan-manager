import { element, by, ElementFinder } from 'protractor';

export class EmployeEntiteComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-employe-entite div table .btn-danger'));
  title = element.all(by.css('jhi-employe-entite div h2#page-heading span')).first();
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

export class EmployeEntiteUpdatePage {
  pageTitle = element(by.id('jhi-employe-entite-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  roleInput = element(by.id('field_role'));
  isPrimaryInput = element(by.id('field_isPrimary'));
  isChiefInput = element(by.id('field_isChief'));

  employeSelect = element(by.id('field_employe'));
  entiteSelect = element(by.id('field_entite'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setRoleInput(role: string): Promise<void> {
    await this.roleInput.sendKeys(role);
  }

  async getRoleInput(): Promise<string> {
    return await this.roleInput.getAttribute('value');
  }

  getIsPrimaryInput(): ElementFinder {
    return this.isPrimaryInput;
  }

  getIsChiefInput(): ElementFinder {
    return this.isChiefInput;
  }

  async employeSelectLastOption(): Promise<void> {
    await this.employeSelect.all(by.tagName('option')).last().click();
  }

  async employeSelectOption(option: string): Promise<void> {
    await this.employeSelect.sendKeys(option);
  }

  getEmployeSelect(): ElementFinder {
    return this.employeSelect;
  }

  async getEmployeSelectedOption(): Promise<string> {
    return await this.employeSelect.element(by.css('option:checked')).getText();
  }

  async entiteSelectLastOption(): Promise<void> {
    await this.entiteSelect.all(by.tagName('option')).last().click();
  }

  async entiteSelectOption(option: string): Promise<void> {
    await this.entiteSelect.sendKeys(option);
  }

  getEntiteSelect(): ElementFinder {
    return this.entiteSelect;
  }

  async getEntiteSelectedOption(): Promise<string> {
    return await this.entiteSelect.element(by.css('option:checked')).getText();
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

export class EmployeEntiteDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-employeEntite-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-employeEntite'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
