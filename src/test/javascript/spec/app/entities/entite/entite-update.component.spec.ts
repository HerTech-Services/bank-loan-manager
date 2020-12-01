import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BankLoanManagerTestModule } from '../../../test.module';
import { EntiteUpdateComponent } from 'app/admin/entite/entite-update.component';
import { EntiteService } from 'app/admin/entite/entite.service';
import { Entite } from 'app/shared/model/entite.model';

describe('Component Tests', () => {
  describe('Entite Management Update Component', () => {
    let comp: EntiteUpdateComponent;
    let fixture: ComponentFixture<EntiteUpdateComponent>;
    let service: EntiteService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BankLoanManagerTestModule],
        declarations: [EntiteUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(EntiteUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EntiteUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EntiteService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Entite(123);
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
        const entity = new Entite();
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
