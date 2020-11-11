import { IEngagement } from 'app/shared/model/engagement.model';

export interface ICompte {
  id?: number;
  codBnk?: string;
  codCpt?: string;
  numCpt?: string;
  libCpt?: string;
  numCpta?: string;
  numAgc?: string;
  typCpt?: string;
  etat?: string;
  codCli?: string;
  numCtr?: string;
  codeFor?: string;
  engagements?: IEngagement[];
  clientNomCli?: string;
  clientId?: number;
}

export class Compte implements ICompte {
  constructor(
    public id?: number,
    public codBnk?: string,
    public codCpt?: string,
    public numCpt?: string,
    public libCpt?: string,
    public numCpta?: string,
    public numAgc?: string,
    public typCpt?: string,
    public etat?: string,
    public codCli?: string,
    public numCtr?: string,
    public codeFor?: string,
    public engagements?: IEngagement[],
    public clientNomCli?: string,
    public clientId?: number
  ) {}
}
