import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { TaskLogComponentsPage, TaskLogDeleteDialog, TaskLogUpdatePage } from './task-log.page-object';

const expect = chai.expect;

describe('TaskLog e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let taskLogComponentsPage: TaskLogComponentsPage;
  let taskLogUpdatePage: TaskLogUpdatePage;
  let taskLogDeleteDialog: TaskLogDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load TaskLogs', async () => {
    await navBarPage.goToEntity('task-log');
    taskLogComponentsPage = new TaskLogComponentsPage();
    await browser.wait(ec.visibilityOf(taskLogComponentsPage.title), 5000);
    expect(await taskLogComponentsPage.getTitle()).to.eq('bankLoanManagerApp.taskLog.home.title');
    await browser.wait(ec.or(ec.visibilityOf(taskLogComponentsPage.entities), ec.visibilityOf(taskLogComponentsPage.noResult)), 1000);
  });

  it('should load create TaskLog page', async () => {
    await taskLogComponentsPage.clickOnCreateButton();
    taskLogUpdatePage = new TaskLogUpdatePage();
    expect(await taskLogUpdatePage.getPageTitle()).to.eq('bankLoanManagerApp.taskLog.home.createOrEditLabel');
    await taskLogUpdatePage.cancel();
  });

  it('should create and save TaskLogs', async () => {
    const nbButtonsBeforeCreate = await taskLogComponentsPage.countDeleteButtons();

    await taskLogComponentsPage.clickOnCreateButton();

    await promise.all([
      taskLogUpdatePage.setCommentInput('comment'),
      taskLogUpdatePage.setCreatedDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      taskLogUpdatePage.setTaskPropertiesInput('taskProperties'),
      taskLogUpdatePage.setEngagementPropertiesInput('engagementProperties'),
      taskLogUpdatePage.userSelectLastOption(),
      taskLogUpdatePage.actionSelectLastOption(),
      taskLogUpdatePage.taskSelectLastOption(),
      taskLogUpdatePage.engagementSelectLastOption(),
    ]);

    expect(await taskLogUpdatePage.getCommentInput()).to.eq('comment', 'Expected Comment value to be equals to comment');
    expect(await taskLogUpdatePage.getCreatedDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected createdDate value to be equals to 2000-12-31'
    );
    expect(await taskLogUpdatePage.getTaskPropertiesInput()).to.eq(
      'taskProperties',
      'Expected TaskProperties value to be equals to taskProperties'
    );
    expect(await taskLogUpdatePage.getEngagementPropertiesInput()).to.eq(
      'engagementProperties',
      'Expected EngagementProperties value to be equals to engagementProperties'
    );

    await taskLogUpdatePage.save();
    expect(await taskLogUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await taskLogComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last TaskLog', async () => {
    const nbButtonsBeforeDelete = await taskLogComponentsPage.countDeleteButtons();
    await taskLogComponentsPage.clickOnLastDeleteButton();

    taskLogDeleteDialog = new TaskLogDeleteDialog();
    expect(await taskLogDeleteDialog.getDialogTitle()).to.eq('bankLoanManagerApp.taskLog.delete.question');
    await taskLogDeleteDialog.clickOnConfirmButton();

    expect(await taskLogComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
