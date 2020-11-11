import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, convertToParamMap } from '@angular/router';

import { BankLoanManagerTestModule } from '../../../test.module';
import { FilelistEgagementTypeComponent } from 'app/entities/filelist-egagement-type/filelist-egagement-type.component';
import { FilelistEgagementTypeService } from 'app/entities/filelist-egagement-type/filelist-egagement-type.service';
import { FilelistEgagementType } from 'app/shared/model/filelist-egagement-type.model';

describe('Component Tests', () => {
  describe('FilelistEgagementType Management Component', () => {
    let comp: FilelistEgagementTypeComponent;
    let fixture: ComponentFixture<FilelistEgagementTypeComponent>;
    let service: FilelistEgagementTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BankLoanManagerTestModule],
        declarations: [FilelistEgagementTypeComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: {
              data: of({
                defaultSort: 'id,asc',
              }),
              queryParamMap: of(
                convertToParamMap({
                  page: '1',
                  size: '1',
                  sort: 'id,desc',
                })
              ),
            },
          },
        ],
      })
        .overrideTemplate(FilelistEgagementTypeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FilelistEgagementTypeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FilelistEgagementTypeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new FilelistEgagementType(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.filelistEgagementTypes && comp.filelistEgagementTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new FilelistEgagementType(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.filelistEgagementTypes && comp.filelistEgagementTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      comp.ngOnInit();
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,desc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // INIT
      comp.ngOnInit();

      // GIVEN
      comp.predicate = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,desc', 'id']);
    });
  });
});
