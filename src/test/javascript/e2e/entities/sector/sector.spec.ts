import { browser, ExpectedConditions as ec /* , promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  SectorComponentsPage,
  /* SectorDeleteDialog, */
  SectorUpdatePage,
} from './sector.page-object';

const expect = chai.expect;

describe('Sector e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let sectorComponentsPage: SectorComponentsPage;
  let sectorUpdatePage: SectorUpdatePage;
  /* let sectorDeleteDialog: SectorDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Sectors', async () => {
    await navBarPage.goToEntity('sector');
    sectorComponentsPage = new SectorComponentsPage();
    await browser.wait(ec.visibilityOf(sectorComponentsPage.title), 5000);
    expect(await sectorComponentsPage.getTitle()).to.eq('bankLoanManagerApp.sector.home.title');
    await browser.wait(ec.or(ec.visibilityOf(sectorComponentsPage.entities), ec.visibilityOf(sectorComponentsPage.noResult)), 1000);
  });

  it('should load create Sector page', async () => {
    await sectorComponentsPage.clickOnCreateButton();
    sectorUpdatePage = new SectorUpdatePage();
    expect(await sectorUpdatePage.getPageTitle()).to.eq('bankLoanManagerApp.sector.home.createOrEditLabel');
    await sectorUpdatePage.cancel();
  });

  /* it('should create and save Sectors', async () => {
        const nbButtonsBeforeCreate = await sectorComponentsPage.countDeleteButtons();

        await sectorComponentsPage.clickOnCreateButton();

        await promise.all([
            sectorUpdatePage.setLabelInput('label'),
            sectorUpdatePage.setIdentifierInput('identifier'),
            sectorUpdatePage.scoringSelectLastOption(),
        ]);

        expect(await sectorUpdatePage.getLabelInput()).to.eq('label', 'Expected Label value to be equals to label');
        expect(await sectorUpdatePage.getIdentifierInput()).to.eq('identifier', 'Expected Identifier value to be equals to identifier');

        await sectorUpdatePage.save();
        expect(await sectorUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await sectorComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /* it('should delete last Sector', async () => {
        const nbButtonsBeforeDelete = await sectorComponentsPage.countDeleteButtons();
        await sectorComponentsPage.clickOnLastDeleteButton();

        sectorDeleteDialog = new SectorDeleteDialog();
        expect(await sectorDeleteDialog.getDialogTitle())
            .to.eq('bankLoanManagerApp.sector.delete.question');
        await sectorDeleteDialog.clickOnConfirmButton();

        expect(await sectorComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
