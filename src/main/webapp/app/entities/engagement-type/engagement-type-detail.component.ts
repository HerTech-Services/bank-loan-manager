import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEngagementType } from 'app/shared/model/engagement-type.model';

@Component({
  selector: 'jhi-engagement-type-detail',
  templateUrl: './engagement-type-detail.component.html',
})
export class EngagementTypeDetailComponent implements OnInit {
  engagementType: IEngagementType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ engagementType }) => (this.engagementType = engagementType));
  }

  previousState(): void {
    window.history.back();
  }
}
