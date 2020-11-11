import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BankLoanManagerTestModule } from '../../../test.module';
import { DowngradingUpdateComponent } from 'app/entities/downgrading/downgrading-update.component';
import { DowngradingService } from 'app/entities/downgrading/downgrading.service';
import { Downgrading } from 'app/shared/model/downgrading.model';

describe('Component Tests', () => {
  describe('Downgrading Management Update Component', () => {
    let comp: DowngradingUpdateComponent;
    let fixture: ComponentFixture<DowngradingUpdateComponent>;
    let service: DowngradingService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BankLoanManagerTestModule],
        declarations: [DowngradingUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(DowngradingUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DowngradingUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DowngradingService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Downgrading(123);
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
        const entity = new Downgrading();
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
