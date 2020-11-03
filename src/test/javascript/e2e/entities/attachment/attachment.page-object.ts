import { element, by, ElementFinder } from 'protractor';

export class AttachmentComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-attachment div table .btn-danger'));
  title = element.all(by.css('jhi-attachment div h2#page-heading span')).first();
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

export class AttachmentUpdatePage {
  pageTitle = element(by.id('jhi-attachment-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  labelInput = element(by.id('field_label'));
  formatInput = element(by.id('field_format'));
  createdDateInput = element(by.id('field_createdDate'));
  updatedDateInput = element(by.id('field_updatedDate'));
  versionInput = element(by.id('field_version'));
  pathInput = element(by.id('field_path'));
  filenameInput = element(by.id('field_filename'));
  filesizeInput = element(by.id('field_filesize'));
  validationDateInput = element(by.id('field_validationDate'));

  engagementSelect = element(by.id('field_engagement'));
  userSelect = element(by.id('field_user'));
  updatedBySelect = element(by.id('field_updatedBy'));
  statusSelect = element(by.id('field_status'));
  originSelect = element(by.id('field_origin'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setLabelInput(label: string): Promise<void> {
    await this.labelInput.sendKeys(label);
  }

  async getLabelInput(): Promise<string> {
    return await this.labelInput.getAttribute('value');
  }

  async setFormatInput(format: string): Promise<void> {
    await this.formatInput.sendKeys(format);
  }

  async getFormatInput(): Promise<string> {
    return await this.formatInput.getAttribute('value');
  }

  async setCreatedDateInput(createdDate: string): Promise<void> {
    await this.createdDateInput.sendKeys(createdDate);
  }

  async getCreatedDateInput(): Promise<string> {
    return await this.createdDateInput.getAttribute('value');
  }

  async setUpdatedDateInput(updatedDate: string): Promise<void> {
    await this.updatedDateInput.sendKeys(updatedDate);
  }

  async getUpdatedDateInput(): Promise<string> {
    return await this.updatedDateInput.getAttribute('value');
  }

  async setVersionInput(version: string): Promise<void> {
    await this.versionInput.sendKeys(version);
  }

  async getVersionInput(): Promise<string> {
    return await this.versionInput.getAttribute('value');
  }

  async setPathInput(path: string): Promise<void> {
    await this.pathInput.sendKeys(path);
  }

  async getPathInput(): Promise<string> {
    return await this.pathInput.getAttribute('value');
  }

  async setFilenameInput(filename: string): Promise<void> {
    await this.filenameInput.sendKeys(filename);
  }

  async getFilenameInput(): Promise<string> {
    return await this.filenameInput.getAttribute('value');
  }

  async setFilesizeInput(filesize: string): Promise<void> {
    await this.filesizeInput.sendKeys(filesize);
  }

  async getFilesizeInput(): Promise<string> {
    return await this.filesizeInput.getAttribute('value');
  }

  async setValidationDateInput(validationDate: string): Promise<void> {
    await this.validationDateInput.sendKeys(validationDate);
  }

  async getValidationDateInput(): Promise<string> {
    return await this.validationDateInput.getAttribute('value');
  }

  async engagementSelectLastOption(): Promise<void> {
    await this.engagementSelect.all(by.tagName('option')).last().click();
  }

  async engagementSelectOption(option: string): Promise<void> {
    await this.engagementSelect.sendKeys(option);
  }

  getEngagementSelect(): ElementFinder {
    return this.engagementSelect;
  }

  async getEngagementSelectedOption(): Promise<string> {
    return await this.engagementSelect.element(by.css('option:checked')).getText();
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

  async updatedBySelectLastOption(): Promise<void> {
    await this.updatedBySelect.all(by.tagName('option')).last().click();
  }

  async updatedBySelectOption(option: string): Promise<void> {
    await this.updatedBySelect.sendKeys(option);
  }

  getUpdatedBySelect(): ElementFinder {
    return this.updatedBySelect;
  }

  async getUpdatedBySelectedOption(): Promise<string> {
    return await this.updatedBySelect.element(by.css('option:checked')).getText();
  }

  async statusSelectLastOption(): Promise<void> {
    await this.statusSelect.all(by.tagName('option')).last().click();
  }

  async statusSelectOption(option: string): Promise<void> {
    await this.statusSelect.sendKeys(option);
  }

  getStatusSelect(): ElementFinder {
    return this.statusSelect;
  }

  async getStatusSelectedOption(): Promise<string> {
    return await this.statusSelect.element(by.css('option:checked')).getText();
  }

  async originSelectLastOption(): Promise<void> {
    await this.originSelect.all(by.tagName('option')).last().click();
  }

  async originSelectOption(option: string): Promise<void> {
    await this.originSelect.sendKeys(option);
  }

  getOriginSelect(): ElementFinder {
    return this.originSelect;
  }

  async getOriginSelectedOption(): Promise<string> {
    return await this.originSelect.element(by.css('option:checked')).getText();
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

export class AttachmentDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-attachment-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-attachment'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
