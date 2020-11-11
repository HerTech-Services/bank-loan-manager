import { element, by, ElementFinder } from 'protractor';

export class RejectComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-reject div table .btn-danger'));
  title = element.all(by.css('jhi-reject div h2#page-heading span')).first();
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

export class RejectUpdatePage {
  pageTitle = element(by.id('jhi-reject-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  createdDateInput = element(by.id('field_createdDate'));
  capitalInput = element(by.id('field_capital'));
  interestsInput = element(by.id('field_interests'));
  penaltiesInput = element(by.id('field_penalties'));
  accessoriesInput = element(by.id('field_accessories'));
  descriptionInput = element(by.id('field_description'));

  typeSelect = element(by.id('field_type'));
  engagementSelect = element(by.id('field_engagement'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setCreatedDateInput(createdDate: string): Promise<void> {
    await this.createdDateInput.sendKeys(createdDate);
  }

  async getCreatedDateInput(): Promise<string> {
    return await this.createdDateInput.getAttribute('value');
  }

  async setCapitalInput(capital: string): Promise<void> {
    await this.capitalInput.sendKeys(capital);
  }

  async getCapitalInput(): Promise<string> {
    return await this.capitalInput.getAttribute('value');
  }

  async setInterestsInput(interests: string): Promise<void> {
    await this.interestsInput.sendKeys(interests);
  }

  async getInterestsInput(): Promise<string> {
    return await this.interestsInput.getAttribute('value');
  }

  async setPenaltiesInput(penalties: string): Promise<void> {
    await this.penaltiesInput.sendKeys(penalties);
  }

  async getPenaltiesInput(): Promise<string> {
    return await this.penaltiesInput.getAttribute('value');
  }

  async setAccessoriesInput(accessories: string): Promise<void> {
    await this.accessoriesInput.sendKeys(accessories);
  }

  async getAccessoriesInput(): Promise<string> {
    return await this.accessoriesInput.getAttribute('value');
  }

  async setDescriptionInput(description: string): Promise<void> {
    await this.descriptionInput.sendKeys(description);
  }

  async getDescriptionInput(): Promise<string> {
    return await this.descriptionInput.getAttribute('value');
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

export class RejectDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-reject-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-reject'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
