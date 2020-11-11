import { element, by, ElementFinder } from 'protractor';

export class StatusComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-status div table .btn-danger'));
  title = element.all(by.css('jhi-status div h2#page-heading span')).first();
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

export class StatusUpdatePage {
  pageTitle = element(by.id('jhi-status-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  labelInput = element(by.id('field_label'));
  isSystemInput = element(by.id('field_isSystem'));
  imgFilenameInput = element(by.id('field_imgFilename'));
  canBeSearchedInput = element(by.id('field_canBeSearched'));
  canBeModifiedInput = element(by.id('field_canBeModified'));
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

  getIsSystemInput(): ElementFinder {
    return this.isSystemInput;
  }

  async setImgFilenameInput(imgFilename: string): Promise<void> {
    await this.imgFilenameInput.sendKeys(imgFilename);
  }

  async getImgFilenameInput(): Promise<string> {
    return await this.imgFilenameInput.getAttribute('value');
  }

  getCanBeSearchedInput(): ElementFinder {
    return this.canBeSearchedInput;
  }

  getCanBeModifiedInput(): ElementFinder {
    return this.canBeModifiedInput;
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

export class StatusDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-status-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-status'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
