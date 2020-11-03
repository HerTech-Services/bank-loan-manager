import { element, by, ElementFinder } from 'protractor';

export class DowngradingStepComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-downgrading-step div table .btn-danger'));
  title = element.all(by.css('jhi-downgrading-step div h2#page-heading span')).first();
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

export class DowngradingStepUpdatePage {
  pageTitle = element(by.id('jhi-downgrading-step-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  labelInput = element(by.id('field_label'));
  identifierInput = element(by.id('field_identifier'));

  scoringSelect = element(by.id('field_scoring'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setLabelInput(label: string): Promise<void> {
    await this.labelInput.sendKeys(label);
  }

  async getLabelInput(): Promise<string> {
    return await this.labelInput.getAttribute('value');
  }

  async setIdentifierInput(identifier: string): Promise<void> {
    await this.identifierInput.sendKeys(identifier);
  }

  async getIdentifierInput(): Promise<string> {
    return await this.identifierInput.getAttribute('value');
  }

  async scoringSelectLastOption(): Promise<void> {
    await this.scoringSelect.all(by.tagName('option')).last().click();
  }

  async scoringSelectOption(option: string): Promise<void> {
    await this.scoringSelect.sendKeys(option);
  }

  getScoringSelect(): ElementFinder {
    return this.scoringSelect;
  }

  async getScoringSelectedOption(): Promise<string> {
    return await this.scoringSelect.element(by.css('option:checked')).getText();
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

export class DowngradingStepDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-downgradingStep-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-downgradingStep'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
