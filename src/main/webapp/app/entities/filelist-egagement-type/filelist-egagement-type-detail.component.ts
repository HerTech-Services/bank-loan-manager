import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFilelistEgagementType } from 'app/shared/model/filelist-egagement-type.model';

@Component({
  selector: 'jhi-filelist-egagement-type-detail',
  templateUrl: './filelist-egagement-type-detail.component.html',
})
export class FilelistEgagementTypeDetailComponent implements OnInit {
  filelistEgagementType: IFilelistEgagementType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ filelistEgagementType }) => (this.filelistEgagementType = filelistEgagementType));
  }

  previousState(): void {
    window.history.back();
  }
}
