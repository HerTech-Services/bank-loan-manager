import { element, by, ElementFinder } from 'protractor';

export class TasktypeStatusActionComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-tasktype-status-action div table .btn-danger'));
  title = element.all(by.css('jhi-tasktype-status-action div h2#page-heading span')).first();
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

export class TasktypeStatusActionUpdatePage {
  pageTitle = element(by.id('jhi-tasktype-status-action-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  tasktypeSelect = element(by.id('field_tasktype'));
  actionSelect = element(by.id('field_action'));
  statusSelect = element(by.id('field_status'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async tasktypeSelectLastOption(): Promise<void> {
    await this.tasktypeSelect.all(by.tagName('option')).last().click();
  }

  async tasktypeSelectOption(option: string): Promise<void> {
    await this.tasktypeSelect.sendKeys(option);
  }

  getTasktypeSelect(): ElementFinder {
    return this.tasktypeSelect;
  }

  async getTasktypeSelectedOption(): Promise<string> {
    return await this.tasktypeSelect.element(by.css('option:checked')).getText();
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

export class TasktypeStatusActionDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-tasktypeStatusAction-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-tasktypeStatusAction'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
