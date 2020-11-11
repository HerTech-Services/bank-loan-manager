import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { EngagementTypeComponentsPage, EngagementTypeDeleteDialog, EngagementTypeUpdatePage } from './engagement-type.page-object';

const expect = chai.expect;

describe('EngagementType e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let engagementTypeComponentsPage: EngagementTypeComponentsPage;
  let engagementTypeUpdatePage: EngagementTypeUpdatePage;
  let engagementTypeDeleteDialog: EngagementTypeDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load EngagementTypes', async () => {
    await navBarPage.goToEntity('engagement-type');
    engagementTypeComponentsPage = new EngagementTypeComponentsPage();
    await browser.wait(ec.visibilityOf(engagementTypeComponentsPage.title), 5000);
    expect(await engagementTypeComponentsPage.getTitle()).to.eq('bankLoanManagerApp.engagementType.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(engagementTypeComponentsPage.entities), ec.visibilityOf(engagementTypeComponentsPage.noResult)),
      1000
    );
  });

  it('should load create EngagementType page', async () => {
    await engagementTypeComponentsPage.clickOnCreateButton();
    engagementTypeUpdatePage = new EngagementTypeUpdatePage();
    expect(await engagementTypeUpdatePage.getPageTitle()).to.eq('bankLoanManagerApp.engagementType.home.createOrEditLabel');
    await engagementTypeUpdatePage.cancel();
  });

  it('should create and save EngagementTypes', async () => {
    const nbButtonsBeforeCreate = await engagementTypeComponentsPage.countDeleteButtons();

    await engagementTypeComponentsPage.clickOnCreateButton();

    await promise.all([
      engagementTypeUpdatePage.setLabelInput('label'),
      engagementTypeUpdatePage.setProcessDelayInput('5'),
      engagementTypeUpdatePage.setDelay1Input('5'),
      engagementTypeUpdatePage.setDelay2Input('5'),
    ]);

    expect(await engagementTypeUpdatePage.getLabelInput()).to.eq('label', 'Expected Label value to be equals to label');
    const selectedIsEnabled = engagementTypeUpdatePage.getIsEnabledInput();
    if (await selectedIsEnabled.isSelected()) {
      await engagementTypeUpdatePage.getIsEnabledInput().click();
      expect(await engagementTypeUpdatePage.getIsEnabledInput().isSelected(), 'Expected isEnabled not to be selected').to.be.false;
    } else {
      await engagementTypeUpdatePage.getIsEnabledInput().click();
      expect(await engagementTypeUpdatePage.getIsEnabledInput().isSelected(), 'Expected isEnabled to be selected').to.be.true;
    }
    expect(await engagementTypeUpdatePage.getProcessDelayInput()).to.eq('5', 'Expected processDelay value to be equals to 5');
    expect(await engagementTypeUpdatePage.getDelay1Input()).to.eq('5', 'Expected delay1 value to be equals to 5');
    expect(await engagementTypeUpdatePage.getDelay2Input()).to.eq('5', 'Expected delay2 value to be equals to 5');

    await engagementTypeUpdatePage.save();
    expect(await engagementTypeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await engagementTypeComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last EngagementType', async () => {
    const nbButtonsBeforeDelete = await engagementTypeComponentsPage.countDeleteButtons();
    await engagementTypeComponentsPage.clickOnLastDeleteButton();

    engagementTypeDeleteDialog = new EngagementTypeDeleteDialog();
    expect(await engagementTypeDeleteDialog.getDialogTitle()).to.eq('bankLoanManagerApp.engagementType.delete.question');
    await engagementTypeDeleteDialog.clickOnConfirmButton();

    expect(await engagementTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
