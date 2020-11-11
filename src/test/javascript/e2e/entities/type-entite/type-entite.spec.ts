import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { TypeEntiteComponentsPage, TypeEntiteDeleteDialog, TypeEntiteUpdatePage } from './type-entite.page-object';

const expect = chai.expect;

describe('TypeEntite e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let typeEntiteComponentsPage: TypeEntiteComponentsPage;
  let typeEntiteUpdatePage: TypeEntiteUpdatePage;
  let typeEntiteDeleteDialog: TypeEntiteDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load TypeEntites', async () => {
    await navBarPage.goToEntity('type-entite');
    typeEntiteComponentsPage = new TypeEntiteComponentsPage();
    await browser.wait(ec.visibilityOf(typeEntiteComponentsPage.title), 5000);
    expect(await typeEntiteComponentsPage.getTitle()).to.eq('bankLoanManagerApp.typeEntite.home.title');
    await browser.wait(ec.or(ec.visibilityOf(typeEntiteComponentsPage.entities), ec.visibilityOf(typeEntiteComponentsPage.noResult)), 1000);
  });

  it('should load create TypeEntite page', async () => {
    await typeEntiteComponentsPage.clickOnCreateButton();
    typeEntiteUpdatePage = new TypeEntiteUpdatePage();
    expect(await typeEntiteUpdatePage.getPageTitle()).to.eq('bankLoanManagerApp.typeEntite.home.createOrEditLabel');
    await typeEntiteUpdatePage.cancel();
  });

  it('should create and save TypeEntites', async () => {
    const nbButtonsBeforeCreate = await typeEntiteComponentsPage.countDeleteButtons();

    await typeEntiteComponentsPage.clickOnCreateButton();

    await promise.all([
      typeEntiteUpdatePage.setIdentifierInput('identifier'),
      typeEntiteUpdatePage.setLabelInput('label'),
      typeEntiteUpdatePage.setShortLabelInput('shortLabel'),
    ]);

    expect(await typeEntiteUpdatePage.getIdentifierInput()).to.eq('identifier', 'Expected Identifier value to be equals to identifier');
    expect(await typeEntiteUpdatePage.getLabelInput()).to.eq('label', 'Expected Label value to be equals to label');
    expect(await typeEntiteUpdatePage.getShortLabelInput()).to.eq('shortLabel', 'Expected ShortLabel value to be equals to shortLabel');

    await typeEntiteUpdatePage.save();
    expect(await typeEntiteUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await typeEntiteComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last TypeEntite', async () => {
    const nbButtonsBeforeDelete = await typeEntiteComponentsPage.countDeleteButtons();
    await typeEntiteComponentsPage.clickOnLastDeleteButton();

    typeEntiteDeleteDialog = new TypeEntiteDeleteDialog();
    expect(await typeEntiteDeleteDialog.getDialogTitle()).to.eq('bankLoanManagerApp.typeEntite.delete.question');
    await typeEntiteDeleteDialog.clickOnConfirmButton();

    expect(await typeEntiteComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
