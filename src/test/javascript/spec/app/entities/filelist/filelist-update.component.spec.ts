import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BankLoanManagerTestModule } from '../../../test.module';
import { FilelistUpdateComponent } from 'app/entities/filelist/filelist-update.component';
import { FilelistService } from 'app/entities/filelist/filelist.service';
import { Filelist } from 'app/shared/model/filelist.model';

describe('Component Tests', () => {
  describe('Filelist Management Update Component', () => {
    let comp: FilelistUpdateComponent;
    let fixture: ComponentFixture<FilelistUpdateComponent>;
    let service: FilelistService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BankLoanManagerTestModule],
        declarations: [FilelistUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(FilelistUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FilelistUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FilelistService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Filelist(123);
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
        const entity = new Filelist();
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
