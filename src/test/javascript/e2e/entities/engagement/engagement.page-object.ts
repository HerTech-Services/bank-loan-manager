import { element, by, ElementFinder } from 'protractor';

export class EngagementComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-engagement div table .btn-danger'));
  title = element.all(by.css('jhi-engagement div h2#page-heading span')).first();
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

export class EngagementUpdatePage {
  pageTitle = element(by.id('jhi-engagement-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  identifierInput = element(by.id('field_identifier'));
  scoringInput = element(by.id('field_scoring'));
  subjectInput = element(by.id('field_subject'));
  amountInput = element(by.id('field_amount'));
  delayInput = element(by.id('field_delay'));
  paymentSelect = element(by.id('field_payment'));
  delayedInput = element(by.id('field_delayed'));
  reimbursementSelect = element(by.id('field_reimbursement'));
  analyseInput = element(by.id('field_analyse'));
  isStopInput = element(by.id('field_isStop'));
  isDeletedInput = element(by.id('field_isDeleted'));
  startDateInput = element(by.id('field_startDate'));
  enDateInput = element(by.id('field_enDate'));
  createdDateInput = element(by.id('field_createdDate'));
  updatedDateInput = element(by.id('field_updatedDate'));
  deletedDateInput = element(by.id('field_deletedDate'));
  stopedDateInput = element(by.id('field_stopedDate'));
  submissionDateInput = element(by.id('field_submissionDate'));

  currentTaskSelect = element(by.id('field_currentTask'));
  typeSelect = element(by.id('field_type'));
  statusSelect = element(by.id('field_status'));
  decisionSelect = element(by.id('field_decision'));
  createdBySelect = element(by.id('field_createdBy'));
  clientSelect = element(by.id('field_client'));
  compteSelect = element(by.id('field_compte'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setIdentifierInput(identifier: string): Promise<void> {
    await this.identifierInput.sendKeys(identifier);
  }

  async getIdentifierInput(): Promise<string> {
    return await this.identifierInput.getAttribute('value');
  }

  async setScoringInput(scoring: string): Promise<void> {
    await this.scoringInput.sendKeys(scoring);
  }

  async getScoringInput(): Promise<string> {
    return await this.scoringInput.getAttribute('value');
  }

  async setSubjectInput(subject: string): Promise<void> {
    await this.subjectInput.sendKeys(subject);
  }

  async getSubjectInput(): Promise<string> {
    return await this.subjectInput.getAttribute('value');
  }

  async setAmountInput(amount: string): Promise<void> {
    await this.amountInput.sendKeys(amount);
  }

  async getAmountInput(): Promise<string> {
    return await this.amountInput.getAttribute('value');
  }

  async setDelayInput(delay: string): Promise<void> {
    await this.delayInput.sendKeys(delay);
  }

  async getDelayInput(): Promise<string> {
    return await this.delayInput.getAttribute('value');
  }

  async setPaymentSelect(payment: string): Promise<void> {
    await this.paymentSelect.sendKeys(payment);
  }

  async getPaymentSelect(): Promise<string> {
    return await this.paymentSelect.element(by.css('option:checked')).getText();
  }

  async paymentSelectLastOption(): Promise<void> {
    await this.paymentSelect.all(by.tagName('option')).last().click();
  }

  async setDelayedInput(delayed: string): Promise<void> {
    await this.delayedInput.sendKeys(delayed);
  }

  async getDelayedInput(): Promise<string> {
    return await this.delayedInput.getAttribute('value');
  }

  async setReimbursementSelect(reimbursement: string): Promise<void> {
    await this.reimbursementSelect.sendKeys(reimbursement);
  }

  async getReimbursementSelect(): Promise<string> {
    return await this.reimbursementSelect.element(by.css('option:checked')).getText();
  }

  async reimbursementSelectLastOption(): Promise<void> {
    await this.reimbursementSelect.all(by.tagName('option')).last().click();
  }

  async setAnalyseInput(analyse: string): Promise<void> {
    await this.analyseInput.sendKeys(analyse);
  }

  async getAnalyseInput(): Promise<string> {
    return await this.analyseInput.getAttribute('value');
  }

  getIsStopInput(): ElementFinder {
    return this.isStopInput;
  }

  getIsDeletedInput(): ElementFinder {
    return this.isDeletedInput;
  }

  async setStartDateInput(startDate: string): Promise<void> {
    await this.startDateInput.sendKeys(startDate);
  }

  async getStartDateInput(): Promise<string> {
    return await this.startDateInput.getAttribute('value');
  }

  async setEnDateInput(enDate: string): Promise<void> {
    await this.enDateInput.sendKeys(enDate);
  }

  async getEnDateInput(): Promise<string> {
    return await this.enDateInput.getAttribute('value');
  }

  async setCreatedDateInput(createdDate: string): Promise<void> {
    await this.createdDateInput.sendKeys(createdDate);
  }

  async getCreatedDateInput(): Promise<string> {
    return await this.createdDateInput.getAttribute('value');
  }

  async setUpdatedDateInput(updatedDate: string): Promise<void> {
    await this.updatedDateInput.sendKeys(updatedDate);
  }

  async getUpdatedDateInput(): Promise<string> {
    return await this.updatedDateInput.getAttribute('value');
  }

  async setDeletedDateInput(deletedDate: string): Promise<void> {
    await this.deletedDateInput.sendKeys(deletedDate);
  }

  async getDeletedDateInput(): Promise<string> {
    return await this.deletedDateInput.getAttribute('value');
  }

  async setStopedDateInput(stopedDate: string): Promise<void> {
    await this.stopedDateInput.sendKeys(stopedDate);
  }

  async getStopedDateInput(): Promise<string> {
    return await this.stopedDateInput.getAttribute('value');
  }

  async setSubmissionDateInput(submissionDate: string): Promise<void> {
    await this.submissionDateInput.sendKeys(submissionDate);
  }

  async getSubmissionDateInput(): Promise<string> {
    return await this.submissionDateInput.getAttribute('value');
  }

  async currentTaskSelectLastOption(): Promise<void> {
    await this.currentTaskSelect.all(by.tagName('option')).last().click();
  }

  async currentTaskSelectOption(option: string): Promise<void> {
    await this.currentTaskSelect.sendKeys(option);
  }

  getCurrentTaskSelect(): ElementFinder {
    return this.currentTaskSelect;
  }

  async getCurrentTaskSelectedOption(): Promise<string> {
    return await this.currentTaskSelect.element(by.css('option:checked')).getText();
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

  async decisionSelectLastOption(): Promise<void> {
    await this.decisionSelect.all(by.tagName('option')).last().click();
  }

  async decisionSelectOption(option: string): Promise<void> {
    await this.decisionSelect.sendKeys(option);
  }

  getDecisionSelect(): ElementFinder {
    return this.decisionSelect;
  }

  async getDecisionSelectedOption(): Promise<string> {
    return await this.decisionSelect.element(by.css('option:checked')).getText();
  }

  async createdBySelectLastOption(): Promise<void> {
    await this.createdBySelect.all(by.tagName('option')).last().click();
  }

  async createdBySelectOption(option: string): Promise<void> {
    await this.createdBySelect.sendKeys(option);
  }

  getCreatedBySelect(): ElementFinder {
    return this.createdBySelect;
  }

  async getCreatedBySelectedOption(): Promise<string> {
    return await this.createdBySelect.element(by.css('option:checked')).getText();
  }

  async clientSelectLastOption(): Promise<void> {
    await this.clientSelect.all(by.tagName('option')).last().click();
  }

  async clientSelectOption(option: string): Promise<void> {
    await this.clientSelect.sendKeys(option);
  }

  getClientSelect(): ElementFinder {
    return this.clientSelect;
  }

  async getClientSelectedOption(): Promise<string> {
    return await this.clientSelect.element(by.css('option:checked')).getText();
  }

  async compteSelectLastOption(): Promise<void> {
    await this.compteSelect.all(by.tagName('option')).last().click();
  }

  async compteSelectOption(option: string): Promise<void> {
    await this.compteSelect.sendKeys(option);
  }

  getCompteSelect(): ElementFinder {
    return this.compteSelect;
  }

  async getCompteSelectedOption(): Promise<string> {
    return await this.compteSelect.element(by.css('option:checked')).getText();
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

export class EngagementDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-engagement-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-engagement'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
