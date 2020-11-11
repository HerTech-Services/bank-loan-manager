import { Moment } from 'moment';

export interface IParameters {
  id?: number;
  identifier?: string;
  description?: string;
  paramValueString?: string;
  paramValueInt?: number;
  paramValueDate?: Moment;
  paramValueJson?: any;
  updatedDate?: Moment;
}

export class Parameters implements IParameters {
  constructor(
    public id?: number,
    public identifier?: string,
    public description?: string,
    public paramValueString?: string,
    public paramValueInt?: number,
    public paramValueDate?: Moment,
    public paramValueJson?: any,
    public updatedDate?: Moment
  ) {}
}
