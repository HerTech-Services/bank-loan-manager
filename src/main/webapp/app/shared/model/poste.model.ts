import { IUserPoste } from 'app/shared/model/user-poste.model';

export interface IPoste {
  id?: number;
  code?: string;
  label?: string;
  shortLabel?: string;
  entity?: string;
  enabled?: boolean;
  adrs1?: string;
  adrs2?: string;
  adrs3?: string;
  city?: string;
  userPostes?: IUserPoste[];
  parentPostes?: IPoste[];
  children?: IPoste[];
}

export class Poste implements IPoste {
  constructor(
    public id?: number,
    public code?: string,
    public label?: string,
    public shortLabel?: string,
    public entity?: string,
    public enabled?: boolean,
    public adrs1?: string,
    public adrs2?: string,
    public adrs3?: string,
    public city?: string,
    public userPostes?: IUserPoste[],
    public parentPostes?: IPoste[],
    public children?: IPoste[]
  ) {
    this.enabled = this.enabled || false;
  }
}
