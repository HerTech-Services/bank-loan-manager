import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { PosteComponentsPage, PosteDeleteDialog, PosteUpdatePage } from './poste.page-object';

const expect = chai.expect;

describe('Poste e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let posteComponentsPage: PosteComponentsPage;
  let posteUpdatePage: PosteUpdatePage;
  let posteDeleteDialog: PosteDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Postes', async () => {
    await navBarPage.goToEntity('poste');
    posteComponentsPage = new PosteComponentsPage();
    await browser.wait(ec.visibilityOf(posteComponentsPage.title), 5000);
    expect(await posteComponentsPage.getTitle()).to.eq('bankLoanManagerApp.poste.home.title');
    await browser.wait(ec.or(ec.visibilityOf(posteComponentsPage.entities), ec.visibilityOf(posteComponentsPage.noResult)), 1000);
  });

  it('should load create Poste page', async () => {
    await posteComponentsPage.clickOnCreateButton();
    posteUpdatePage = new PosteUpdatePage();
    expect(await posteUpdatePage.getPageTitle()).to.eq('bankLoanManagerApp.poste.home.createOrEditLabel');
    await posteUpdatePage.cancel();
  });

  it('should create and save Postes', async () => {
    const nbButtonsBeforeCreate = await posteComponentsPage.countDeleteButtons();

    await posteComponentsPage.clickOnCreateButton();

    await promise.all([
      posteUpdatePage.setCodeInput('code'),
      posteUpdatePage.setLabelInput('label'),
      posteUpdatePage.setShortLabelInput('shortLabel'),
      posteUpdatePage.setEntityInput('entity'),
      posteUpdatePage.setAdrs1Input('adrs1'),
      posteUpdatePage.setAdrs2Input('adrs2'),
      posteUpdatePage.setAdrs3Input('adrs3'),
      posteUpdatePage.setCityInput('city'),
      // posteUpdatePage.parentPosteSelectLastOption(),
    ]);

    expect(await posteUpdatePage.getCodeInput()).to.eq('code', 'Expected Code value to be equals to code');
    expect(await posteUpdatePage.getLabelInput()).to.eq('label', 'Expected Label value to be equals to label');
    expect(await posteUpdatePage.getShortLabelInput()).to.eq('shortLabel', 'Expected ShortLabel value to be equals to shortLabel');
    expect(await posteUpdatePage.getEntityInput()).to.eq('entity', 'Expected Entity value to be equals to entity');
    const selectedEnabled = posteUpdatePage.getEnabledInput();
    if (await selectedEnabled.isSelected()) {
      await posteUpdatePage.getEnabledInput().click();
      expect(await posteUpdatePage.getEnabledInput().isSelected(), 'Expected enabled not to be selected').to.be.false;
    } else {
      await posteUpdatePage.getEnabledInput().click();
      expect(await posteUpdatePage.getEnabledInput().isSelected(), 'Expected enabled to be selected').to.be.true;
    }
    expect(await posteUpdatePage.getAdrs1Input()).to.eq('adrs1', 'Expected Adrs1 value to be equals to adrs1');
    expect(await posteUpdatePage.getAdrs2Input()).to.eq('adrs2', 'Expected Adrs2 value to be equals to adrs2');
    expect(await posteUpdatePage.getAdrs3Input()).to.eq('adrs3', 'Expected Adrs3 value to be equals to adrs3');
    expect(await posteUpdatePage.getCityInput()).to.eq('city', 'Expected City value to be equals to city');

    await posteUpdatePage.save();
    expect(await posteUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await posteComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Poste', async () => {
    const nbButtonsBeforeDelete = await posteComponentsPage.countDeleteButtons();
    await posteComponentsPage.clickOnLastDeleteButton();

    posteDeleteDialog = new PosteDeleteDialog();
    expect(await posteDeleteDialog.getDialogTitle()).to.eq('bankLoanManagerApp.poste.delete.question');
    await posteDeleteDialog.clickOnConfirmButton();

    expect(await posteComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
