import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEmployeEntite } from 'app/shared/model/employe-entite.model';

@Component({
  selector: 'jhi-employe-entite-detail',
  templateUrl: './employe-entite-detail.component.html',
})
export class EmployeEntiteDetailComponent implements OnInit {
  employeEntite: IEmployeEntite | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ employeEntite }) => (this.employeEntite = employeEntite));
  }

  previousState(): void {
    window.history.back();
  }
}
