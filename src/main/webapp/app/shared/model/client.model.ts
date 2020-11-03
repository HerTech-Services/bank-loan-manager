import { ICompte } from 'app/shared/model/compte.model';
import { IEngagement } from 'app/shared/model/engagement.model';
import { MaritalStatus } from 'app/shared/model/enumerations/marital-status.model';
import { Sex } from 'app/shared/model/enumerations/sex.model';

export interface IClient {
  id?: number;
  codBnk?: string;
  codCli?: string;
  nomCli?: string;
  mendCli?: string;
  sfCli?: MaritalStatus;
  titre?: string;
  datNai?: string;
  lieuNai?: string;
  natCli?: string;
  lngCli?: string;
  socCli?: string;
  emploi?: string;
  sexe?: Sex;
  numCni?: string;
  datCni?: string;
  finCni?: string;
  lieuCni?: string;
  autoCni?: string;
  adr?: string;
  tel?: string;
  ville?: string;
  site?: string;
  loc?: string;
  fax?: string;
  agnce?: string;
  mail?: string;
  pays?: string;
  comptes?: ICompte[];
  engagements?: IEngagement[];
}

export class Client implements IClient {
  constructor(
    public id?: number,
    public codBnk?: string,
    public codCli?: string,
    public nomCli?: string,
    public mendCli?: string,
    public sfCli?: MaritalStatus,
    public titre?: string,
    public datNai?: string,
    public lieuNai?: string,
    public natCli?: string,
    public lngCli?: string,
    public socCli?: string,
    public emploi?: string,
    public sexe?: Sex,
    public numCni?: string,
    public datCni?: string,
    public finCni?: string,
    public lieuCni?: string,
    public autoCni?: string,
    public adr?: string,
    public tel?: string,
    public ville?: string,
    public site?: string,
    public loc?: string,
    public fax?: string,
    public agnce?: string,
    public mail?: string,
    public pays?: string,
    public comptes?: ICompte[],
    public engagements?: IEngagement[]
  ) {}
}
