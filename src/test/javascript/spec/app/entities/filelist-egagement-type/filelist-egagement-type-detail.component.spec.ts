import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BankLoanManagerTestModule } from '../../../test.module';
import { FilelistEgagementTypeDetailComponent } from 'app/entities/filelist-egagement-type/filelist-egagement-type-detail.component';
import { FilelistEgagementType } from 'app/shared/model/filelist-egagement-type.model';

describe('Component Tests', () => {
  describe('FilelistEgagementType Management Detail Component', () => {
    let comp: FilelistEgagementTypeDetailComponent;
    let fixture: ComponentFixture<FilelistEgagementTypeDetailComponent>;
    const route = ({ data: of({ filelistEgagementType: new FilelistEgagementType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BankLoanManagerTestModule],
        declarations: [FilelistEgagementTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(FilelistEgagementTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FilelistEgagementTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load filelistEgagementType on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.filelistEgagementType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
