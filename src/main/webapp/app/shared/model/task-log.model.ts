import { Moment } from 'moment';

export interface ITaskLog {
  id?: number;
  comment?: string;
  createdDate?: Moment;
  taskProperties?: any;
  engagementProperties?: any;
  userFirstName?: string;
  userId?: number;
  actionLabel?: string;
  actionId?: number;
  taskId?: number;
  engagementSubject?: string;
  engagementId?: number;
}

export class TaskLog implements ITaskLog {
  constructor(
    public id?: number,
    public comment?: string,
    public createdDate?: Moment,
    public taskProperties?: any,
    public engagementProperties?: any,
    public userFirstName?: string,
    public userId?: number,
    public actionLabel?: string,
    public actionId?: number,
    public taskId?: number,
    public engagementSubject?: string,
    public engagementId?: number
  ) {}
}
