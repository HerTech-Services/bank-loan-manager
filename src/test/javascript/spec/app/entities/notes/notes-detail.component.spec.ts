import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { BankLoanManagerTestModule } from '../../../test.module';
import { NotesDetailComponent } from 'app/entities/notes/notes-detail.component';
import { Notes } from 'app/shared/model/notes.model';

describe('Component Tests', () => {
  describe('Notes Management Detail Component', () => {
    let comp: NotesDetailComponent;
    let fixture: ComponentFixture<NotesDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ notes: new Notes(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BankLoanManagerTestModule],
        declarations: [NotesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(NotesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(NotesDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load notes on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.notes).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });

    describe('byteSize', () => {
      it('Should call byteSize from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'byteSize');
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.byteSize(fakeBase64);

        // THEN
        expect(dataUtils.byteSize).toBeCalledWith(fakeBase64);
      });
    });

    describe('openFile', () => {
      it('Should call openFile from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'openFile');
        const fakeContentType = 'fake content type';
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.openFile(fakeContentType, fakeBase64);

        // THEN
        expect(dataUtils.openFile).toBeCalledWith(fakeContentType, fakeBase64);
      });
    });
  });
});
