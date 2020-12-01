import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BankLoanManagerTestModule } from '../../../test.module';
import { TypeEntiteDetailComponent } from 'app/admin/type-entite/type-entite-detail.component';
import { TypeEntite } from 'app/shared/model/type-entite.model';

describe('Component Tests', () => {
  describe('TypeEntite Management Detail Component', () => {
    let comp: TypeEntiteDetailComponent;
    let fixture: ComponentFixture<TypeEntiteDetailComponent>;
    const route = ({ data: of({ typeEntite: new TypeEntite(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BankLoanManagerTestModule],
        declarations: [TypeEntiteDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TypeEntiteDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TypeEntiteDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load typeEntite on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.typeEntite).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
