import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { FilelistComponentsPage, FilelistDeleteDialog, FilelistUpdatePage } from './filelist.page-object';

const expect = chai.expect;

describe('Filelist e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let filelistComponentsPage: FilelistComponentsPage;
  let filelistUpdatePage: FilelistUpdatePage;
  let filelistDeleteDialog: FilelistDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Filelists', async () => {
    await navBarPage.goToEntity('filelist');
    filelistComponentsPage = new FilelistComponentsPage();
    await browser.wait(ec.visibilityOf(filelistComponentsPage.title), 5000);
    expect(await filelistComponentsPage.getTitle()).to.eq('bankLoanManagerApp.filelist.home.title');
    await browser.wait(ec.or(ec.visibilityOf(filelistComponentsPage.entities), ec.visibilityOf(filelistComponentsPage.noResult)), 1000);
  });

  it('should load create Filelist page', async () => {
    await filelistComponentsPage.clickOnCreateButton();
    filelistUpdatePage = new FilelistUpdatePage();
    expect(await filelistUpdatePage.getPageTitle()).to.eq('bankLoanManagerApp.filelist.home.createOrEditLabel');
    await filelistUpdatePage.cancel();
  });

  it('should create and save Filelists', async () => {
    const nbButtonsBeforeCreate = await filelistComponentsPage.countDeleteButtons();

    await filelistComponentsPage.clickOnCreateButton();

    await promise.all([
      filelistUpdatePage.setLabelInput('label'),
      filelistUpdatePage.setIdentifierInput('identifier'),
      filelistUpdatePage.setDescriptionInput('description'),
    ]);

    expect(await filelistUpdatePage.getLabelInput()).to.eq('label', 'Expected Label value to be equals to label');
    expect(await filelistUpdatePage.getIdentifierInput()).to.eq('identifier', 'Expected Identifier value to be equals to identifier');
    expect(await filelistUpdatePage.getDescriptionInput()).to.eq('description', 'Expected Description value to be equals to description');

    await filelistUpdatePage.save();
    expect(await filelistUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await filelistComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Filelist', async () => {
    const nbButtonsBeforeDelete = await filelistComponentsPage.countDeleteButtons();
    await filelistComponentsPage.clickOnLastDeleteButton();

    filelistDeleteDialog = new FilelistDeleteDialog();
    expect(await filelistDeleteDialog.getDialogTitle()).to.eq('bankLoanManagerApp.filelist.delete.question');
    await filelistDeleteDialog.clickOnConfirmButton();

    expect(await filelistComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
