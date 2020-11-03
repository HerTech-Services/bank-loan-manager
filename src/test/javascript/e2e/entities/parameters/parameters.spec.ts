import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ParametersComponentsPage, ParametersDeleteDialog, ParametersUpdatePage } from './parameters.page-object';

const expect = chai.expect;

describe('Parameters e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let parametersComponentsPage: ParametersComponentsPage;
  let parametersUpdatePage: ParametersUpdatePage;
  let parametersDeleteDialog: ParametersDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Parameters', async () => {
    await navBarPage.goToEntity('parameters');
    parametersComponentsPage = new ParametersComponentsPage();
    await browser.wait(ec.visibilityOf(parametersComponentsPage.title), 5000);
    expect(await parametersComponentsPage.getTitle()).to.eq('bankLoanManagerApp.parameters.home.title');
    await browser.wait(ec.or(ec.visibilityOf(parametersComponentsPage.entities), ec.visibilityOf(parametersComponentsPage.noResult)), 1000);
  });

  it('should load create Parameters page', async () => {
    await parametersComponentsPage.clickOnCreateButton();
    parametersUpdatePage = new ParametersUpdatePage();
    expect(await parametersUpdatePage.getPageTitle()).to.eq('bankLoanManagerApp.parameters.home.createOrEditLabel');
    await parametersUpdatePage.cancel();
  });

  it('should create and save Parameters', async () => {
    const nbButtonsBeforeCreate = await parametersComponentsPage.countDeleteButtons();

    await parametersComponentsPage.clickOnCreateButton();

    await promise.all([
      parametersUpdatePage.setIdentifierInput('identifier'),
      parametersUpdatePage.setDescriptionInput('description'),
      parametersUpdatePage.setParamValueStringInput('paramValueString'),
      parametersUpdatePage.setParamValueIntInput('5'),
      parametersUpdatePage.setParamValueDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      parametersUpdatePage.setUpdatedDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
    ]);

    expect(await parametersUpdatePage.getIdentifierInput()).to.eq('identifier', 'Expected Identifier value to be equals to identifier');
    expect(await parametersUpdatePage.getDescriptionInput()).to.eq('description', 'Expected Description value to be equals to description');
    expect(await parametersUpdatePage.getParamValueStringInput()).to.eq(
      'paramValueString',
      'Expected ParamValueString value to be equals to paramValueString'
    );
    expect(await parametersUpdatePage.getParamValueIntInput()).to.eq('5', 'Expected paramValueInt value to be equals to 5');
    expect(await parametersUpdatePage.getParamValueDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected paramValueDate value to be equals to 2000-12-31'
    );
    expect(await parametersUpdatePage.getUpdatedDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected updatedDate value to be equals to 2000-12-31'
    );

    await parametersUpdatePage.save();
    expect(await parametersUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await parametersComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Parameters', async () => {
    const nbButtonsBeforeDelete = await parametersComponentsPage.countDeleteButtons();
    await parametersComponentsPage.clickOnLastDeleteButton();

    parametersDeleteDialog = new ParametersDeleteDialog();
    expect(await parametersDeleteDialog.getDialogTitle()).to.eq('bankLoanManagerApp.parameters.delete.question');
    await parametersDeleteDialog.clickOnConfirmButton();

    expect(await parametersComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
