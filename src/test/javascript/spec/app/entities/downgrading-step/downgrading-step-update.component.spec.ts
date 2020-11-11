import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BankLoanManagerTestModule } from '../../../test.module';
import { DowngradingStepUpdateComponent } from 'app/entities/downgrading-step/downgrading-step-update.component';
import { DowngradingStepService } from 'app/entities/downgrading-step/downgrading-step.service';
import { DowngradingStep } from 'app/shared/model/downgrading-step.model';

describe('Component Tests', () => {
  describe('DowngradingStep Management Update Component', () => {
    let comp: DowngradingStepUpdateComponent;
    let fixture: ComponentFixture<DowngradingStepUpdateComponent>;
    let service: DowngradingStepService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BankLoanManagerTestModule],
        declarations: [DowngradingStepUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(DowngradingStepUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DowngradingStepUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DowngradingStepService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DowngradingStep(123);
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
        const entity = new DowngradingStep();
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
