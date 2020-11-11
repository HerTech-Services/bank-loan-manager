import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BankLoanManagerTestModule } from '../../../test.module';
import { EmployeEntiteUpdateComponent } from 'app/entities/employe-entite/employe-entite-update.component';
import { EmployeEntiteService } from 'app/entities/employe-entite/employe-entite.service';
import { EmployeEntite } from 'app/shared/model/employe-entite.model';

describe('Component Tests', () => {
  describe('EmployeEntite Management Update Component', () => {
    let comp: EmployeEntiteUpdateComponent;
    let fixture: ComponentFixture<EmployeEntiteUpdateComponent>;
    let service: EmployeEntiteService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BankLoanManagerTestModule],
        declarations: [EmployeEntiteUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(EmployeEntiteUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EmployeEntiteUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EmployeEntiteService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EmployeEntite(123);
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
        const entity = new EmployeEntite();
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
