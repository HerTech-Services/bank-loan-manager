import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BankLoanManagerTestModule } from '../../../test.module';
import { EngagementUpdateComponent } from 'app/entities/engagement/engagement-update.component';
import { EngagementService } from 'app/entities/engagement/engagement.service';
import { Engagement } from 'app/shared/model/engagement.model';

describe('Component Tests', () => {
  describe('Engagement Management Update Component', () => {
    let comp: EngagementUpdateComponent;
    let fixture: ComponentFixture<EngagementUpdateComponent>;
    let service: EngagementService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BankLoanManagerTestModule],
        declarations: [EngagementUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(EngagementUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EngagementUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EngagementService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Engagement(123);
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
        const entity = new Engagement();
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
