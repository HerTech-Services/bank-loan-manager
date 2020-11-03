import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { BankLoanManagerTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { TasktypeStatusActionDeleteDialogComponent } from 'app/entities/tasktype-status-action/tasktype-status-action-delete-dialog.component';
import { TasktypeStatusActionService } from 'app/entities/tasktype-status-action/tasktype-status-action.service';

describe('Component Tests', () => {
  describe('TasktypeStatusAction Management Delete Component', () => {
    let comp: TasktypeStatusActionDeleteDialogComponent;
    let fixture: ComponentFixture<TasktypeStatusActionDeleteDialogComponent>;
    let service: TasktypeStatusActionService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BankLoanManagerTestModule],
        declarations: [TasktypeStatusActionDeleteDialogComponent],
      })
        .overrideTemplate(TasktypeStatusActionDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TasktypeStatusActionDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TasktypeStatusActionService);
      mockEventManager = TestBed.get(JhiEventManager);
      mockActiveModal = TestBed.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.closeSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));

      it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.cancel();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
      });
    });
  });
});
