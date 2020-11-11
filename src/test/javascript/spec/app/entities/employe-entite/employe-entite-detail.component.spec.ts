import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BankLoanManagerTestModule } from '../../../test.module';
import { EmployeEntiteDetailComponent } from 'app/entities/employe-entite/employe-entite-detail.component';
import { EmployeEntite } from 'app/shared/model/employe-entite.model';

describe('Component Tests', () => {
  describe('EmployeEntite Management Detail Component', () => {
    let comp: EmployeEntiteDetailComponent;
    let fixture: ComponentFixture<EmployeEntiteDetailComponent>;
    const route = ({ data: of({ employeEntite: new EmployeEntite(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BankLoanManagerTestModule],
        declarations: [EmployeEntiteDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(EmployeEntiteDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EmployeEntiteDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load employeEntite on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.employeEntite).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
