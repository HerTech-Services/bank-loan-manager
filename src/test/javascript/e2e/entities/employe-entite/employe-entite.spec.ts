import { browser, ExpectedConditions as ec /* , promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  EmployeEntiteComponentsPage,
  /* EmployeEntiteDeleteDialog, */
  EmployeEntiteUpdatePage,
} from './employe-entite.page-object';

const expect = chai.expect;

describe('EmployeEntite e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let employeEntiteComponentsPage: EmployeEntiteComponentsPage;
  let employeEntiteUpdatePage: EmployeEntiteUpdatePage;
  /* let employeEntiteDeleteDialog: EmployeEntiteDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load EmployeEntites', async () => {
    await navBarPage.goToEntity('employe-entite');
    employeEntiteComponentsPage = new EmployeEntiteComponentsPage();
    await browser.wait(ec.visibilityOf(employeEntiteComponentsPage.title), 5000);
    expect(await employeEntiteComponentsPage.getTitle()).to.eq('bankLoanManagerApp.employeEntite.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(employeEntiteComponentsPage.entities), ec.visibilityOf(employeEntiteComponentsPage.noResult)),
      1000
    );
  });

  it('should load create EmployeEntite page', async () => {
    await employeEntiteComponentsPage.clickOnCreateButton();
    employeEntiteUpdatePage = new EmployeEntiteUpdatePage();
    expect(await employeEntiteUpdatePage.getPageTitle()).to.eq('bankLoanManagerApp.employeEntite.home.createOrEditLabel');
    await employeEntiteUpdatePage.cancel();
  });

  /* it('should create and save EmployeEntites', async () => {
        const nbButtonsBeforeCreate = await employeEntiteComponentsPage.countDeleteButtons();

        await employeEntiteComponentsPage.clickOnCreateButton();

        await promise.all([
            employeEntiteUpdatePage.setRoleInput('role'),
            employeEntiteUpdatePage.employeSelectLastOption(),
            employeEntiteUpdatePage.entiteSelectLastOption(),
        ]);

        expect(await employeEntiteUpdatePage.getRoleInput()).to.eq('role', 'Expected Role value to be equals to role');
        const selectedIsPrimary = employeEntiteUpdatePage.getIsPrimaryInput();
        if (await selectedIsPrimary.isSelected()) {
            await employeEntiteUpdatePage.getIsPrimaryInput().click();
            expect(await employeEntiteUpdatePage.getIsPrimaryInput().isSelected(), 'Expected isPrimary not to be selected').to.be.false;
        } else {
            await employeEntiteUpdatePage.getIsPrimaryInput().click();
            expect(await employeEntiteUpdatePage.getIsPrimaryInput().isSelected(), 'Expected isPrimary to be selected').to.be.true;
        }
        const selectedIsChief = employeEntiteUpdatePage.getIsChiefInput();
        if (await selectedIsChief.isSelected()) {
            await employeEntiteUpdatePage.getIsChiefInput().click();
            expect(await employeEntiteUpdatePage.getIsChiefInput().isSelected(), 'Expected isChief not to be selected').to.be.false;
        } else {
            await employeEntiteUpdatePage.getIsChiefInput().click();
            expect(await employeEntiteUpdatePage.getIsChiefInput().isSelected(), 'Expected isChief to be selected').to.be.true;
        }

        await employeEntiteUpdatePage.save();
        expect(await employeEntiteUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await employeEntiteComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /* it('should delete last EmployeEntite', async () => {
        const nbButtonsBeforeDelete = await employeEntiteComponentsPage.countDeleteButtons();
        await employeEntiteComponentsPage.clickOnLastDeleteButton();

        employeEntiteDeleteDialog = new EmployeEntiteDeleteDialog();
        expect(await employeEntiteDeleteDialog.getDialogTitle())
            .to.eq('bankLoanManagerApp.employeEntite.delete.question');
        await employeEntiteDeleteDialog.clickOnConfirmButton();

        expect(await employeEntiteComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
