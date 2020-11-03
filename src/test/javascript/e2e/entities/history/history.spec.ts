import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { HistoryComponentsPage, HistoryDeleteDialog, HistoryUpdatePage } from './history.page-object';

const expect = chai.expect;

describe('History e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let historyComponentsPage: HistoryComponentsPage;
  let historyUpdatePage: HistoryUpdatePage;
  let historyDeleteDialog: HistoryDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Histories', async () => {
    await navBarPage.goToEntity('history');
    historyComponentsPage = new HistoryComponentsPage();
    await browser.wait(ec.visibilityOf(historyComponentsPage.title), 5000);
    expect(await historyComponentsPage.getTitle()).to.eq('bankLoanManagerApp.history.home.title');
    await browser.wait(ec.or(ec.visibilityOf(historyComponentsPage.entities), ec.visibilityOf(historyComponentsPage.noResult)), 1000);
  });

  it('should load create History page', async () => {
    await historyComponentsPage.clickOnCreateButton();
    historyUpdatePage = new HistoryUpdatePage();
    expect(await historyUpdatePage.getPageTitle()).to.eq('bankLoanManagerApp.history.home.createOrEditLabel');
    await historyUpdatePage.cancel();
  });

  it('should create and save Histories', async () => {
    const nbButtonsBeforeCreate = await historyComponentsPage.countDeleteButtons();

    await historyComponentsPage.clickOnCreateButton();

    await promise.all([
      historyUpdatePage.setTableNameInput('tableName'),
      historyUpdatePage.setRecordIdInput('recordId'),
      historyUpdatePage.setEventTypeInput('eventType'),
      historyUpdatePage.setEventDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      historyUpdatePage.setInfoInput('info'),
      historyUpdatePage.setHostInput('host'),
      historyUpdatePage.setPropertiesInput('properties'),
    ]);

    expect(await historyUpdatePage.getTableNameInput()).to.eq('tableName', 'Expected TableName value to be equals to tableName');
    expect(await historyUpdatePage.getRecordIdInput()).to.eq('recordId', 'Expected RecordId value to be equals to recordId');
    expect(await historyUpdatePage.getEventTypeInput()).to.eq('eventType', 'Expected EventType value to be equals to eventType');
    expect(await historyUpdatePage.getEventDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected eventDate value to be equals to 2000-12-31'
    );
    expect(await historyUpdatePage.getInfoInput()).to.eq('info', 'Expected Info value to be equals to info');
    expect(await historyUpdatePage.getHostInput()).to.eq('host', 'Expected Host value to be equals to host');
    expect(await historyUpdatePage.getPropertiesInput()).to.eq('properties', 'Expected Properties value to be equals to properties');

    await historyUpdatePage.save();
    expect(await historyUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await historyComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last History', async () => {
    const nbButtonsBeforeDelete = await historyComponentsPage.countDeleteButtons();
    await historyComponentsPage.clickOnLastDeleteButton();

    historyDeleteDialog = new HistoryDeleteDialog();
    expect(await historyDeleteDialog.getDialogTitle()).to.eq('bankLoanManagerApp.history.delete.question');
    await historyDeleteDialog.clickOnConfirmButton();

    expect(await historyComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
