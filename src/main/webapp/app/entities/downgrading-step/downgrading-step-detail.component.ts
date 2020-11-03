import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDowngradingStep } from 'app/shared/model/downgrading-step.model';

@Component({
  selector: 'jhi-downgrading-step-detail',
  templateUrl: './downgrading-step-detail.component.html',
})
export class DowngradingStepDetailComponent implements OnInit {
  downgradingStep: IDowngradingStep | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ downgradingStep }) => (this.downgradingStep = downgradingStep));
  }

  previousState(): void {
    window.history.back();
  }
}
