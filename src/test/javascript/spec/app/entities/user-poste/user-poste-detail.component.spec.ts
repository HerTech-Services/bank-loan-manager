import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BankLoanManagerTestModule } from '../../../test.module';
import { UserPosteDetailComponent } from 'app/entities/user-poste/user-poste-detail.component';
import { UserPoste } from 'app/shared/model/user-poste.model';

describe('Component Tests', () => {
  describe('UserPoste Management Detail Component', () => {
    let comp: UserPosteDetailComponent;
    let fixture: ComponentFixture<UserPosteDetailComponent>;
    const route = ({ data: of({ userPoste: new UserPoste(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BankLoanManagerTestModule],
        declarations: [UserPosteDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(UserPosteDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserPosteDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load userPoste on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.userPoste).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
