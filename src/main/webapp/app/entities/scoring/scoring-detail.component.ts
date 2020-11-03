import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IScoring } from 'app/shared/model/scoring.model';

@Component({
  selector: 'jhi-scoring-detail',
  templateUrl: './scoring-detail.component.html',
})
export class ScoringDetailComponent implements OnInit {
  scoring: IScoring | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ scoring }) => (this.scoring = scoring));
  }

  previousState(): void {
    window.history.back();
  }
}
