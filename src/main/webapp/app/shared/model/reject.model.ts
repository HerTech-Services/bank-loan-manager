import { Moment } from 'moment';

export interface IReject {
  id?: number;
  createdDate?: Moment;
  capital?: number;
  interests?: number;
  penalties?: number;
  accessories?: number;
  description?: any;
  typeLabel?: string;
  typeId?: number;
  engagementSubject?: string;
  engagementId?: number;
}

export class Reject implements IReject {
  constructor(
    public id?: number,
    public createdDate?: Moment,
    public capital?: number,
    public interests?: number,
    public penalties?: number,
    public accessories?: number,
    public description?: any,
    public typeLabel?: string,
    public typeId?: number,
    public engagementSubject?: string,
    public engagementId?: number
  ) {}
}
