import { browser, ExpectedConditions as ec /* , protractor, promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  TaskComponentsPage,
  /* TaskDeleteDialog, */
  TaskUpdatePage,
} from './task.page-object';

const expect = chai.expect;

describe('Task e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let taskComponentsPage: TaskComponentsPage;
  let taskUpdatePage: TaskUpdatePage;
  /* let taskDeleteDialog: TaskDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Tasks', async () => {
    await navBarPage.goToEntity('task');
    taskComponentsPage = new TaskComponentsPage();
    await browser.wait(ec.visibilityOf(taskComponentsPage.title), 5000);
    expect(await taskComponentsPage.getTitle()).to.eq('bankLoanManagerApp.task.home.title');
    await browser.wait(ec.or(ec.visibilityOf(taskComponentsPage.entities), ec.visibilityOf(taskComponentsPage.noResult)), 1000);
  });

  it('should load create Task page', async () => {
    await taskComponentsPage.clickOnCreateButton();
    taskUpdatePage = new TaskUpdatePage();
    expect(await taskUpdatePage.getPageTitle()).to.eq('bankLoanManagerApp.task.home.createOrEditLabel');
    await taskUpdatePage.cancel();
  });

  /* it('should create and save Tasks', async () => {
        const nbButtonsBeforeCreate = await taskComponentsPage.countDeleteButtons();

        await taskComponentsPage.clickOnCreateButton();

        await promise.all([
            taskUpdatePage.setSequenceInput('5'),
            taskUpdatePage.setProcessDelayInput('5'),
            taskUpdatePage.setDelay1Input('5'),
            taskUpdatePage.setDelay2Input('5'),
            taskUpdatePage.setViewedInput('5'),
            taskUpdatePage.setCreatedDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            taskUpdatePage.setProcessDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            taskUpdatePage.setProcessCommentInput('processComment'),
            taskUpdatePage.typeSelectLastOption(),
            taskUpdatePage.assigneeSelectLastOption(),
            taskUpdatePage.engagementSelectLastOption(),
        ]);

        expect(await taskUpdatePage.getSequenceInput()).to.eq('5', 'Expected sequence value to be equals to 5');
        const selectedIsSystem = taskUpdatePage.getIsSystemInput();
        if (await selectedIsSystem.isSelected()) {
            await taskUpdatePage.getIsSystemInput().click();
            expect(await taskUpdatePage.getIsSystemInput().isSelected(), 'Expected isSystem not to be selected').to.be.false;
        } else {
            await taskUpdatePage.getIsSystemInput().click();
            expect(await taskUpdatePage.getIsSystemInput().isSelected(), 'Expected isSystem to be selected').to.be.true;
        }
        expect(await taskUpdatePage.getProcessDelayInput()).to.eq('5', 'Expected processDelay value to be equals to 5');
        expect(await taskUpdatePage.getDelay1Input()).to.eq('5', 'Expected delay1 value to be equals to 5');
        expect(await taskUpdatePage.getDelay2Input()).to.eq('5', 'Expected delay2 value to be equals to 5');
        expect(await taskUpdatePage.getViewedInput()).to.eq('5', 'Expected viewed value to be equals to 5');
        expect(await taskUpdatePage.getCreatedDateInput()).to.contain('2001-01-01T02:30', 'Expected createdDate value to be equals to 2000-12-31');
        expect(await taskUpdatePage.getProcessDateInput()).to.contain('2001-01-01T02:30', 'Expected processDate value to be equals to 2000-12-31');
        expect(await taskUpdatePage.getProcessCommentInput()).to.eq('processComment', 'Expected ProcessComment value to be equals to processComment');

        await taskUpdatePage.save();
        expect(await taskUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await taskComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /* it('should delete last Task', async () => {
        const nbButtonsBeforeDelete = await taskComponentsPage.countDeleteButtons();
        await taskComponentsPage.clickOnLastDeleteButton();

        taskDeleteDialog = new TaskDeleteDialog();
        expect(await taskDeleteDialog.getDialogTitle())
            .to.eq('bankLoanManagerApp.task.delete.question');
        await taskDeleteDialog.clickOnConfirmButton();

        expect(await taskComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
