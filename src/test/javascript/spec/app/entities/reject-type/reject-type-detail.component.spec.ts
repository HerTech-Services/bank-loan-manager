import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BankLoanManagerTestModule } from '../../../test.module';
import { RejectTypeDetailComponent } from 'app/entities/reject-type/reject-type-detail.component';
import { RejectType } from 'app/shared/model/reject-type.model';

describe('Component Tests', () => {
  describe('RejectType Management Detail Component', () => {
    let comp: RejectTypeDetailComponent;
    let fixture: ComponentFixture<RejectTypeDetailComponent>;
    const route = ({ data: of({ rejectType: new RejectType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BankLoanManagerTestModule],
        declarations: [RejectTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(RejectTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RejectTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load rejectType on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.rejectType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
