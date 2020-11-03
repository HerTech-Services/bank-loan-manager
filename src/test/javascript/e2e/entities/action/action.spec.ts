import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ActionComponentsPage, ActionDeleteDialog, ActionUpdatePage } from './action.page-object';

const expect = chai.expect;

describe('Action e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let actionComponentsPage: ActionComponentsPage;
  let actionUpdatePage: ActionUpdatePage;
  let actionDeleteDialog: ActionDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Actions', async () => {
    await navBarPage.goToEntity('action');
    actionComponentsPage = new ActionComponentsPage();
    await browser.wait(ec.visibilityOf(actionComponentsPage.title), 5000);
    expect(await actionComponentsPage.getTitle()).to.eq('bankLoanManagerApp.action.home.title');
    await browser.wait(ec.or(ec.visibilityOf(actionComponentsPage.entities), ec.visibilityOf(actionComponentsPage.noResult)), 1000);
  });

  it('should load create Action page', async () => {
    await actionComponentsPage.clickOnCreateButton();
    actionUpdatePage = new ActionUpdatePage();
    expect(await actionUpdatePage.getPageTitle()).to.eq('bankLoanManagerApp.action.home.createOrEditLabel');
    await actionUpdatePage.cancel();
  });

  it('should create and save Actions', async () => {
    const nbButtonsBeforeCreate = await actionComponentsPage.countDeleteButtons();

    await actionComponentsPage.clickOnCreateButton();

    await promise.all([
      actionUpdatePage.setLabelInput('label'),
      actionUpdatePage.setKeywordInput('keyword'),
      actionUpdatePage.setActionPageInput('actionPage'),
      actionUpdatePage.setComposantInput('composant'),
      actionUpdatePage.setParametersInput('parameters'),
      actionUpdatePage.statusSelectLastOption(),
    ]);

    expect(await actionUpdatePage.getLabelInput()).to.eq('label', 'Expected Label value to be equals to label');
    expect(await actionUpdatePage.getKeywordInput()).to.eq('keyword', 'Expected Keyword value to be equals to keyword');
    const selectedIsSystem = actionUpdatePage.getIsSystemInput();
    if (await selectedIsSystem.isSelected()) {
      await actionUpdatePage.getIsSystemInput().click();
      expect(await actionUpdatePage.getIsSystemInput().isSelected(), 'Expected isSystem not to be selected').to.be.false;
    } else {
      await actionUpdatePage.getIsSystemInput().click();
      expect(await actionUpdatePage.getIsSystemInput().isSelected(), 'Expected isSystem to be selected').to.be.true;
    }
    expect(await actionUpdatePage.getActionPageInput()).to.eq('actionPage', 'Expected ActionPage value to be equals to actionPage');
    const selectedHistory = actionUpdatePage.getHistoryInput();
    if (await selectedHistory.isSelected()) {
      await actionUpdatePage.getHistoryInput().click();
      expect(await actionUpdatePage.getHistoryInput().isSelected(), 'Expected history not to be selected').to.be.false;
    } else {
      await actionUpdatePage.getHistoryInput().click();
      expect(await actionUpdatePage.getHistoryInput().isSelected(), 'Expected history to be selected').to.be.true;
    }
    expect(await actionUpdatePage.getComposantInput()).to.eq('composant', 'Expected Composant value to be equals to composant');
    expect(await actionUpdatePage.getParametersInput()).to.eq('parameters', 'Expected Parameters value to be equals to parameters');

    await actionUpdatePage.save();
    expect(await actionUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await actionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Action', async () => {
    const nbButtonsBeforeDelete = await actionComponentsPage.countDeleteButtons();
    await actionComponentsPage.clickOnLastDeleteButton();

    actionDeleteDialog = new ActionDeleteDialog();
    expect(await actionDeleteDialog.getDialogTitle()).to.eq('bankLoanManagerApp.action.delete.question');
    await actionDeleteDialog.clickOnConfirmButton();

    expect(await actionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
