import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { BankLoanManagerTestModule } from '../../../test.module';
import { EngagementDetailComponent } from 'app/entities/engagement/engagement-detail.component';
import { Engagement } from 'app/shared/model/engagement.model';

describe('Component Tests', () => {
  describe('Engagement Management Detail Component', () => {
    let comp: EngagementDetailComponent;
    let fixture: ComponentFixture<EngagementDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ engagement: new Engagement(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BankLoanManagerTestModule],
        declarations: [EngagementDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(EngagementDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EngagementDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load engagement on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.engagement).toEqual(jasmine.objectContaining({ id: 123 }));
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
