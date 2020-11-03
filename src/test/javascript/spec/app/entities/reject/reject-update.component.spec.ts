import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BankLoanManagerTestModule } from '../../../test.module';
import { RejectUpdateComponent } from 'app/entities/reject/reject-update.component';
import { RejectService } from 'app/entities/reject/reject.service';
import { Reject } from 'app/shared/model/reject.model';

describe('Component Tests', () => {
  describe('Reject Management Update Component', () => {
    let comp: RejectUpdateComponent;
    let fixture: ComponentFixture<RejectUpdateComponent>;
    let service: RejectService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BankLoanManagerTestModule],
        declarations: [RejectUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(RejectUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RejectUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RejectService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Reject(123);
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
        const entity = new Reject();
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
