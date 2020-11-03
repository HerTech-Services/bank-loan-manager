import { browser, ExpectedConditions as ec /* , protractor, promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  AttachmentComponentsPage,
  /* AttachmentDeleteDialog, */
  AttachmentUpdatePage,
} from './attachment.page-object';

const expect = chai.expect;

describe('Attachment e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let attachmentComponentsPage: AttachmentComponentsPage;
  let attachmentUpdatePage: AttachmentUpdatePage;
  /* let attachmentDeleteDialog: AttachmentDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Attachments', async () => {
    await navBarPage.goToEntity('attachment');
    attachmentComponentsPage = new AttachmentComponentsPage();
    await browser.wait(ec.visibilityOf(attachmentComponentsPage.title), 5000);
    expect(await attachmentComponentsPage.getTitle()).to.eq('bankLoanManagerApp.attachment.home.title');
    await browser.wait(ec.or(ec.visibilityOf(attachmentComponentsPage.entities), ec.visibilityOf(attachmentComponentsPage.noResult)), 1000);
  });

  it('should load create Attachment page', async () => {
    await attachmentComponentsPage.clickOnCreateButton();
    attachmentUpdatePage = new AttachmentUpdatePage();
    expect(await attachmentUpdatePage.getPageTitle()).to.eq('bankLoanManagerApp.attachment.home.createOrEditLabel');
    await attachmentUpdatePage.cancel();
  });

  /* it('should create and save Attachments', async () => {
        const nbButtonsBeforeCreate = await attachmentComponentsPage.countDeleteButtons();

        await attachmentComponentsPage.clickOnCreateButton();

        await promise.all([
            attachmentUpdatePage.setLabelInput('label'),
            attachmentUpdatePage.setFormatInput('format'),
            attachmentUpdatePage.setCreatedDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            attachmentUpdatePage.setUpdatedDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            attachmentUpdatePage.setVersionInput('5'),
            attachmentUpdatePage.setPathInput('path'),
            attachmentUpdatePage.setFilenameInput('filename'),
            attachmentUpdatePage.setFilesizeInput('5'),
            attachmentUpdatePage.setValidationDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            attachmentUpdatePage.engagementSelectLastOption(),
            attachmentUpdatePage.userSelectLastOption(),
            attachmentUpdatePage.updatedBySelectLastOption(),
            attachmentUpdatePage.statusSelectLastOption(),
            // attachmentUpdatePage.originSelectLastOption(),
        ]);

        expect(await attachmentUpdatePage.getLabelInput()).to.eq('label', 'Expected Label value to be equals to label');
        expect(await attachmentUpdatePage.getFormatInput()).to.eq('format', 'Expected Format value to be equals to format');
        expect(await attachmentUpdatePage.getCreatedDateInput()).to.contain('2001-01-01T02:30', 'Expected createdDate value to be equals to 2000-12-31');
        expect(await attachmentUpdatePage.getUpdatedDateInput()).to.contain('2001-01-01T02:30', 'Expected updatedDate value to be equals to 2000-12-31');
        expect(await attachmentUpdatePage.getVersionInput()).to.eq('5', 'Expected version value to be equals to 5');
        expect(await attachmentUpdatePage.getPathInput()).to.eq('path', 'Expected Path value to be equals to path');
        expect(await attachmentUpdatePage.getFilenameInput()).to.eq('filename', 'Expected Filename value to be equals to filename');
        expect(await attachmentUpdatePage.getFilesizeInput()).to.eq('5', 'Expected filesize value to be equals to 5');
        expect(await attachmentUpdatePage.getValidationDateInput()).to.contain('2001-01-01T02:30', 'Expected validationDate value to be equals to 2000-12-31');

        await attachmentUpdatePage.save();
        expect(await attachmentUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await attachmentComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /* it('should delete last Attachment', async () => {
        const nbButtonsBeforeDelete = await attachmentComponentsPage.countDeleteButtons();
        await attachmentComponentsPage.clickOnLastDeleteButton();

        attachmentDeleteDialog = new AttachmentDeleteDialog();
        expect(await attachmentDeleteDialog.getDialogTitle())
            .to.eq('bankLoanManagerApp.attachment.delete.question');
        await attachmentDeleteDialog.clickOnConfirmButton();

        expect(await attachmentComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
