import { browser, ExpectedConditions as ec /* , protractor, promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  RejectComponentsPage,
  /* RejectDeleteDialog, */
  RejectUpdatePage,
} from './reject.page-object';

const expect = chai.expect;

describe('Reject e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let rejectComponentsPage: RejectComponentsPage;
  let rejectUpdatePage: RejectUpdatePage;
  /* let rejectDeleteDialog: RejectDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Rejects', async () => {
    await navBarPage.goToEntity('reject');
    rejectComponentsPage = new RejectComponentsPage();
    await browser.wait(ec.visibilityOf(rejectComponentsPage.title), 5000);
    expect(await rejectComponentsPage.getTitle()).to.eq('bankLoanManagerApp.reject.home.title');
    await browser.wait(ec.or(ec.visibilityOf(rejectComponentsPage.entities), ec.visibilityOf(rejectComponentsPage.noResult)), 1000);
  });

  it('should load create Reject page', async () => {
    await rejectComponentsPage.clickOnCreateButton();
    rejectUpdatePage = new RejectUpdatePage();
    expect(await rejectUpdatePage.getPageTitle()).to.eq('bankLoanManagerApp.reject.home.createOrEditLabel');
    await rejectUpdatePage.cancel();
  });

  /* it('should create and save Rejects', async () => {
        const nbButtonsBeforeCreate = await rejectComponentsPage.countDeleteButtons();

        await rejectComponentsPage.clickOnCreateButton();

        await promise.all([
            rejectUpdatePage.setCreatedDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            rejectUpdatePage.setCapitalInput('5'),
            rejectUpdatePage.setInterestsInput('5'),
            rejectUpdatePage.setPenaltiesInput('5'),
            rejectUpdatePage.setAccessoriesInput('5'),
            rejectUpdatePage.setDescriptionInput('description'),
            rejectUpdatePage.typeSelectLastOption(),
            rejectUpdatePage.engagementSelectLastOption(),
        ]);

        expect(await rejectUpdatePage.getCreatedDateInput()).to.contain('2001-01-01T02:30', 'Expected createdDate value to be equals to 2000-12-31');
        expect(await rejectUpdatePage.getCapitalInput()).to.eq('5', 'Expected capital value to be equals to 5');
        expect(await rejectUpdatePage.getInterestsInput()).to.eq('5', 'Expected interests value to be equals to 5');
        expect(await rejectUpdatePage.getPenaltiesInput()).to.eq('5', 'Expected penalties value to be equals to 5');
        expect(await rejectUpdatePage.getAccessoriesInput()).to.eq('5', 'Expected accessories value to be equals to 5');
        expect(await rejectUpdatePage.getDescriptionInput()).to.eq('description', 'Expected Description value to be equals to description');

        await rejectUpdatePage.save();
        expect(await rejectUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await rejectComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /* it('should delete last Reject', async () => {
        const nbButtonsBeforeDelete = await rejectComponentsPage.countDeleteButtons();
        await rejectComponentsPage.clickOnLastDeleteButton();

        rejectDeleteDialog = new RejectDeleteDialog();
        expect(await rejectDeleteDialog.getDialogTitle())
            .to.eq('bankLoanManagerApp.reject.delete.question');
        await rejectDeleteDialog.clickOnConfirmButton();

        expect(await rejectComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
