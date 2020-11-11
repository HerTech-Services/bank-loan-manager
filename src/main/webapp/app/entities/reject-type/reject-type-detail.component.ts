import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRejectType } from 'app/shared/model/reject-type.model';

@Component({
  selector: 'jhi-reject-type-detail',
  templateUrl: './reject-type-detail.component.html',
})
export class RejectTypeDetailComponent implements OnInit {
  rejectType: IRejectType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ rejectType }) => (this.rejectType = rejectType));
  }

  previousState(): void {
    window.history.back();
  }
}
