import { element, by, ElementFinder } from 'protractor';

export class TaskComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-task div table .btn-danger'));
  title = element.all(by.css('jhi-task div h2#page-heading span')).first();
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

export class TaskUpdatePage {
  pageTitle = element(by.id('jhi-task-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  sequenceInput = element(by.id('field_sequence'));
  isSystemInput = element(by.id('field_isSystem'));
  processDelayInput = element(by.id('field_processDelay'));
  delay1Input = element(by.id('field_delay1'));
  delay2Input = element(by.id('field_delay2'));
  viewedInput = element(by.id('field_viewed'));
  createdDateInput = element(by.id('field_createdDate'));
  processDateInput = element(by.id('field_processDate'));
  processCommentInput = element(by.id('field_processComment'));

  typeSelect = element(by.id('field_type'));
  processUserSelect = element(by.id('field_processUser'));
  posteSelect = element(by.id('field_poste'));
  engagementSelect = element(by.id('field_engagement'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setSequenceInput(sequence: string): Promise<void> {
    await this.sequenceInput.sendKeys(sequence);
  }

  async getSequenceInput(): Promise<string> {
    return await this.sequenceInput.getAttribute('value');
  }

  getIsSystemInput(): ElementFinder {
    return this.isSystemInput;
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

  async setViewedInput(viewed: string): Promise<void> {
    await this.viewedInput.sendKeys(viewed);
  }

  async getViewedInput(): Promise<string> {
    return await this.viewedInput.getAttribute('value');
  }

  async setCreatedDateInput(createdDate: string): Promise<void> {
    await this.createdDateInput.sendKeys(createdDate);
  }

  async getCreatedDateInput(): Promise<string> {
    return await this.createdDateInput.getAttribute('value');
  }

  async setProcessDateInput(processDate: string): Promise<void> {
    await this.processDateInput.sendKeys(processDate);
  }

  async getProcessDateInput(): Promise<string> {
    return await this.processDateInput.getAttribute('value');
  }

  async setProcessCommentInput(processComment: string): Promise<void> {
    await this.processCommentInput.sendKeys(processComment);
  }

  async getProcessCommentInput(): Promise<string> {
    return await this.processCommentInput.getAttribute('value');
  }

  async typeSelectLastOption(): Promise<void> {
    await this.typeSelect.all(by.tagName('option')).last().click();
  }

  async typeSelectOption(option: string): Promise<void> {
    await this.typeSelect.sendKeys(option);
  }

  getTypeSelect(): ElementFinder {
    return this.typeSelect;
  }

  async getTypeSelectedOption(): Promise<string> {
    return await this.typeSelect.element(by.css('option:checked')).getText();
  }

  async processUserSelectLastOption(): Promise<void> {
    await this.processUserSelect.all(by.tagName('option')).last().click();
  }

  async processUserSelectOption(option: string): Promise<void> {
    await this.processUserSelect.sendKeys(option);
  }

  getProcessUserSelect(): ElementFinder {
    return this.processUserSelect;
  }

  async getProcessUserSelectedOption(): Promise<string> {
    return await this.processUserSelect.element(by.css('option:checked')).getText();
  }

  async posteSelectLastOption(): Promise<void> {
    await this.posteSelect.all(by.tagName('option')).last().click();
  }

  async posteSelectOption(option: string): Promise<void> {
    await this.posteSelect.sendKeys(option);
  }

  getPosteSelect(): ElementFinder {
    return this.posteSelect;
  }

  async getPosteSelectedOption(): Promise<string> {
    return await this.posteSelect.element(by.css('option:checked')).getText();
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

export class TaskDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-task-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-task'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
