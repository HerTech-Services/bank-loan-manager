import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BankLoanManagerTestModule } from '../../../test.module';
import { EngagementTypeDetailComponent } from 'app/entities/engagement-type/engagement-type-detail.component';
import { EngagementType } from 'app/shared/model/engagement-type.model';

describe('Component Tests', () => {
  describe('EngagementType Management Detail Component', () => {
    let comp: EngagementTypeDetailComponent;
    let fixture: ComponentFixture<EngagementTypeDetailComponent>;
    const route = ({ data: of({ engagementType: new EngagementType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BankLoanManagerTestModule],
        declarations: [EngagementTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(EngagementTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EngagementTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load engagementType on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.engagementType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
