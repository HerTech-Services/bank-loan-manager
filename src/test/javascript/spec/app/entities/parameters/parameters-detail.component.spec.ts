import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BankLoanManagerTestModule } from '../../../test.module';
import { ParametersDetailComponent } from 'app/entities/parameters/parameters-detail.component';
import { Parameters } from 'app/shared/model/parameters.model';

describe('Component Tests', () => {
  describe('Parameters Management Detail Component', () => {
    let comp: ParametersDetailComponent;
    let fixture: ComponentFixture<ParametersDetailComponent>;
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
    });

    describe('OnInit', () => {
      it('Should load parameters on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.parameters).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
