import { browser, ExpectedConditions as ec /* , protractor, promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  DowngradingComponentsPage,
  /* DowngradingDeleteDialog, */
  DowngradingUpdatePage,
} from './downgrading.page-object';

const expect = chai.expect;

describe('Downgrading e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let downgradingComponentsPage: DowngradingComponentsPage;
  let downgradingUpdatePage: DowngradingUpdatePage;
  /* let downgradingDeleteDialog: DowngradingDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Downgradings', async () => {
    await navBarPage.goToEntity('downgrading');
    downgradingComponentsPage = new DowngradingComponentsPage();
    await browser.wait(ec.visibilityOf(downgradingComponentsPage.title), 5000);
    expect(await downgradingComponentsPage.getTitle()).to.eq('bankLoanManagerApp.downgrading.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(downgradingComponentsPage.entities), ec.visibilityOf(downgradingComponentsPage.noResult)),
      1000
    );
  });

  it('should load create Downgrading page', async () => {
    await downgradingComponentsPage.clickOnCreateButton();
    downgradingUpdatePage = new DowngradingUpdatePage();
    expect(await downgradingUpdatePage.getPageTitle()).to.eq('bankLoanManagerApp.downgrading.home.createOrEditLabel');
    await downgradingUpdatePage.cancel();
  });

  /* it('should create and save Downgradings', async () => {
        const nbButtonsBeforeCreate = await downgradingComponentsPage.countDeleteButtons();

        await downgradingComponentsPage.clickOnCreateButton();

        await promise.all([
            downgradingUpdatePage.setCreatedDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            downgradingUpdatePage.setCapitalInput('5'),
            downgradingUpdatePage.setInterestsInput('5'),
            downgradingUpdatePage.setPenaltiesInput('5'),
            downgradingUpdatePage.setAccessoriesInput('5'),
            downgradingUpdatePage.setDescriptionInput('description'),
            downgradingUpdatePage.stepSelectLastOption(),
            downgradingUpdatePage.engagementSelectLastOption(),
        ]);

        expect(await downgradingUpdatePage.getCreatedDateInput()).to.contain('2001-01-01T02:30', 'Expected createdDate value to be equals to 2000-12-31');
        expect(await downgradingUpdatePage.getCapitalInput()).to.eq('5', 'Expected capital value to be equals to 5');
        expect(await downgradingUpdatePage.getInterestsInput()).to.eq('5', 'Expected interests value to be equals to 5');
        expect(await downgradingUpdatePage.getPenaltiesInput()).to.eq('5', 'Expected penalties value to be equals to 5');
        expect(await downgradingUpdatePage.getAccessoriesInput()).to.eq('5', 'Expected accessories value to be equals to 5');
        expect(await downgradingUpdatePage.getDescriptionInput()).to.eq('description', 'Expected Description value to be equals to description');

        await downgradingUpdatePage.save();
        expect(await downgradingUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await downgradingComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /* it('should delete last Downgrading', async () => {
        const nbButtonsBeforeDelete = await downgradingComponentsPage.countDeleteButtons();
        await downgradingComponentsPage.clickOnLastDeleteButton();

        downgradingDeleteDialog = new DowngradingDeleteDialog();
        expect(await downgradingDeleteDialog.getDialogTitle())
            .to.eq('bankLoanManagerApp.downgrading.delete.question');
        await downgradingDeleteDialog.clickOnConfirmButton();

        expect(await downgradingComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
