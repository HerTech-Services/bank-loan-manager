import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { TaskTypeComponentsPage, TaskTypeDeleteDialog, TaskTypeUpdatePage } from './task-type.page-object';

const expect = chai.expect;

describe('TaskType e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let taskTypeComponentsPage: TaskTypeComponentsPage;
  let taskTypeUpdatePage: TaskTypeUpdatePage;
  let taskTypeDeleteDialog: TaskTypeDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load TaskTypes', async () => {
    await navBarPage.goToEntity('task-type');
    taskTypeComponentsPage = new TaskTypeComponentsPage();
    await browser.wait(ec.visibilityOf(taskTypeComponentsPage.title), 5000);
    expect(await taskTypeComponentsPage.getTitle()).to.eq('bankLoanManagerApp.taskType.home.title');
    await browser.wait(ec.or(ec.visibilityOf(taskTypeComponentsPage.entities), ec.visibilityOf(taskTypeComponentsPage.noResult)), 1000);
  });

  it('should load create TaskType page', async () => {
    await taskTypeComponentsPage.clickOnCreateButton();
    taskTypeUpdatePage = new TaskTypeUpdatePage();
    expect(await taskTypeUpdatePage.getPageTitle()).to.eq('bankLoanManagerApp.taskType.home.createOrEditLabel');
    await taskTypeUpdatePage.cancel();
  });

  it('should create and save TaskTypes', async () => {
    const nbButtonsBeforeCreate = await taskTypeComponentsPage.countDeleteButtons();

    await taskTypeComponentsPage.clickOnCreateButton();

    await promise.all([
      taskTypeUpdatePage.setLabelInput('label'),
      taskTypeUpdatePage.setIdentifierInput('identifier'),
      taskTypeUpdatePage.setDelayInput('5'),
    ]);

    expect(await taskTypeUpdatePage.getLabelInput()).to.eq('label', 'Expected Label value to be equals to label');
    expect(await taskTypeUpdatePage.getIdentifierInput()).to.eq('identifier', 'Expected Identifier value to be equals to identifier');
    expect(await taskTypeUpdatePage.getDelayInput()).to.eq('5', 'Expected delay value to be equals to 5');
    const selectedIsEnabled = taskTypeUpdatePage.getIsEnabledInput();
    if (await selectedIsEnabled.isSelected()) {
      await taskTypeUpdatePage.getIsEnabledInput().click();
      expect(await taskTypeUpdatePage.getIsEnabledInput().isSelected(), 'Expected isEnabled not to be selected').to.be.false;
    } else {
      await taskTypeUpdatePage.getIsEnabledInput().click();
      expect(await taskTypeUpdatePage.getIsEnabledInput().isSelected(), 'Expected isEnabled to be selected').to.be.true;
    }

    await taskTypeUpdatePage.save();
    expect(await taskTypeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await taskTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last TaskType', async () => {
    const nbButtonsBeforeDelete = await taskTypeComponentsPage.countDeleteButtons();
    await taskTypeComponentsPage.clickOnLastDeleteButton();

    taskTypeDeleteDialog = new TaskTypeDeleteDialog();
    expect(await taskTypeDeleteDialog.getDialogTitle()).to.eq('bankLoanManagerApp.taskType.delete.question');
    await taskTypeDeleteDialog.clickOnConfirmButton();

    expect(await taskTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
