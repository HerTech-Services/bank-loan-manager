import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BankLoanManagerTestModule } from '../../../test.module';
import { TaskLogUpdateComponent } from 'app/entities/task-log/task-log-update.component';
import { TaskLogService } from 'app/entities/task-log/task-log.service';
import { TaskLog } from 'app/shared/model/task-log.model';

describe('Component Tests', () => {
  describe('TaskLog Management Update Component', () => {
    let comp: TaskLogUpdateComponent;
    let fixture: ComponentFixture<TaskLogUpdateComponent>;
    let service: TaskLogService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BankLoanManagerTestModule],
        declarations: [TaskLogUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TaskLogUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TaskLogUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TaskLogService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TaskLog(123);
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
        const entity = new TaskLog();
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
