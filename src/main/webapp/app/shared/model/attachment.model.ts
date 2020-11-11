import { Moment } from 'moment';

export interface IAttachment {
  id?: number;
  label?: string;
  format?: string;
  createdDate?: Moment;
  updatedDate?: Moment;
  version?: number;
  path?: string;
  filename?: string;
  filesize?: number;
  validationDate?: Moment;
  engagementSubject?: string;
  engagementId?: number;
  userFirstName?: string;
  userId?: number;
  updatedByFirstName?: string;
  updatedById?: number;
  statusLabel?: string;
  statusId?: number;
  origins?: IAttachment[];
  children?: IAttachment[];
}

export class Attachment implements IAttachment {
  constructor(
    public id?: number,
    public label?: string,
    public format?: string,
    public createdDate?: Moment,
    public updatedDate?: Moment,
    public version?: number,
    public path?: string,
    public filename?: string,
    public filesize?: number,
    public validationDate?: Moment,
    public engagementSubject?: string,
    public engagementId?: number,
    public userFirstName?: string,
    public userId?: number,
    public updatedByFirstName?: string,
    public updatedById?: number,
    public statusLabel?: string,
    public statusId?: number,
    public origins?: IAttachment[],
    public children?: IAttachment[]
  ) {}
}
