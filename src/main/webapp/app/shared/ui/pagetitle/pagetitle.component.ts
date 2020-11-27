import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'jhi-page-title',
  templateUrl: './pagetitle.component.html',
  styleUrls: ['./pagetitle.component.scss'],
})
export class PagetitleComponent implements OnInit {
  @Input() breadcrumbItems: any;
  @Input() title = '';

  constructor() {}

  ngOnInit() {}
}
