import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BankLoanManagerTestModule } from '../../../test.module';
import { TasktypeStatusActionUpdateComponent } from 'app/entities/tasktype-status-action/tasktype-status-action-update.component';
import { TasktypeStatusActionService } from 'app/entities/tasktype-status-action/tasktype-status-action.service';
import { TasktypeStatusAction } from 'app/shared/model/tasktype-status-action.model';

describe('Component Tests', () => {
  describe('TasktypeStatusAction Management Update Component', () => {
    let comp: TasktypeStatusActionUpdateComponent;
    let fixture: ComponentFixture<TasktypeStatusActionUpdateComponent>;
    let service: TasktypeStatusActionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BankLoanManagerTestModule],
        declarations: [TasktypeStatusActionUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TasktypeStatusActionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TasktypeStatusActionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TasktypeStatusActionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TasktypeStatusAction(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new TasktypeStatusAction();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
