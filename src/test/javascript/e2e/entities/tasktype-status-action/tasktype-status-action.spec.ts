import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  TasktypeStatusActionComponentsPage,
  TasktypeStatusActionDeleteDialog,
  TasktypeStatusActionUpdatePage,
} from './tasktype-status-action.page-object';

const expect = chai.expect;

describe('TasktypeStatusAction e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let tasktypeStatusActionComponentsPage: TasktypeStatusActionComponentsPage;
  let tasktypeStatusActionUpdatePage: TasktypeStatusActionUpdatePage;
  let tasktypeStatusActionDeleteDialog: TasktypeStatusActionDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load TasktypeStatusActions', async () => {
    await navBarPage.goToEntity('tasktype-status-action');
    tasktypeStatusActionComponentsPage = new TasktypeStatusActionComponentsPage();
    await browser.wait(ec.visibilityOf(tasktypeStatusActionComponentsPage.title), 5000);
    expect(await tasktypeStatusActionComponentsPage.getTitle()).to.eq('bankLoanManagerApp.tasktypeStatusAction.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(tasktypeStatusActionComponentsPage.entities), ec.visibilityOf(tasktypeStatusActionComponentsPage.noResult)),
      1000
    );
  });

  it('should load create TasktypeStatusAction page', async () => {
    await tasktypeStatusActionComponentsPage.clickOnCreateButton();
    tasktypeStatusActionUpdatePage = new TasktypeStatusActionUpdatePage();
    expect(await tasktypeStatusActionUpdatePage.getPageTitle()).to.eq('bankLoanManagerApp.tasktypeStatusAction.home.createOrEditLabel');
    await tasktypeStatusActionUpdatePage.cancel();
  });

  it('should create and save TasktypeStatusActions', async () => {
    const nbButtonsBeforeCreate = await tasktypeStatusActionComponentsPage.countDeleteButtons();

    await tasktypeStatusActionComponentsPage.clickOnCreateButton();

    await promise.all([
      tasktypeStatusActionUpdatePage.tasktypeSelectLastOption(),
      tasktypeStatusActionUpdatePage.actionSelectLastOption(),
      tasktypeStatusActionUpdatePage.statusSelectLastOption(),
    ]);

    await tasktypeStatusActionUpdatePage.save();
    expect(await tasktypeStatusActionUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await tasktypeStatusActionComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last TasktypeStatusAction', async () => {
    const nbButtonsBeforeDelete = await tasktypeStatusActionComponentsPage.countDeleteButtons();
    await tasktypeStatusActionComponentsPage.clickOnLastDeleteButton();

    tasktypeStatusActionDeleteDialog = new TasktypeStatusActionDeleteDialog();
    expect(await tasktypeStatusActionDeleteDialog.getDialogTitle()).to.eq('bankLoanManagerApp.tasktypeStatusAction.delete.question');
    await tasktypeStatusActionDeleteDialog.clickOnConfirmButton();

    expect(await tasktypeStatusActionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
