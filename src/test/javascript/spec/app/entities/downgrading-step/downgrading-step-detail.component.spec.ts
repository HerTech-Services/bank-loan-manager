import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BankLoanManagerTestModule } from '../../../test.module';
import { DowngradingStepDetailComponent } from 'app/entities/downgrading-step/downgrading-step-detail.component';
import { DowngradingStep } from 'app/shared/model/downgrading-step.model';

describe('Component Tests', () => {
  describe('DowngradingStep Management Detail Component', () => {
    let comp: DowngradingStepDetailComponent;
    let fixture: ComponentFixture<DowngradingStepDetailComponent>;
    const route = ({ data: of({ downgradingStep: new DowngradingStep(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BankLoanManagerTestModule],
        declarations: [DowngradingStepDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(DowngradingStepDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DowngradingStepDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load downgradingStep on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.downgradingStep).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
