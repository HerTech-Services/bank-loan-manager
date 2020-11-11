import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BankLoanManagerTestModule } from '../../../test.module';
import { TasktypeStatusActionDetailComponent } from 'app/entities/tasktype-status-action/tasktype-status-action-detail.component';
import { TasktypeStatusAction } from 'app/shared/model/tasktype-status-action.model';

describe('Component Tests', () => {
  describe('TasktypeStatusAction Management Detail Component', () => {
    let comp: TasktypeStatusActionDetailComponent;
    let fixture: ComponentFixture<TasktypeStatusActionDetailComponent>;
    const route = ({ data: of({ tasktypeStatusAction: new TasktypeStatusAction(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BankLoanManagerTestModule],
        declarations: [TasktypeStatusActionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TasktypeStatusActionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TasktypeStatusActionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load tasktypeStatusAction on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tasktypeStatusAction).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
