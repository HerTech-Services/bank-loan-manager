import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITasktypeStatusAction } from 'app/shared/model/tasktype-status-action.model';

@Component({
  selector: 'jhi-tasktype-status-action-detail',
  templateUrl: './tasktype-status-action-detail.component.html',
})
export class TasktypeStatusActionDetailComponent implements OnInit {
  tasktypeStatusAction: ITasktypeStatusAction | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tasktypeStatusAction }) => (this.tasktypeStatusAction = tasktypeStatusAction));
  }

  previousState(): void {
    window.history.back();
  }
}
