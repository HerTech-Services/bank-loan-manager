/* eslint-disable @typescript-eslint/tslint/config */
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class LogService {
  constructor() {}

  // tslint:disable-next-line:typedef
  log(msg: any) {
    // eslint-disable-next-line no-console
    console.log(new Date() + ': ' + JSON.stringify(msg));
  }
}
