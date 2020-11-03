import { browser, ExpectedConditions as ec /* , promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  UserPosteComponentsPage,
  /* UserPosteDeleteDialog, */
  UserPosteUpdatePage,
} from './user-poste.page-object';

const expect = chai.expect;

describe('UserPoste e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let userPosteComponentsPage: UserPosteComponentsPage;
  let userPosteUpdatePage: UserPosteUpdatePage;
  /* let userPosteDeleteDialog: UserPosteDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load UserPostes', async () => {
    await navBarPage.goToEntity('user-poste');
    userPosteComponentsPage = new UserPosteComponentsPage();
    await browser.wait(ec.visibilityOf(userPosteComponentsPage.title), 5000);
    expect(await userPosteComponentsPage.getTitle()).to.eq('bankLoanManagerApp.userPoste.home.title');
    await browser.wait(ec.or(ec.visibilityOf(userPosteComponentsPage.entities), ec.visibilityOf(userPosteComponentsPage.noResult)), 1000);
  });

  it('should load create UserPoste page', async () => {
    await userPosteComponentsPage.clickOnCreateButton();
    userPosteUpdatePage = new UserPosteUpdatePage();
    expect(await userPosteUpdatePage.getPageTitle()).to.eq('bankLoanManagerApp.userPoste.home.createOrEditLabel');
    await userPosteUpdatePage.cancel();
  });

  /* it('should create and save UserPostes', async () => {
        const nbButtonsBeforeCreate = await userPosteComponentsPage.countDeleteButtons();

        await userPosteComponentsPage.clickOnCreateButton();

        await promise.all([
            userPosteUpdatePage.setRoleInput('role'),
            userPosteUpdatePage.userSelectLastOption(),
            userPosteUpdatePage.entitiesSelectLastOption(),
        ]);

        expect(await userPosteUpdatePage.getRoleInput()).to.eq('role', 'Expected Role value to be equals to role');
        const selectedIsPrimary = userPosteUpdatePage.getIsPrimaryInput();
        if (await selectedIsPrimary.isSelected()) {
            await userPosteUpdatePage.getIsPrimaryInput().click();
            expect(await userPosteUpdatePage.getIsPrimaryInput().isSelected(), 'Expected isPrimary not to be selected').to.be.false;
        } else {
            await userPosteUpdatePage.getIsPrimaryInput().click();
            expect(await userPosteUpdatePage.getIsPrimaryInput().isSelected(), 'Expected isPrimary to be selected').to.be.true;
        }

        await userPosteUpdatePage.save();
        expect(await userPosteUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await userPosteComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /* it('should delete last UserPoste', async () => {
        const nbButtonsBeforeDelete = await userPosteComponentsPage.countDeleteButtons();
        await userPosteComponentsPage.clickOnLastDeleteButton();

        userPosteDeleteDialog = new UserPosteDeleteDialog();
        expect(await userPosteDeleteDialog.getDialogTitle())
            .to.eq('bankLoanManagerApp.userPoste.delete.question');
        await userPosteDeleteDialog.clickOnConfirmButton();

        expect(await userPosteComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
