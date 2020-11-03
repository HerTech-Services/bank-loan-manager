import { element, by, ElementFinder } from 'protractor';

export class ParametersComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-parameters div table .btn-danger'));
  title = element.all(by.css('jhi-parameters div h2#page-heading span')).first();
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

export class ParametersUpdatePage {
  pageTitle = element(by.id('jhi-parameters-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  identifierInput = element(by.id('field_identifier'));
  descriptionInput = element(by.id('field_description'));
  paramValueStringInput = element(by.id('field_paramValueString'));
  paramValueIntInput = element(by.id('field_paramValueInt'));
  paramValueDateInput = element(by.id('field_paramValueDate'));
  updatedDateInput = element(by.id('field_updatedDate'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setIdentifierInput(identifier: string): Promise<void> {
    await this.identifierInput.sendKeys(identifier);
  }

  async getIdentifierInput(): Promise<string> {
    return await this.identifierInput.getAttribute('value');
  }

  async setDescriptionInput(description: string): Promise<void> {
    await this.descriptionInput.sendKeys(description);
  }

  async getDescriptionInput(): Promise<string> {
    return await this.descriptionInput.getAttribute('value');
  }

  async setParamValueStringInput(paramValueString: string): Promise<void> {
    await this.paramValueStringInput.sendKeys(paramValueString);
  }

  async getParamValueStringInput(): Promise<string> {
    return await this.paramValueStringInput.getAttribute('value');
  }

  async setParamValueIntInput(paramValueInt: string): Promise<void> {
    await this.paramValueIntInput.sendKeys(paramValueInt);
  }

  async getParamValueIntInput(): Promise<string> {
    return await this.paramValueIntInput.getAttribute('value');
  }

  async setParamValueDateInput(paramValueDate: string): Promise<void> {
    await this.paramValueDateInput.sendKeys(paramValueDate);
  }

  async getParamValueDateInput(): Promise<string> {
    return await this.paramValueDateInput.getAttribute('value');
  }

  async setUpdatedDateInput(updatedDate: string): Promise<void> {
    await this.updatedDateInput.sendKeys(updatedDate);
  }

  async getUpdatedDateInput(): Promise<string> {
    return await this.updatedDateInput.getAttribute('value');
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

export class ParametersDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-parameters-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-parameters'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
