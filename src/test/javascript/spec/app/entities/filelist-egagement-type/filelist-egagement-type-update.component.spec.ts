import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BankLoanManagerTestModule } from '../../../test.module';
import { FilelistEgagementTypeUpdateComponent } from 'app/entities/filelist-egagement-type/filelist-egagement-type-update.component';
import { FilelistEgagementTypeService } from 'app/entities/filelist-egagement-type/filelist-egagement-type.service';
import { FilelistEgagementType } from 'app/shared/model/filelist-egagement-type.model';

describe('Component Tests', () => {
  describe('FilelistEgagementType Management Update Component', () => {
    let comp: FilelistEgagementTypeUpdateComponent;
    let fixture: ComponentFixture<FilelistEgagementTypeUpdateComponent>;
    let service: FilelistEgagementTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BankLoanManagerTestModule],
        declarations: [FilelistEgagementTypeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(FilelistEgagementTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FilelistEgagementTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FilelistEgagementTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new FilelistEgagementType(123);
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
        const entity = new FilelistEgagementType();
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
