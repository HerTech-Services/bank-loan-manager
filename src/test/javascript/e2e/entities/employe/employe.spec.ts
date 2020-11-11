import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { EmployeComponentsPage, EmployeDeleteDialog, EmployeUpdatePage } from './employe.page-object';

const expect = chai.expect;

describe('Employe e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let employeComponentsPage: EmployeComponentsPage;
  let employeUpdatePage: EmployeUpdatePage;
  let employeDeleteDialog: EmployeDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Employes', async () => {
    await navBarPage.goToEntity('employe');
    employeComponentsPage = new EmployeComponentsPage();
    await browser.wait(ec.visibilityOf(employeComponentsPage.title), 5000);
    expect(await employeComponentsPage.getTitle()).to.eq('bankLoanManagerApp.employe.home.title');
    await browser.wait(ec.or(ec.visibilityOf(employeComponentsPage.entities), ec.visibilityOf(employeComponentsPage.noResult)), 1000);
  });

  it('should load create Employe page', async () => {
    await employeComponentsPage.clickOnCreateButton();
    employeUpdatePage = new EmployeUpdatePage();
    expect(await employeUpdatePage.getPageTitle()).to.eq('bankLoanManagerApp.employe.home.createOrEditLabel');
    await employeUpdatePage.cancel();
  });

  it('should create and save Employes', async () => {
    const nbButtonsBeforeCreate = await employeComponentsPage.countDeleteButtons();

    await employeComponentsPage.clickOnCreateButton();

    await promise.all([
      employeUpdatePage.setCodBnkInput('codBnk'),
      employeUpdatePage.setCodEmpInput('codEmp'),
      employeUpdatePage.setRsEmpInput('rsEmp'),
      employeUpdatePage.setNomEmpInput('nomEmp'),
      employeUpdatePage.setPrenomEmpInput('prenomEmp'),
      employeUpdatePage.setFctEmpInput('fctEmp'),
      employeUpdatePage.setAdrEmpInput('adrEmp'),
      employeUpdatePage.setTeEmpInput('teEmp'),
      employeUpdatePage.setTypEnmpInput('typEnmp'),
      employeUpdatePage.setNumMatInput('numMat'),
      employeUpdatePage.userSelectLastOption(),
    ]);

    expect(await employeUpdatePage.getCodBnkInput()).to.eq('codBnk', 'Expected CodBnk value to be equals to codBnk');
    expect(await employeUpdatePage.getCodEmpInput()).to.eq('codEmp', 'Expected CodEmp value to be equals to codEmp');
    expect(await employeUpdatePage.getRsEmpInput()).to.eq('rsEmp', 'Expected RsEmp value to be equals to rsEmp');
    expect(await employeUpdatePage.getNomEmpInput()).to.eq('nomEmp', 'Expected NomEmp value to be equals to nomEmp');
    expect(await employeUpdatePage.getPrenomEmpInput()).to.eq('prenomEmp', 'Expected PrenomEmp value to be equals to prenomEmp');
    expect(await employeUpdatePage.getFctEmpInput()).to.eq('fctEmp', 'Expected FctEmp value to be equals to fctEmp');
    expect(await employeUpdatePage.getAdrEmpInput()).to.eq('adrEmp', 'Expected AdrEmp value to be equals to adrEmp');
    expect(await employeUpdatePage.getTeEmpInput()).to.eq('teEmp', 'Expected TeEmp value to be equals to teEmp');
    expect(await employeUpdatePage.getTypEnmpInput()).to.eq('typEnmp', 'Expected TypEnmp value to be equals to typEnmp');
    expect(await employeUpdatePage.getNumMatInput()).to.eq('numMat', 'Expected NumMat value to be equals to numMat');

    await employeUpdatePage.save();
    expect(await employeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await employeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Employe', async () => {
    const nbButtonsBeforeDelete = await employeComponentsPage.countDeleteButtons();
    await employeComponentsPage.clickOnLastDeleteButton();

    employeDeleteDialog = new EmployeDeleteDialog();
    expect(await employeDeleteDialog.getDialogTitle()).to.eq('bankLoanManagerApp.employe.delete.question');
    await employeDeleteDialog.clickOnConfirmButton();

    expect(await employeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
