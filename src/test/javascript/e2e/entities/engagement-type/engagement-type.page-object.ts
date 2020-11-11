import { element, by, ElementFinder } from 'protractor';

export class EngagementTypeComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-engagement-type div table .btn-danger'));
  title = element.all(by.css('jhi-engagement-type div h2#page-heading span')).first();
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

export class EngagementTypeUpdatePage {
  pageTitle = element(by.id('jhi-engagement-type-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  labelInput = element(by.id('field_label'));
  isEnabledInput = element(by.id('field_isEnabled'));
  processDelayInput = element(by.id('field_processDelay'));
  delay1Input = element(by.id('field_delay1'));
  delay2Input = element(by.id('field_delay2'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setLabelInput(label: string): Promise<void> {
    await this.labelInput.sendKeys(label);
  }

  async getLabelInput(): Promise<string> {
    return await this.labelInput.getAttribute('value');
  }

  getIsEnabledInput(): ElementFinder {
    return this.isEnabledInput;
  }

  async setProcessDelayInput(processDelay: string): Promise<void> {
    await this.processDelayInput.sendKeys(processDelay);
  }

  async getProcessDelayInput(): Promise<string> {
    return await this.processDelayInput.getAttribute('value');
  }

  async setDelay1Input(delay1: string): Promise<void> {
    await this.delay1Input.sendKeys(delay1);
  }

  async getDelay1Input(): Promise<string> {
    return await this.delay1Input.getAttribute('value');
  }

  async setDelay2Input(delay2: string): Promise<void> {
    await this.delay2Input.sendKeys(delay2);
  }

  async getDelay2Input(): Promise<string> {
    return await this.delay2Input.getAttribute('value');
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

export class EngagementTypeDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-engagementType-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-engagementType'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
