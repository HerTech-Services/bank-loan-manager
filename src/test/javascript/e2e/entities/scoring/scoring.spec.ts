import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ScoringComponentsPage, ScoringDeleteDialog, ScoringUpdatePage } from './scoring.page-object';

const expect = chai.expect;

describe('Scoring e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let scoringComponentsPage: ScoringComponentsPage;
  let scoringUpdatePage: ScoringUpdatePage;
  let scoringDeleteDialog: ScoringDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Scorings', async () => {
    await navBarPage.goToEntity('scoring');
    scoringComponentsPage = new ScoringComponentsPage();
    await browser.wait(ec.visibilityOf(scoringComponentsPage.title), 5000);
    expect(await scoringComponentsPage.getTitle()).to.eq('bankLoanManagerApp.scoring.home.title');
    await browser.wait(ec.or(ec.visibilityOf(scoringComponentsPage.entities), ec.visibilityOf(scoringComponentsPage.noResult)), 1000);
  });

  it('should load create Scoring page', async () => {
    await scoringComponentsPage.clickOnCreateButton();
    scoringUpdatePage = new ScoringUpdatePage();
    expect(await scoringUpdatePage.getPageTitle()).to.eq('bankLoanManagerApp.scoring.home.createOrEditLabel');
    await scoringUpdatePage.cancel();
  });

  it('should create and save Scorings', async () => {
    const nbButtonsBeforeCreate = await scoringComponentsPage.countDeleteButtons();

    await scoringComponentsPage.clickOnCreateButton();

    await promise.all([scoringUpdatePage.setLabelInput('label'), scoringUpdatePage.setScoreInput('5')]);

    expect(await scoringUpdatePage.getLabelInput()).to.eq('label', 'Expected Label value to be equals to label');
    expect(await scoringUpdatePage.getScoreInput()).to.eq('5', 'Expected score value to be equals to 5');

    await scoringUpdatePage.save();
    expect(await scoringUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await scoringComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Scoring', async () => {
    const nbButtonsBeforeDelete = await scoringComponentsPage.countDeleteButtons();
    await scoringComponentsPage.clickOnLastDeleteButton();

    scoringDeleteDialog = new ScoringDeleteDialog();
    expect(await scoringDeleteDialog.getDialogTitle()).to.eq('bankLoanManagerApp.scoring.delete.question');
    await scoringDeleteDialog.clickOnConfirmButton();

    expect(await scoringComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
