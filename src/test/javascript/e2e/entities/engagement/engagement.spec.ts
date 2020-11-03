import { browser, ExpectedConditions as ec /* , protractor, promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  EngagementComponentsPage,
  /* EngagementDeleteDialog, */
  EngagementUpdatePage,
} from './engagement.page-object';

const expect = chai.expect;

describe('Engagement e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let engagementComponentsPage: EngagementComponentsPage;
  let engagementUpdatePage: EngagementUpdatePage;
  /* let engagementDeleteDialog: EngagementDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Engagements', async () => {
    await navBarPage.goToEntity('engagement');
    engagementComponentsPage = new EngagementComponentsPage();
    await browser.wait(ec.visibilityOf(engagementComponentsPage.title), 5000);
    expect(await engagementComponentsPage.getTitle()).to.eq('bankLoanManagerApp.engagement.home.title');
    await browser.wait(ec.or(ec.visibilityOf(engagementComponentsPage.entities), ec.visibilityOf(engagementComponentsPage.noResult)), 1000);
  });

  it('should load create Engagement page', async () => {
    await engagementComponentsPage.clickOnCreateButton();
    engagementUpdatePage = new EngagementUpdatePage();
    expect(await engagementUpdatePage.getPageTitle()).to.eq('bankLoanManagerApp.engagement.home.createOrEditLabel');
    await engagementUpdatePage.cancel();
  });

  /* it('should create and save Engagements', async () => {
        const nbButtonsBeforeCreate = await engagementComponentsPage.countDeleteButtons();

        await engagementComponentsPage.clickOnCreateButton();

        await promise.all([
            engagementUpdatePage.setScoringInput('5'),
            engagementUpdatePage.setSubjectInput('subject'),
            engagementUpdatePage.setAmountInput('5'),
            engagementUpdatePage.setDelayInput('5'),
            engagementUpdatePage.paymentSelectLastOption(),
            engagementUpdatePage.setDelayedInput('5'),
            engagementUpdatePage.reimbursementSelectLastOption(),
            engagementUpdatePage.setAnalyseInput('analyse'),
            engagementUpdatePage.setStartDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            engagementUpdatePage.setEnDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            engagementUpdatePage.setCreatedDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            engagementUpdatePage.setUpdatedDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            engagementUpdatePage.setDeletedDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            engagementUpdatePage.setStopedDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            engagementUpdatePage.currentTaskSelectLastOption(),
            engagementUpdatePage.typeSelectLastOption(),
            engagementUpdatePage.statusSelectLastOption(),
            engagementUpdatePage.decisionSelectLastOption(),
            engagementUpdatePage.createdBySelectLastOption(),
            engagementUpdatePage.clientSelectLastOption(),
            engagementUpdatePage.compteSelectLastOption(),
        ]);

        expect(await engagementUpdatePage.getScoringInput()).to.eq('5', 'Expected scoring value to be equals to 5');
        expect(await engagementUpdatePage.getSubjectInput()).to.eq('subject', 'Expected Subject value to be equals to subject');
        expect(await engagementUpdatePage.getAmountInput()).to.eq('5', 'Expected amount value to be equals to 5');
        expect(await engagementUpdatePage.getDelayInput()).to.eq('5', 'Expected delay value to be equals to 5');
        expect(await engagementUpdatePage.getDelayedInput()).to.eq('5', 'Expected delayed value to be equals to 5');
        expect(await engagementUpdatePage.getAnalyseInput()).to.eq('analyse', 'Expected Analyse value to be equals to analyse');
        const selectedIsStop = engagementUpdatePage.getIsStopInput();
        if (await selectedIsStop.isSelected()) {
            await engagementUpdatePage.getIsStopInput().click();
            expect(await engagementUpdatePage.getIsStopInput().isSelected(), 'Expected isStop not to be selected').to.be.false;
        } else {
            await engagementUpdatePage.getIsStopInput().click();
            expect(await engagementUpdatePage.getIsStopInput().isSelected(), 'Expected isStop to be selected').to.be.true;
        }
        const selectedIsDeleted = engagementUpdatePage.getIsDeletedInput();
        if (await selectedIsDeleted.isSelected()) {
            await engagementUpdatePage.getIsDeletedInput().click();
            expect(await engagementUpdatePage.getIsDeletedInput().isSelected(), 'Expected isDeleted not to be selected').to.be.false;
        } else {
            await engagementUpdatePage.getIsDeletedInput().click();
            expect(await engagementUpdatePage.getIsDeletedInput().isSelected(), 'Expected isDeleted to be selected').to.be.true;
        }
        expect(await engagementUpdatePage.getStartDateInput()).to.contain('2001-01-01T02:30', 'Expected startDate value to be equals to 2000-12-31');
        expect(await engagementUpdatePage.getEnDateInput()).to.contain('2001-01-01T02:30', 'Expected enDate value to be equals to 2000-12-31');
        expect(await engagementUpdatePage.getCreatedDateInput()).to.contain('2001-01-01T02:30', 'Expected createdDate value to be equals to 2000-12-31');
        expect(await engagementUpdatePage.getUpdatedDateInput()).to.contain('2001-01-01T02:30', 'Expected updatedDate value to be equals to 2000-12-31');
        expect(await engagementUpdatePage.getDeletedDateInput()).to.contain('2001-01-01T02:30', 'Expected deletedDate value to be equals to 2000-12-31');
        expect(await engagementUpdatePage.getStopedDateInput()).to.contain('2001-01-01T02:30', 'Expected stopedDate value to be equals to 2000-12-31');

        await engagementUpdatePage.save();
        expect(await engagementUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await engagementComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /* it('should delete last Engagement', async () => {
        const nbButtonsBeforeDelete = await engagementComponentsPage.countDeleteButtons();
        await engagementComponentsPage.clickOnLastDeleteButton();

        engagementDeleteDialog = new EngagementDeleteDialog();
        expect(await engagementDeleteDialog.getDialogTitle())
            .to.eq('bankLoanManagerApp.engagement.delete.question');
        await engagementDeleteDialog.clickOnConfirmButton();

        expect(await engagementComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
