import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { EntiteComponentsPage, EntiteDeleteDialog, EntiteUpdatePage } from './entite.page-object';

const expect = chai.expect;

describe('Entite e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let entiteComponentsPage: EntiteComponentsPage;
  let entiteUpdatePage: EntiteUpdatePage;
  let entiteDeleteDialog: EntiteDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Entites', async () => {
    await navBarPage.goToEntity('entite');
    entiteComponentsPage = new EntiteComponentsPage();
    await browser.wait(ec.visibilityOf(entiteComponentsPage.title), 5000);
    expect(await entiteComponentsPage.getTitle()).to.eq('bankLoanManagerApp.entite.home.title');
    await browser.wait(ec.or(ec.visibilityOf(entiteComponentsPage.entities), ec.visibilityOf(entiteComponentsPage.noResult)), 1000);
  });

  it('should load create Entite page', async () => {
    await entiteComponentsPage.clickOnCreateButton();
    entiteUpdatePage = new EntiteUpdatePage();
    expect(await entiteUpdatePage.getPageTitle()).to.eq('bankLoanManagerApp.entite.home.createOrEditLabel');
    await entiteUpdatePage.cancel();
  });

  it('should create and save Entites', async () => {
    const nbButtonsBeforeCreate = await entiteComponentsPage.countDeleteButtons();

    await entiteComponentsPage.clickOnCreateButton();

    await promise.all([
      entiteUpdatePage.setIdentifierInput('identifier'),
      entiteUpdatePage.setLabelInput('label'),
      entiteUpdatePage.setShortLabelInput('shortLabel'),
      entiteUpdatePage.setParentInput('5'),
      entiteUpdatePage.setAdrs1Input('adrs1'),
      entiteUpdatePage.setAdrs2Input('adrs2'),
      entiteUpdatePage.setAdrs3Input('adrs3'),
      entiteUpdatePage.setZipcodeInput('zipcode'),
      entiteUpdatePage.setCityInput('city'),
      entiteUpdatePage.setCountryInput('country'),
      entiteUpdatePage.setEmailInput('email'),
      entiteUpdatePage.setParametersInput('parameters'),
      entiteUpdatePage.setParentIdInput('parentId'),
    ]);

    expect(await entiteUpdatePage.getIdentifierInput()).to.eq('identifier', 'Expected Identifier value to be equals to identifier');
    expect(await entiteUpdatePage.getLabelInput()).to.eq('label', 'Expected Label value to be equals to label');
    expect(await entiteUpdatePage.getShortLabelInput()).to.eq('shortLabel', 'Expected ShortLabel value to be equals to shortLabel');
    expect(await entiteUpdatePage.getParentInput()).to.eq('5', 'Expected parent value to be equals to 5');
    const selectedIsEnabled = entiteUpdatePage.getIsEnabledInput();
    if (await selectedIsEnabled.isSelected()) {
      await entiteUpdatePage.getIsEnabledInput().click();
      expect(await entiteUpdatePage.getIsEnabledInput().isSelected(), 'Expected isEnabled not to be selected').to.be.false;
    } else {
      await entiteUpdatePage.getIsEnabledInput().click();
      expect(await entiteUpdatePage.getIsEnabledInput().isSelected(), 'Expected isEnabled to be selected').to.be.true;
    }
    expect(await entiteUpdatePage.getAdrs1Input()).to.eq('adrs1', 'Expected Adrs1 value to be equals to adrs1');
    expect(await entiteUpdatePage.getAdrs2Input()).to.eq('adrs2', 'Expected Adrs2 value to be equals to adrs2');
    expect(await entiteUpdatePage.getAdrs3Input()).to.eq('adrs3', 'Expected Adrs3 value to be equals to adrs3');
    expect(await entiteUpdatePage.getZipcodeInput()).to.eq('zipcode', 'Expected Zipcode value to be equals to zipcode');
    expect(await entiteUpdatePage.getCityInput()).to.eq('city', 'Expected City value to be equals to city');
    expect(await entiteUpdatePage.getCountryInput()).to.eq('country', 'Expected Country value to be equals to country');
    expect(await entiteUpdatePage.getEmailInput()).to.eq('email', 'Expected Email value to be equals to email');
    expect(await entiteUpdatePage.getParametersInput()).to.eq('parameters', 'Expected Parameters value to be equals to parameters');
    expect(await entiteUpdatePage.getParentIdInput()).to.eq('parentId', 'Expected ParentId value to be equals to parentId');

    await entiteUpdatePage.save();
    expect(await entiteUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await entiteComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Entite', async () => {
    const nbButtonsBeforeDelete = await entiteComponentsPage.countDeleteButtons();
    await entiteComponentsPage.clickOnLastDeleteButton();

    entiteDeleteDialog = new EntiteDeleteDialog();
    expect(await entiteDeleteDialog.getDialogTitle()).to.eq('bankLoanManagerApp.entite.delete.question');
    await entiteDeleteDialog.clickOnConfirmButton();

    expect(await entiteComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
