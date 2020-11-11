import { Moment } from 'moment';

export interface ITask {
  id?: number;
  sequence?: number;
  isSystem?: boolean;
  processDelay?: number;
  delay1?: number;
  delay2?: number;
  viewed?: number;
  createdDate?: Moment;
  processDate?: Moment;
  processComment?: any;
  typeLabel?: string;
  typeId?: number;
  assigneeLogin?: string;
  assigneeId?: number;
  engagementSubject?: string;
  engagementId?: number;
}

export class Task implements ITask {
  constructor(
    public id?: number,
    public sequence?: number,
    public isSystem?: boolean,
    public processDelay?: number,
    public delay1?: number,
    public delay2?: number,
    public viewed?: number,
    public createdDate?: Moment,
    public processDate?: Moment,
    public processComment?: any,
    public typeLabel?: string,
    public typeId?: number,
    public assigneeLogin?: string,
    public assigneeId?: number,
    public engagementSubject?: string,
    public engagementId?: number
  ) {
    this.isSystem = this.isSystem || false;
  }
}
