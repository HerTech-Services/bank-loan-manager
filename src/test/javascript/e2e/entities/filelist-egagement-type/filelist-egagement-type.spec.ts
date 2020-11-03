import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  FilelistEgagementTypeComponentsPage,
  FilelistEgagementTypeDeleteDialog,
  FilelistEgagementTypeUpdatePage,
} from './filelist-egagement-type.page-object';

const expect = chai.expect;

describe('FilelistEgagementType e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let filelistEgagementTypeComponentsPage: FilelistEgagementTypeComponentsPage;
  let filelistEgagementTypeUpdatePage: FilelistEgagementTypeUpdatePage;
  let filelistEgagementTypeDeleteDialog: FilelistEgagementTypeDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load FilelistEgagementTypes', async () => {
    await navBarPage.goToEntity('filelist-egagement-type');
    filelistEgagementTypeComponentsPage = new FilelistEgagementTypeComponentsPage();
    await browser.wait(ec.visibilityOf(filelistEgagementTypeComponentsPage.title), 5000);
    expect(await filelistEgagementTypeComponentsPage.getTitle()).to.eq('bankLoanManagerApp.filelistEgagementType.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(filelistEgagementTypeComponentsPage.entities), ec.visibilityOf(filelistEgagementTypeComponentsPage.noResult)),
      1000
    );
  });

  it('should load create FilelistEgagementType page', async () => {
    await filelistEgagementTypeComponentsPage.clickOnCreateButton();
    filelistEgagementTypeUpdatePage = new FilelistEgagementTypeUpdatePage();
    expect(await filelistEgagementTypeUpdatePage.getPageTitle()).to.eq('bankLoanManagerApp.filelistEgagementType.home.createOrEditLabel');
    await filelistEgagementTypeUpdatePage.cancel();
  });

  it('should create and save FilelistEgagementTypes', async () => {
    const nbButtonsBeforeCreate = await filelistEgagementTypeComponentsPage.countDeleteButtons();

    await filelistEgagementTypeComponentsPage.clickOnCreateButton();

    await promise.all([
      filelistEgagementTypeUpdatePage.engagementTypeSelectLastOption(),
      filelistEgagementTypeUpdatePage.filelistSelectLastOption(),
    ]);

    const selectedMandatory = filelistEgagementTypeUpdatePage.getMandatoryInput();
    if (await selectedMandatory.isSelected()) {
      await filelistEgagementTypeUpdatePage.getMandatoryInput().click();
      expect(await filelistEgagementTypeUpdatePage.getMandatoryInput().isSelected(), 'Expected mandatory not to be selected').to.be.false;
    } else {
      await filelistEgagementTypeUpdatePage.getMandatoryInput().click();
      expect(await filelistEgagementTypeUpdatePage.getMandatoryInput().isSelected(), 'Expected mandatory to be selected').to.be.true;
    }

    await filelistEgagementTypeUpdatePage.save();
    expect(await filelistEgagementTypeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await filelistEgagementTypeComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last FilelistEgagementType', async () => {
    const nbButtonsBeforeDelete = await filelistEgagementTypeComponentsPage.countDeleteButtons();
    await filelistEgagementTypeComponentsPage.clickOnLastDeleteButton();

    filelistEgagementTypeDeleteDialog = new FilelistEgagementTypeDeleteDialog();
    expect(await filelistEgagementTypeDeleteDialog.getDialogTitle()).to.eq('bankLoanManagerApp.filelistEgagementType.delete.question');
    await filelistEgagementTypeDeleteDialog.clickOnConfirmButton();

    expect(await filelistEgagementTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
