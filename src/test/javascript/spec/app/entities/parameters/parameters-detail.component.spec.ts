import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { BankLoanManagerTestModule } from '../../../test.module';
import { ParametersDetailComponent } from 'app/entities/parameters/parameters-detail.component';
import { Parameters } from 'app/shared/model/parameters.model';

describe('Component Tests', () => {
  describe('Parameters Management Detail Component', () => {
    let comp: ParametersDetailComponent;
    let fixture: ComponentFixture<ParametersDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ parameters: new Parameters(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BankLoanManagerTestModule],
        declarations: [ParametersDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ParametersDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ParametersDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load parameters on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.parameters).toEqual(jasmine.objectContaining({ id: 123 }));
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
