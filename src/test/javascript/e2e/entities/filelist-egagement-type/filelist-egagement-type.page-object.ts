import { element, by, ElementFinder } from 'protractor';

export class FilelistEgagementTypeComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-filelist-egagement-type div table .btn-danger'));
  title = element.all(by.css('jhi-filelist-egagement-type div h2#page-heading span')).first();
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

export class FilelistEgagementTypeUpdatePage {
  pageTitle = element(by.id('jhi-filelist-egagement-type-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  mandatoryInput = element(by.id('field_mandatory'));

  engagementTypeSelect = element(by.id('field_engagementType'));
  filelistSelect = element(by.id('field_filelist'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  getMandatoryInput(): ElementFinder {
    return this.mandatoryInput;
  }

  async engagementTypeSelectLastOption(): Promise<void> {
    await this.engagementTypeSelect.all(by.tagName('option')).last().click();
  }

  async engagementTypeSelectOption(option: string): Promise<void> {
    await this.engagementTypeSelect.sendKeys(option);
  }

  getEngagementTypeSelect(): ElementFinder {
    return this.engagementTypeSelect;
  }

  async getEngagementTypeSelectedOption(): Promise<string> {
    return await this.engagementTypeSelect.element(by.css('option:checked')).getText();
  }

  async filelistSelectLastOption(): Promise<void> {
    await this.filelistSelect.all(by.tagName('option')).last().click();
  }

  async filelistSelectOption(option: string): Promise<void> {
    await this.filelistSelect.sendKeys(option);
  }

  getFilelistSelect(): ElementFinder {
    return this.filelistSelect;
  }

  async getFilelistSelectedOption(): Promise<string> {
    return await this.filelistSelect.element(by.css('option:checked')).getText();
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

export class FilelistEgagementTypeDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-filelistEgagementType-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-filelistEgagementType'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
