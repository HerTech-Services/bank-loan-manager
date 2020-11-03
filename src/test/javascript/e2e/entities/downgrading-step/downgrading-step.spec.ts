import { browser, ExpectedConditions as ec /* , promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  DowngradingStepComponentsPage,
  /* DowngradingStepDeleteDialog, */
  DowngradingStepUpdatePage,
} from './downgrading-step.page-object';

const expect = chai.expect;

describe('DowngradingStep e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let downgradingStepComponentsPage: DowngradingStepComponentsPage;
  let downgradingStepUpdatePage: DowngradingStepUpdatePage;
  /* let downgradingStepDeleteDialog: DowngradingStepDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load DowngradingSteps', async () => {
    await navBarPage.goToEntity('downgrading-step');
    downgradingStepComponentsPage = new DowngradingStepComponentsPage();
    await browser.wait(ec.visibilityOf(downgradingStepComponentsPage.title), 5000);
    expect(await downgradingStepComponentsPage.getTitle()).to.eq('bankLoanManagerApp.downgradingStep.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(downgradingStepComponentsPage.entities), ec.visibilityOf(downgradingStepComponentsPage.noResult)),
      1000
    );
  });

  it('should load create DowngradingStep page', async () => {
    await downgradingStepComponentsPage.clickOnCreateButton();
    downgradingStepUpdatePage = new DowngradingStepUpdatePage();
    expect(await downgradingStepUpdatePage.getPageTitle()).to.eq('bankLoanManagerApp.downgradingStep.home.createOrEditLabel');
    await downgradingStepUpdatePage.cancel();
  });

  /* it('should create and save DowngradingSteps', async () => {
        const nbButtonsBeforeCreate = await downgradingStepComponentsPage.countDeleteButtons();

        await downgradingStepComponentsPage.clickOnCreateButton();

        await promise.all([
            downgradingStepUpdatePage.setLabelInput('label'),
            downgradingStepUpdatePage.setIdentifierInput('identifier'),
            downgradingStepUpdatePage.scoringSelectLastOption(),
        ]);

        expect(await downgradingStepUpdatePage.getLabelInput()).to.eq('label', 'Expected Label value to be equals to label');
        expect(await downgradingStepUpdatePage.getIdentifierInput()).to.eq('identifier', 'Expected Identifier value to be equals to identifier');

        await downgradingStepUpdatePage.save();
        expect(await downgradingStepUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await downgradingStepComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /* it('should delete last DowngradingStep', async () => {
        const nbButtonsBeforeDelete = await downgradingStepComponentsPage.countDeleteButtons();
        await downgradingStepComponentsPage.clickOnLastDeleteButton();

        downgradingStepDeleteDialog = new DowngradingStepDeleteDialog();
        expect(await downgradingStepDeleteDialog.getDialogTitle())
            .to.eq('bankLoanManagerApp.downgradingStep.delete.question');
        await downgradingStepDeleteDialog.clickOnConfirmButton();

        expect(await downgradingStepComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
