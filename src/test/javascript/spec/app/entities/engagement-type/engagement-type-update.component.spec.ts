import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BankLoanManagerTestModule } from '../../../test.module';
import { EngagementTypeUpdateComponent } from 'app/entities/engagement-type/engagement-type-update.component';
import { EngagementTypeService } from 'app/entities/engagement-type/engagement-type.service';
import { EngagementType } from 'app/shared/model/engagement-type.model';

describe('Component Tests', () => {
  describe('EngagementType Management Update Component', () => {
    let comp: EngagementTypeUpdateComponent;
    let fixture: ComponentFixture<EngagementTypeUpdateComponent>;
    let service: EngagementTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BankLoanManagerTestModule],
        declarations: [EngagementTypeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(EngagementTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EngagementTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EngagementTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EngagementType(123);
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
        const entity = new EngagementType();
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
