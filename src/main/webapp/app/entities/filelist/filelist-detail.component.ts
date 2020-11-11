import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFilelist } from 'app/shared/model/filelist.model';

@Component({
  selector: 'jhi-filelist-detail',
  templateUrl: './filelist-detail.component.html',
})
export class FilelistDetailComponent implements OnInit {
  filelist: IFilelist | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ filelist }) => (this.filelist = filelist));
  }

  previousState(): void {
    window.history.back();
  }
}
