import { element, by, ElementFinder } from 'protractor';

export class UserPosteComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-user-poste div table .btn-danger'));
  title = element.all(by.css('jhi-user-poste div h2#page-heading span')).first();
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

export class UserPosteUpdatePage {
  pageTitle = element(by.id('jhi-user-poste-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  roleInput = element(by.id('field_role'));
  isPrimaryInput = element(by.id('field_isPrimary'));

  userSelect = element(by.id('field_user'));
  entitiesSelect = element(by.id('field_entities'));

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

  async entitiesSelectLastOption(): Promise<void> {
    await this.entitiesSelect.all(by.tagName('option')).last().click();
  }

  async entitiesSelectOption(option: string): Promise<void> {
    await this.entitiesSelect.sendKeys(option);
  }

  getEntitiesSelect(): ElementFinder {
    return this.entitiesSelect;
  }

  async getEntitiesSelectedOption(): Promise<string> {
    return await this.entitiesSelect.element(by.css('option:checked')).getText();
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

export class UserPosteDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-userPoste-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-userPoste'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
