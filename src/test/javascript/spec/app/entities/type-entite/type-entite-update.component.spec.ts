import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BankLoanManagerTestModule } from '../../../test.module';
import { TypeEntiteUpdateComponent } from 'app/admin/type-entite/type-entite-update.component';
import { TypeEntiteService } from 'app/admin/type-entite/type-entite.service';
import { TypeEntite } from 'app/shared/model/type-entite.model';

describe('Component Tests', () => {
  describe('TypeEntite Management Update Component', () => {
    let comp: TypeEntiteUpdateComponent;
    let fixture: ComponentFixture<TypeEntiteUpdateComponent>;
    let service: TypeEntiteService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BankLoanManagerTestModule],
        declarations: [TypeEntiteUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TypeEntiteUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TypeEntiteUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TypeEntiteService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TypeEntite(123);
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
        const entity = new TypeEntite();
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
