import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { BankLoanManagerTestModule } from '../../../test.module';
import { EntiteDetailComponent } from 'app/admin/entite/entite-detail.component';
import { Entite } from 'app/shared/model/entite.model';

describe('Component Tests', () => {
  describe('Entite Management Detail Component', () => {
    let comp: EntiteDetailComponent;
    let fixture: ComponentFixture<EntiteDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ entite: new Entite(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BankLoanManagerTestModule],
        declarations: [EntiteDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(EntiteDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EntiteDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load entite on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.entite).toEqual(jasmine.objectContaining({ id: 123 }));
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
