import { IEmployeEntite } from 'app/shared/model/employe-entite.model';

export interface IEntite {
  id?: number;
  identifier?: string;
  label?: string;
  shortLabel?: string;
  parent?: number;
  isEnabled?: boolean;
  adrs1?: string;
  adrs2?: string;
  adrs3?: string;
  zipcode?: string;
  city?: string;
  country?: string;
  email?: string;
  parameters?: any;
  parentId?: string;
  employeEntites?: IEmployeEntite[];
}

export class Entite implements IEntite {
  constructor(
    public id?: number,
    public identifier?: string,
    public label?: string,
    public shortLabel?: string,
    public parent?: number,
    public isEnabled?: boolean,
    public adrs1?: string,
    public adrs2?: string,
    public adrs3?: string,
    public zipcode?: string,
    public city?: string,
    public country?: string,
    public email?: string,
    public parameters?: any,
    public parentId?: string,
    public employeEntites?: IEmployeEntite[]
  ) {
    this.isEnabled = this.isEnabled || false;
  }
}
