import { browser, ExpectedConditions as ec /* , promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  RejectTypeComponentsPage,
  /* RejectTypeDeleteDialog, */
  RejectTypeUpdatePage,
} from './reject-type.page-object';

const expect = chai.expect;

describe('RejectType e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let rejectTypeComponentsPage: RejectTypeComponentsPage;
  let rejectTypeUpdatePage: RejectTypeUpdatePage;
  /* let rejectTypeDeleteDialog: RejectTypeDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load RejectTypes', async () => {
    await navBarPage.goToEntity('reject-type');
    rejectTypeComponentsPage = new RejectTypeComponentsPage();
    await browser.wait(ec.visibilityOf(rejectTypeComponentsPage.title), 5000);
    expect(await rejectTypeComponentsPage.getTitle()).to.eq('bankLoanManagerApp.rejectType.home.title');
    await browser.wait(ec.or(ec.visibilityOf(rejectTypeComponentsPage.entities), ec.visibilityOf(rejectTypeComponentsPage.noResult)), 1000);
  });

  it('should load create RejectType page', async () => {
    await rejectTypeComponentsPage.clickOnCreateButton();
    rejectTypeUpdatePage = new RejectTypeUpdatePage();
    expect(await rejectTypeUpdatePage.getPageTitle()).to.eq('bankLoanManagerApp.rejectType.home.createOrEditLabel');
    await rejectTypeUpdatePage.cancel();
  });

  /* it('should create and save RejectTypes', async () => {
        const nbButtonsBeforeCreate = await rejectTypeComponentsPage.countDeleteButtons();

        await rejectTypeComponentsPage.clickOnCreateButton();

        await promise.all([
            rejectTypeUpdatePage.setLabelInput('label'),
            rejectTypeUpdatePage.setIdentifierInput('identifier'),
            rejectTypeUpdatePage.scoringSelectLastOption(),
        ]);

        expect(await rejectTypeUpdatePage.getLabelInput()).to.eq('label', 'Expected Label value to be equals to label');
        expect(await rejectTypeUpdatePage.getIdentifierInput()).to.eq('identifier', 'Expected Identifier value to be equals to identifier');

        await rejectTypeUpdatePage.save();
        expect(await rejectTypeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await rejectTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /* it('should delete last RejectType', async () => {
        const nbButtonsBeforeDelete = await rejectTypeComponentsPage.countDeleteButtons();
        await rejectTypeComponentsPage.clickOnLastDeleteButton();

        rejectTypeDeleteDialog = new RejectTypeDeleteDialog();
        expect(await rejectTypeDeleteDialog.getDialogTitle())
            .to.eq('bankLoanManagerApp.rejectType.delete.question');
        await rejectTypeDeleteDialog.clickOnConfirmButton();

        expect(await rejectTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
