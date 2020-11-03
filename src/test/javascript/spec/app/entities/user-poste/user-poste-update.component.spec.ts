import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BankLoanManagerTestModule } from '../../../test.module';
import { UserPosteUpdateComponent } from 'app/entities/user-poste/user-poste-update.component';
import { UserPosteService } from 'app/entities/user-poste/user-poste.service';
import { UserPoste } from 'app/shared/model/user-poste.model';

describe('Component Tests', () => {
  describe('UserPoste Management Update Component', () => {
    let comp: UserPosteUpdateComponent;
    let fixture: ComponentFixture<UserPosteUpdateComponent>;
    let service: UserPosteService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BankLoanManagerTestModule],
        declarations: [UserPosteUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(UserPosteUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserPosteUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserPosteService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new UserPoste(123);
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
        const entity = new UserPoste();
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
