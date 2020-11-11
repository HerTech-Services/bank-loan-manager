import { element, by, ElementFinder } from 'protractor';

export class ActionComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-action div table .btn-danger'));
  title = element.all(by.css('jhi-action div h2#page-heading span')).first();
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

export class ActionUpdatePage {
  pageTitle = element(by.id('jhi-action-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  labelInput = element(by.id('field_label'));
  keywordInput = element(by.id('field_keyword'));
  isSystemInput = element(by.id('field_isSystem'));
  actionPageInput = element(by.id('field_actionPage'));
  historyInput = element(by.id('field_history'));
  composantInput = element(by.id('field_composant'));
  parametersInput = element(by.id('field_parameters'));

  statusSelect = element(by.id('field_status'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setLabelInput(label: string): Promise<void> {
    await this.labelInput.sendKeys(label);
  }

  async getLabelInput(): Promise<string> {
    return await this.labelInput.getAttribute('value');
  }

  async setKeywordInput(keyword: string): Promise<void> {
    await this.keywordInput.sendKeys(keyword);
  }

  async getKeywordInput(): Promise<string> {
    return await this.keywordInput.getAttribute('value');
  }

  getIsSystemInput(): ElementFinder {
    return this.isSystemInput;
  }

  async setActionPageInput(actionPage: string): Promise<void> {
    await this.actionPageInput.sendKeys(actionPage);
  }

  async getActionPageInput(): Promise<string> {
    return await this.actionPageInput.getAttribute('value');
  }

  getHistoryInput(): ElementFinder {
    return this.historyInput;
  }

  async setComposantInput(composant: string): Promise<void> {
    await this.composantInput.sendKeys(composant);
  }

  async getComposantInput(): Promise<string> {
    return await this.composantInput.getAttribute('value');
  }

  async setParametersInput(parameters: string): Promise<void> {
    await this.parametersInput.sendKeys(parameters);
  }

  async getParametersInput(): Promise<string> {
    return await this.parametersInput.getAttribute('value');
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

export class ActionDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-action-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-action'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
