import { element, by, ElementFinder } from 'protractor';

export class EntiteComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-entite div table .btn-danger'));
  title = element.all(by.css('jhi-entite div h2#page-heading span')).first();
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

export class EntiteUpdatePage {
  pageTitle = element(by.id('jhi-entite-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  identifierInput = element(by.id('field_identifier'));
  labelInput = element(by.id('field_label'));
  shortLabelInput = element(by.id('field_shortLabel'));
  parentInput = element(by.id('field_parent'));
  isEnabledInput = element(by.id('field_isEnabled'));
  adrs1Input = element(by.id('field_adrs1'));
  adrs2Input = element(by.id('field_adrs2'));
  adrs3Input = element(by.id('field_adrs3'));
  zipcodeInput = element(by.id('field_zipcode'));
  cityInput = element(by.id('field_city'));
  countryInput = element(by.id('field_country'));
  emailInput = element(by.id('field_email'));
  parametersInput = element(by.id('field_parameters'));
  parentIdInput = element(by.id('field_parentId'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setIdentifierInput(identifier: string): Promise<void> {
    await this.identifierInput.sendKeys(identifier);
  }

  async getIdentifierInput(): Promise<string> {
    return await this.identifierInput.getAttribute('value');
  }

  async setLabelInput(label: string): Promise<void> {
    await this.labelInput.sendKeys(label);
  }

  async getLabelInput(): Promise<string> {
    return await this.labelInput.getAttribute('value');
  }

  async setShortLabelInput(shortLabel: string): Promise<void> {
    await this.shortLabelInput.sendKeys(shortLabel);
  }

  async getShortLabelInput(): Promise<string> {
    return await this.shortLabelInput.getAttribute('value');
  }

  async setParentInput(parent: string): Promise<void> {
    await this.parentInput.sendKeys(parent);
  }

  async getParentInput(): Promise<string> {
    return await this.parentInput.getAttribute('value');
  }

  getIsEnabledInput(): ElementFinder {
    return this.isEnabledInput;
  }

  async setAdrs1Input(adrs1: string): Promise<void> {
    await this.adrs1Input.sendKeys(adrs1);
  }

  async getAdrs1Input(): Promise<string> {
    return await this.adrs1Input.getAttribute('value');
  }

  async setAdrs2Input(adrs2: string): Promise<void> {
    await this.adrs2Input.sendKeys(adrs2);
  }

  async getAdrs2Input(): Promise<string> {
    return await this.adrs2Input.getAttribute('value');
  }

  async setAdrs3Input(adrs3: string): Promise<void> {
    await this.adrs3Input.sendKeys(adrs3);
  }

  async getAdrs3Input(): Promise<string> {
    return await this.adrs3Input.getAttribute('value');
  }

  async setZipcodeInput(zipcode: string): Promise<void> {
    await this.zipcodeInput.sendKeys(zipcode);
  }

  async getZipcodeInput(): Promise<string> {
    return await this.zipcodeInput.getAttribute('value');
  }

  async setCityInput(city: string): Promise<void> {
    await this.cityInput.sendKeys(city);
  }

  async getCityInput(): Promise<string> {
    return await this.cityInput.getAttribute('value');
  }

  async setCountryInput(country: string): Promise<void> {
    await this.countryInput.sendKeys(country);
  }

  async getCountryInput(): Promise<string> {
    return await this.countryInput.getAttribute('value');
  }

  async setEmailInput(email: string): Promise<void> {
    await this.emailInput.sendKeys(email);
  }

  async getEmailInput(): Promise<string> {
    return await this.emailInput.getAttribute('value');
  }

  async setParametersInput(parameters: string): Promise<void> {
    await this.parametersInput.sendKeys(parameters);
  }

  async getParametersInput(): Promise<string> {
    return await this.parametersInput.getAttribute('value');
  }

  async setParentIdInput(parentId: string): Promise<void> {
    await this.parentIdInput.sendKeys(parentId);
  }

  async getParentIdInput(): Promise<string> {
    return await this.parentIdInput.getAttribute('value');
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

export class EntiteDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-entite-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-entite'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
