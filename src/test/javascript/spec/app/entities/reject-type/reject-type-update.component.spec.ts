import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BankLoanManagerTestModule } from '../../../test.module';
import { RejectTypeUpdateComponent } from 'app/entities/reject-type/reject-type-update.component';
import { RejectTypeService } from 'app/entities/reject-type/reject-type.service';
import { RejectType } from 'app/shared/model/reject-type.model';

describe('Component Tests', () => {
  describe('RejectType Management Update Component', () => {
    let comp: RejectTypeUpdateComponent;
    let fixture: ComponentFixture<RejectTypeUpdateComponent>;
    let service: RejectTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BankLoanManagerTestModule],
        declarations: [RejectTypeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(RejectTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RejectTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RejectTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new RejectType(123);
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
        const entity = new RejectType();
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
