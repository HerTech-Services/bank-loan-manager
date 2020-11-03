import { element, by, ElementFinder } from 'protractor';

export class PosteComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-poste div table .btn-danger'));
  title = element.all(by.css('jhi-poste div h2#page-heading span')).first();
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

export class PosteUpdatePage {
  pageTitle = element(by.id('jhi-poste-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  codeInput = element(by.id('field_code'));
  labelInput = element(by.id('field_label'));
  shortLabelInput = element(by.id('field_shortLabel'));
  entityInput = element(by.id('field_entity'));
  enabledInput = element(by.id('field_enabled'));
  adrs1Input = element(by.id('field_adrs1'));
  adrs2Input = element(by.id('field_adrs2'));
  adrs3Input = element(by.id('field_adrs3'));
  cityInput = element(by.id('field_city'));

  parentPosteSelect = element(by.id('field_parentPoste'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setCodeInput(code: string): Promise<void> {
    await this.codeInput.sendKeys(code);
  }

  async getCodeInput(): Promise<string> {
    return await this.codeInput.getAttribute('value');
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

  async setEntityInput(entity: string): Promise<void> {
    await this.entityInput.sendKeys(entity);
  }

  async getEntityInput(): Promise<string> {
    return await this.entityInput.getAttribute('value');
  }

  getEnabledInput(): ElementFinder {
    return this.enabledInput;
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

  async setCityInput(city: string): Promise<void> {
    await this.cityInput.sendKeys(city);
  }

  async getCityInput(): Promise<string> {
    return await this.cityInput.getAttribute('value');
  }

  async parentPosteSelectLastOption(): Promise<void> {
    await this.parentPosteSelect.all(by.tagName('option')).last().click();
  }

  async parentPosteSelectOption(option: string): Promise<void> {
    await this.parentPosteSelect.sendKeys(option);
  }

  getParentPosteSelect(): ElementFinder {
    return this.parentPosteSelect;
  }

  async getParentPosteSelectedOption(): Promise<string> {
    return await this.parentPosteSelect.element(by.css('option:checked')).getText();
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

export class PosteDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-poste-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-poste'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
