import { AfterViewInit, Component, OnInit } from '@angular/core';

import { LAYOUT_VERTICAL, LAYOUT_HORIZONTAL } from './layouts.model';
@Component({
  selector: 'jhi-layout',
  templateUrl: './layout.component.html',
  styleUrls: ['./layout.component.scss'],
})
export class LayoutComponent implements OnInit, AfterViewInit {
  // layout related config
  layoutType: string = LAYOUT_VERTICAL;
  constructor() {}

  ngOnInit() {
    // default settings
    this.layoutType = LAYOUT_VERTICAL;
  }
  ngAfterViewInit() {}

  /**
   * Check if the vertical layout is requested
   */
  isVerticalLayoutRequested() {
    return this.layoutType === LAYOUT_VERTICAL;
  }

  /**
   * Check if the horizontal layout is requested
   */
  isHorizontalLayoutRequested() {
    return this.layoutType === LAYOUT_HORIZONTAL;
  }
}
