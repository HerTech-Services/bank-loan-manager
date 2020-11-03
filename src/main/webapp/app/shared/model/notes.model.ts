import { Moment } from 'moment';

export interface INotes {
  id?: number;
  description?: any;
  createdDate?: Moment;
  userFirstName?: string;
  userId?: number;
  engagementSubject?: string;
  engagementId?: number;
}

export class Notes implements INotes {
  constructor(
    public id?: number,
    public description?: any,
    public createdDate?: Moment,
    public userFirstName?: string,
    public userId?: number,
    public engagementSubject?: string,
    public engagementId?: number
  ) {}
}
