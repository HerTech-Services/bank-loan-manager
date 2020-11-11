import { browser, ExpectedConditions as ec /* , promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  CompteComponentsPage,
  /* CompteDeleteDialog, */
  CompteUpdatePage,
} from './compte.page-object';

const expect = chai.expect;

describe('Compte e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let compteComponentsPage: CompteComponentsPage;
  let compteUpdatePage: CompteUpdatePage;
  /* let compteDeleteDialog: CompteDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Comptes', async () => {
    await navBarPage.goToEntity('compte');
    compteComponentsPage = new CompteComponentsPage();
    await browser.wait(ec.visibilityOf(compteComponentsPage.title), 5000);
    expect(await compteComponentsPage.getTitle()).to.eq('bankLoanManagerApp.compte.home.title');
    await browser.wait(ec.or(ec.visibilityOf(compteComponentsPage.entities), ec.visibilityOf(compteComponentsPage.noResult)), 1000);
  });

  it('should load create Compte page', async () => {
    await compteComponentsPage.clickOnCreateButton();
    compteUpdatePage = new CompteUpdatePage();
    expect(await compteUpdatePage.getPageTitle()).to.eq('bankLoanManagerApp.compte.home.createOrEditLabel');
    await compteUpdatePage.cancel();
  });

  /* it('should create and save Comptes', async () => {
        const nbButtonsBeforeCreate = await compteComponentsPage.countDeleteButtons();

        await compteComponentsPage.clickOnCreateButton();

        await promise.all([
            compteUpdatePage.setCodBnkInput('codBnk'),
            compteUpdatePage.setCodCptInput('codCpt'),
            compteUpdatePage.setNumCptInput('numCpt'),
            compteUpdatePage.setLibCptInput('libCpt'),
            compteUpdatePage.setNumCptaInput('numCpta'),
            compteUpdatePage.setNumAgcInput('numAgc'),
            compteUpdatePage.setTypCptInput('typCpt'),
            compteUpdatePage.setEtatInput('etat'),
            compteUpdatePage.setCodCliInput('codCli'),
            compteUpdatePage.setNumCtrInput('numCtr'),
            compteUpdatePage.setCodeForInput('codeFor'),
            compteUpdatePage.clientSelectLastOption(),
        ]);

        expect(await compteUpdatePage.getCodBnkInput()).to.eq('codBnk', 'Expected CodBnk value to be equals to codBnk');
        expect(await compteUpdatePage.getCodCptInput()).to.eq('codCpt', 'Expected CodCpt value to be equals to codCpt');
        expect(await compteUpdatePage.getNumCptInput()).to.eq('numCpt', 'Expected NumCpt value to be equals to numCpt');
        expect(await compteUpdatePage.getLibCptInput()).to.eq('libCpt', 'Expected LibCpt value to be equals to libCpt');
        expect(await compteUpdatePage.getNumCptaInput()).to.eq('numCpta', 'Expected NumCpta value to be equals to numCpta');
        expect(await compteUpdatePage.getNumAgcInput()).to.eq('numAgc', 'Expected NumAgc value to be equals to numAgc');
        expect(await compteUpdatePage.getTypCptInput()).to.eq('typCpt', 'Expected TypCpt value to be equals to typCpt');
        expect(await compteUpdatePage.getEtatInput()).to.eq('etat', 'Expected Etat value to be equals to etat');
        expect(await compteUpdatePage.getCodCliInput()).to.eq('codCli', 'Expected CodCli value to be equals to codCli');
        expect(await compteUpdatePage.getNumCtrInput()).to.eq('numCtr', 'Expected NumCtr value to be equals to numCtr');
        expect(await compteUpdatePage.getCodeForInput()).to.eq('codeFor', 'Expected CodeFor value to be equals to codeFor');

        await compteUpdatePage.save();
        expect(await compteUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await compteComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /* it('should delete last Compte', async () => {
        const nbButtonsBeforeDelete = await compteComponentsPage.countDeleteButtons();
        await compteComponentsPage.clickOnLastDeleteButton();

        compteDeleteDialog = new CompteDeleteDialog();
        expect(await compteDeleteDialog.getDialogTitle())
            .to.eq('bankLoanManagerApp.compte.delete.question');
        await compteDeleteDialog.clickOnConfirmButton();

        expect(await compteComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
