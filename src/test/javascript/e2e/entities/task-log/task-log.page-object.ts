import { element, by, ElementFinder } from 'protractor';

export class TaskLogComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-task-log div table .btn-danger'));
  title = element.all(by.css('jhi-task-log div h2#page-heading span')).first();
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

export class TaskLogUpdatePage {
  pageTitle = element(by.id('jhi-task-log-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  commentInput = element(by.id('field_comment'));
  createdDateInput = element(by.id('field_createdDate'));
  taskPropertiesInput = element(by.id('field_taskProperties'));
  engagementPropertiesInput = element(by.id('field_engagementProperties'));

  userSelect = element(by.id('field_user'));
  actionSelect = element(by.id('field_action'));
  taskSelect = element(by.id('field_task'));
  engagementSelect = element(by.id('field_engagement'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setCommentInput(comment: string): Promise<void> {
    await this.commentInput.sendKeys(comment);
  }

  async getCommentInput(): Promise<string> {
    return await this.commentInput.getAttribute('value');
  }

  async setCreatedDateInput(createdDate: string): Promise<void> {
    await this.createdDateInput.sendKeys(createdDate);
  }

  async getCreatedDateInput(): Promise<string> {
    return await this.createdDateInput.getAttribute('value');
  }

  async setTaskPropertiesInput(taskProperties: string): Promise<void> {
    await this.taskPropertiesInput.sendKeys(taskProperties);
  }

  async getTaskPropertiesInput(): Promise<string> {
    return await this.taskPropertiesInput.getAttribute('value');
  }

  async setEngagementPropertiesInput(engagementProperties: string): Promise<void> {
    await this.engagementPropertiesInput.sendKeys(engagementProperties);
  }

  async getEngagementPropertiesInput(): Promise<string> {
    return await this.engagementPropertiesInput.getAttribute('value');
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

  async actionSelectLastOption(): Promise<void> {
    await this.actionSelect.all(by.tagName('option')).last().click();
  }

  async actionSelectOption(option: string): Promise<void> {
    await this.actionSelect.sendKeys(option);
  }

  getActionSelect(): ElementFinder {
    return this.actionSelect;
  }

  async getActionSelectedOption(): Promise<string> {
    return await this.actionSelect.element(by.css('option:checked')).getText();
  }

  async taskSelectLastOption(): Promise<void> {
    await this.taskSelect.all(by.tagName('option')).last().click();
  }

  async taskSelectOption(option: string): Promise<void> {
    await this.taskSelect.sendKeys(option);
  }

  getTaskSelect(): ElementFinder {
    return this.taskSelect;
  }

  async getTaskSelectedOption(): Promise<string> {
    return await this.taskSelect.element(by.css('option:checked')).getText();
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

export class TaskLogDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-taskLog-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-taskLog'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
