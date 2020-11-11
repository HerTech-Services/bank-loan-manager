import { element, by, ElementFinder } from 'protractor';

export class TaskTypeComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-task-type div table .btn-danger'));
  title = element.all(by.css('jhi-task-type div h2#page-heading span')).first();
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

export class TaskTypeUpdatePage {
  pageTitle = element(by.id('jhi-task-type-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  labelInput = element(by.id('field_label'));
  identifierInput = element(by.id('field_identifier'));
  delayInput = element(by.id('field_delay'));
  isEnabledInput = element(by.id('field_isEnabled'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setLabelInput(label: string): Promise<void> {
    await this.labelInput.sendKeys(label);
  }

  async getLabelInput(): Promise<string> {
    return await this.labelInput.getAttribute('value');
  }

  async setIdentifierInput(identifier: string): Promise<void> {
    await this.identifierInput.sendKeys(identifier);
  }

  async getIdentifierInput(): Promise<string> {
    return await this.identifierInput.getAttribute('value');
  }

  async setDelayInput(delay: string): Promise<void> {
    await this.delayInput.sendKeys(delay);
  }

  async getDelayInput(): Promise<string> {
    return await this.delayInput.getAttribute('value');
  }

  getIsEnabledInput(): ElementFinder {
    return this.isEnabledInput;
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

export class TaskTypeDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-taskType-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-taskType'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
