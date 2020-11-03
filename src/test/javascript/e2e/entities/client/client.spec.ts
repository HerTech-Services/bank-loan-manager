import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ClientComponentsPage, ClientDeleteDialog, ClientUpdatePage } from './client.page-object';

const expect = chai.expect;

describe('Client e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let clientComponentsPage: ClientComponentsPage;
  let clientUpdatePage: ClientUpdatePage;
  let clientDeleteDialog: ClientDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Clients', async () => {
    await navBarPage.goToEntity('client');
    clientComponentsPage = new ClientComponentsPage();
    await browser.wait(ec.visibilityOf(clientComponentsPage.title), 5000);
    expect(await clientComponentsPage.getTitle()).to.eq('bankLoanManagerApp.client.home.title');
    await browser.wait(ec.or(ec.visibilityOf(clientComponentsPage.entities), ec.visibilityOf(clientComponentsPage.noResult)), 1000);
  });

  it('should load create Client page', async () => {
    await clientComponentsPage.clickOnCreateButton();
    clientUpdatePage = new ClientUpdatePage();
    expect(await clientUpdatePage.getPageTitle()).to.eq('bankLoanManagerApp.client.home.createOrEditLabel');
    await clientUpdatePage.cancel();
  });

  it('should create and save Clients', async () => {
    const nbButtonsBeforeCreate = await clientComponentsPage.countDeleteButtons();

    await clientComponentsPage.clickOnCreateButton();

    await promise.all([
      clientUpdatePage.setCodBnkInput('codBnk'),
      clientUpdatePage.setCodCliInput('codCli'),
      clientUpdatePage.setNomCliInput('nomCli'),
      clientUpdatePage.setMendCliInput('mendCli'),
      clientUpdatePage.sfCliSelectLastOption(),
      clientUpdatePage.setTitreInput('titre'),
      clientUpdatePage.setDatNaiInput('datNai'),
      clientUpdatePage.setLieuNaiInput('lieuNai'),
      clientUpdatePage.setNatCliInput('natCli'),
      clientUpdatePage.setLngCliInput('lngCli'),
      clientUpdatePage.setSocCliInput('socCli'),
      clientUpdatePage.setEmploiInput('emploi'),
      clientUpdatePage.sexeSelectLastOption(),
      clientUpdatePage.setNumCniInput('numCni'),
      clientUpdatePage.setDatCniInput('datCni'),
      clientUpdatePage.setFinCniInput('finCni'),
      clientUpdatePage.setLieuCniInput('lieuCni'),
      clientUpdatePage.setAutoCniInput('autoCni'),
      clientUpdatePage.setAdrInput('adr'),
      clientUpdatePage.setTelInput('tel'),
      clientUpdatePage.setVilleInput('ville'),
      clientUpdatePage.setSiteInput('site'),
      clientUpdatePage.setLocInput('loc'),
      clientUpdatePage.setFaxInput('fax'),
      clientUpdatePage.setAgnceInput('agnce'),
      clientUpdatePage.setMailInput('mail'),
      clientUpdatePage.setPaysInput('pays'),
    ]);

    expect(await clientUpdatePage.getCodBnkInput()).to.eq('codBnk', 'Expected CodBnk value to be equals to codBnk');
    expect(await clientUpdatePage.getCodCliInput()).to.eq('codCli', 'Expected CodCli value to be equals to codCli');
    expect(await clientUpdatePage.getNomCliInput()).to.eq('nomCli', 'Expected NomCli value to be equals to nomCli');
    expect(await clientUpdatePage.getMendCliInput()).to.eq('mendCli', 'Expected MendCli value to be equals to mendCli');
    expect(await clientUpdatePage.getTitreInput()).to.eq('titre', 'Expected Titre value to be equals to titre');
    expect(await clientUpdatePage.getDatNaiInput()).to.eq('datNai', 'Expected DatNai value to be equals to datNai');
    expect(await clientUpdatePage.getLieuNaiInput()).to.eq('lieuNai', 'Expected LieuNai value to be equals to lieuNai');
    expect(await clientUpdatePage.getNatCliInput()).to.eq('natCli', 'Expected NatCli value to be equals to natCli');
    expect(await clientUpdatePage.getLngCliInput()).to.eq('lngCli', 'Expected LngCli value to be equals to lngCli');
    expect(await clientUpdatePage.getSocCliInput()).to.eq('socCli', 'Expected SocCli value to be equals to socCli');
    expect(await clientUpdatePage.getEmploiInput()).to.eq('emploi', 'Expected Emploi value to be equals to emploi');
    expect(await clientUpdatePage.getNumCniInput()).to.eq('numCni', 'Expected NumCni value to be equals to numCni');
    expect(await clientUpdatePage.getDatCniInput()).to.eq('datCni', 'Expected DatCni value to be equals to datCni');
    expect(await clientUpdatePage.getFinCniInput()).to.eq('finCni', 'Expected FinCni value to be equals to finCni');
    expect(await clientUpdatePage.getLieuCniInput()).to.eq('lieuCni', 'Expected LieuCni value to be equals to lieuCni');
    expect(await clientUpdatePage.getAutoCniInput()).to.eq('autoCni', 'Expected AutoCni value to be equals to autoCni');
    expect(await clientUpdatePage.getAdrInput()).to.eq('adr', 'Expected Adr value to be equals to adr');
    expect(await clientUpdatePage.getTelInput()).to.eq('tel', 'Expected Tel value to be equals to tel');
    expect(await clientUpdatePage.getVilleInput()).to.eq('ville', 'Expected Ville value to be equals to ville');
    expect(await clientUpdatePage.getSiteInput()).to.eq('site', 'Expected Site value to be equals to site');
    expect(await clientUpdatePage.getLocInput()).to.eq('loc', 'Expected Loc value to be equals to loc');
    expect(await clientUpdatePage.getFaxInput()).to.eq('fax', 'Expected Fax value to be equals to fax');
    expect(await clientUpdatePage.getAgnceInput()).to.eq('agnce', 'Expected Agnce value to be equals to agnce');
    expect(await clientUpdatePage.getMailInput()).to.eq('mail', 'Expected Mail value to be equals to mail');
    expect(await clientUpdatePage.getPaysInput()).to.eq('pays', 'Expected Pays value to be equals to pays');

    await clientUpdatePage.save();
    expect(await clientUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await clientComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Client', async () => {
    const nbButtonsBeforeDelete = await clientComponentsPage.countDeleteButtons();
    await clientComponentsPage.clickOnLastDeleteButton();

    clientDeleteDialog = new ClientDeleteDialog();
    expect(await clientDeleteDialog.getDialogTitle()).to.eq('bankLoanManagerApp.client.delete.question');
    await clientDeleteDialog.clickOnConfirmButton();

    expect(await clientComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
