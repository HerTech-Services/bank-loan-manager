import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BankLoanManagerTestModule } from '../../../test.module';
import { FilelistDetailComponent } from 'app/entities/filelist/filelist-detail.component';
import { Filelist } from 'app/shared/model/filelist.model';

describe('Component Tests', () => {
  describe('Filelist Management Detail Component', () => {
    let comp: FilelistDetailComponent;
    let fixture: ComponentFixture<FilelistDetailComponent>;
    const route = ({ data: of({ filelist: new Filelist(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BankLoanManagerTestModule],
        declarations: [FilelistDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(FilelistDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FilelistDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load filelist on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.filelist).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
