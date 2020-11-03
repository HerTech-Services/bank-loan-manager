import { element, by, ElementFinder } from 'protractor';

export class HistoryComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-history div table .btn-danger'));
  title = element.all(by.css('jhi-history div h2#page-heading span')).first();
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

export class HistoryUpdatePage {
  pageTitle = element(by.id('jhi-history-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  tableNameInput = element(by.id('field_tableName'));
  recordIdInput = element(by.id('field_recordId'));
  eventTypeInput = element(by.id('field_eventType'));
  eventDateInput = element(by.id('field_eventDate'));
  infoInput = element(by.id('field_info'));
  hostInput = element(by.id('field_host'));
  propertiesInput = element(by.id('field_properties'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setTableNameInput(tableName: string): Promise<void> {
    await this.tableNameInput.sendKeys(tableName);
  }

  async getTableNameInput(): Promise<string> {
    return await this.tableNameInput.getAttribute('value');
  }

  async setRecordIdInput(recordId: string): Promise<void> {
    await this.recordIdInput.sendKeys(recordId);
  }

  async getRecordIdInput(): Promise<string> {
    return await this.recordIdInput.getAttribute('value');
  }

  async setEventTypeInput(eventType: string): Promise<void> {
    await this.eventTypeInput.sendKeys(eventType);
  }

  async getEventTypeInput(): Promise<string> {
    return await this.eventTypeInput.getAttribute('value');
  }

  async setEventDateInput(eventDate: string): Promise<void> {
    await this.eventDateInput.sendKeys(eventDate);
  }

  async getEventDateInput(): Promise<string> {
    return await this.eventDateInput.getAttribute('value');
  }

  async setInfoInput(info: string): Promise<void> {
    await this.infoInput.sendKeys(info);
  }

  async getInfoInput(): Promise<string> {
    return await this.infoInput.getAttribute('value');
  }

  async setHostInput(host: string): Promise<void> {
    await this.hostInput.sendKeys(host);
  }

  async getHostInput(): Promise<string> {
    return await this.hostInput.getAttribute('value');
  }

  async setPropertiesInput(properties: string): Promise<void> {
    await this.propertiesInput.sendKeys(properties);
  }

  async getPropertiesInput(): Promise<string> {
    return await this.propertiesInput.getAttribute('value');
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

export class HistoryDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-history-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-history'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
