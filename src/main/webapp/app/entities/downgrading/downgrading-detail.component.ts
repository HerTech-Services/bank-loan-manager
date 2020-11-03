import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IDowngrading } from 'app/shared/model/downgrading.model';

@Component({
  selector: 'jhi-downgrading-detail',
  templateUrl: './downgrading-detail.component.html',
})
export class DowngradingDetailComponent implements OnInit {
  downgrading: IDowngrading | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ downgrading }) => (this.downgrading = downgrading));
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    window.history.back();
  }
}
