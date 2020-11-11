import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITypeEntite } from 'app/shared/model/type-entite.model';

@Component({
  selector: 'jhi-type-entite-detail',
  templateUrl: './type-entite-detail.component.html',
})
export class TypeEntiteDetailComponent implements OnInit {
  typeEntite: ITypeEntite | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeEntite }) => (this.typeEntite = typeEntite));
  }

  previousState(): void {
    window.history.back();
  }
}
