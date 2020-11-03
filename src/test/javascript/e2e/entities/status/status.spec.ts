import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { StatusComponentsPage, StatusDeleteDialog, StatusUpdatePage } from './status.page-object';

const expect = chai.expect;

describe('Status e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let statusComponentsPage: StatusComponentsPage;
  let statusUpdatePage: StatusUpdatePage;
  let statusDeleteDialog: StatusDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Statuses', async () => {
    await navBarPage.goToEntity('status');
    statusComponentsPage = new StatusComponentsPage();
    await browser.wait(ec.visibilityOf(statusComponentsPage.title), 5000);
    expect(await statusComponentsPage.getTitle()).to.eq('bankLoanManagerApp.status.home.title');
    await browser.wait(ec.or(ec.visibilityOf(statusComponentsPage.entities), ec.visibilityOf(statusComponentsPage.noResult)), 1000);
  });

  it('should load create Status page', async () => {
    await statusComponentsPage.clickOnCreateButton();
    statusUpdatePage = new StatusUpdatePage();
    expect(await statusUpdatePage.getPageTitle()).to.eq('bankLoanManagerApp.status.home.createOrEditLabel');
    await statusUpdatePage.cancel();
  });

  it('should create and save Statuses', async () => {
    const nbButtonsBeforeCreate = await statusComponentsPage.countDeleteButtons();

    await statusComponentsPage.clickOnCreateButton();

    await promise.all([statusUpdatePage.setLabelInput('label'), statusUpdatePage.setImgFilenameInput('imgFilename')]);

    expect(await statusUpdatePage.getLabelInput()).to.eq('label', 'Expected Label value to be equals to label');
    const selectedIsSystem = statusUpdatePage.getIsSystemInput();
    if (await selectedIsSystem.isSelected()) {
      await statusUpdatePage.getIsSystemInput().click();
      expect(await statusUpdatePage.getIsSystemInput().isSelected(), 'Expected isSystem not to be selected').to.be.false;
    } else {
      await statusUpdatePage.getIsSystemInput().click();
      expect(await statusUpdatePage.getIsSystemInput().isSelected(), 'Expected isSystem to be selected').to.be.true;
    }
    expect(await statusUpdatePage.getImgFilenameInput()).to.eq('imgFilename', 'Expected ImgFilename value to be equals to imgFilename');
    const selectedCanBeSearched = statusUpdatePage.getCanBeSearchedInput();
    if (await selectedCanBeSearched.isSelected()) {
      await statusUpdatePage.getCanBeSearchedInput().click();
      expect(await statusUpdatePage.getCanBeSearchedInput().isSelected(), 'Expected canBeSearched not to be selected').to.be.false;
    } else {
      await statusUpdatePage.getCanBeSearchedInput().click();
      expect(await statusUpdatePage.getCanBeSearchedInput().isSelected(), 'Expected canBeSearched to be selected').to.be.true;
    }
    const selectedCanBeModified = statusUpdatePage.getCanBeModifiedInput();
    if (await selectedCanBeModified.isSelected()) {
      await statusUpdatePage.getCanBeModifiedInput().click();
      expect(await statusUpdatePage.getCanBeModifiedInput().isSelected(), 'Expected canBeModified not to be selected').to.be.false;
    } else {
      await statusUpdatePage.getCanBeModifiedInput().click();
      expect(await statusUpdatePage.getCanBeModifiedInput().isSelected(), 'Expected canBeModified to be selected').to.be.true;
    }
    const selectedEnabled = statusUpdatePage.getEnabledInput();
    if (await selectedEnabled.isSelected()) {
      await statusUpdatePage.getEnabledInput().click();
      expect(await statusUpdatePage.getEnabledInput().isSelected(), 'Expected enabled not to be selected').to.be.false;
    } else {
      await statusUpdatePage.getEnabledInput().click();
      expect(await statusUpdatePage.getEnabledInput().isSelected(), 'Expected enabled to be selected').to.be.true;
    }

    await statusUpdatePage.save();
    expect(await statusUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await statusComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Status', async () => {
    const nbButtonsBeforeDelete = await statusComponentsPage.countDeleteButtons();
    await statusComponentsPage.clickOnLastDeleteButton();

    statusDeleteDialog = new StatusDeleteDialog();
    expect(await statusDeleteDialog.getDialogTitle()).to.eq('bankLoanManagerApp.status.delete.question');
    await statusDeleteDialog.clickOnConfirmButton();

    expect(await statusComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
