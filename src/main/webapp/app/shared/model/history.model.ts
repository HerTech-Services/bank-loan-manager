import { Moment } from 'moment';

export interface IHistory {
  id?: number;
  tableName?: string;
  recordId?: string;
  eventType?: string;
  eventDate?: Moment;
  info?: string;
  host?: string;
  properties?: any;
}

export class History implements IHistory {
  constructor(
    public id?: number,
    public tableName?: string,
    public recordId?: string,
    public eventType?: string,
    public eventDate?: Moment,
    public info?: string,
    public host?: string,
    public properties?: any
  ) {}
}
