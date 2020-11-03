import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUserPoste } from 'app/shared/model/user-poste.model';

@Component({
  selector: 'jhi-user-poste-detail',
  templateUrl: './user-poste-detail.component.html',
})
export class UserPosteDetailComponent implements OnInit {
  userPoste: IUserPoste | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userPoste }) => (this.userPoste = userPoste));
  }

  previousState(): void {
    window.history.back();
  }
}
