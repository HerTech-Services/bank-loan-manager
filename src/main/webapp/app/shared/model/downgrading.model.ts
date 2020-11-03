import { Moment } from 'moment';

export interface IDowngrading {
  id?: number;
  createdDate?: Moment;
  capital?: number;
  interests?: number;
  penalties?: number;
  accessories?: number;
  description?: any;
  stepLabel?: string;
  stepId?: number;
  engagementSubject?: string;
  engagementId?: number;
}

export class Downgrading implements IDowngrading {
  constructor(
    public id?: number,
    public createdDate?: Moment,
    public capital?: number,
    public interests?: number,
    public penalties?: number,
    public accessories?: number,
    public description?: any,
    public stepLabel?: string,
    public stepId?: number,
    public engagementSubject?: string,
    public engagementId?: number
  ) {}
}
