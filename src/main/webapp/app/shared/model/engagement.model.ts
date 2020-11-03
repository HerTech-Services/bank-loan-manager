import { Moment } from 'moment';
import { IDowngrading } from 'app/shared/model/downgrading.model';
import { IReject } from 'app/shared/model/reject.model';
import { ITask } from 'app/shared/model/task.model';
import { INotes } from 'app/shared/model/notes.model';
import { Echeance } from 'app/shared/model/enumerations/echeance.model';
import { ModeRembourssement } from 'app/shared/model/enumerations/mode-rembourssement.model';

export interface IEngagement {
  id?: number;
  scoring?: number;
  subject?: string;
  amount?: number;
  delay?: number;
  payment?: Echeance;
  delayed?: number;
  reimbursement?: ModeRembourssement;
  analyse?: any;
  isStop?: boolean;
  isDeleted?: boolean;
  startDate?: Moment;
  enDate?: Moment;
  createdDate?: Moment;
  updatedDate?: Moment;
  deletedDate?: Moment;
  stopedDate?: Moment;
  currentTaskId?: number;
  downgradings?: IDowngrading[];
  rejects?: IReject[];
  tasks?: ITask[];
  notes?: INotes[];
  typeLabel?: string;
  typeId?: number;
  statusLabel?: string;
  statusId?: number;
  decisionLabel?: string;
  decisionId?: number;
  createdByFirstName?: string;
  createdById?: number;
  clientNomCli?: string;
  clientId?: number;
  compteLibCpt?: string;
  compteId?: number;
}

export class Engagement implements IEngagement {
  constructor(
    public id?: number,
    public scoring?: number,
    public subject?: string,
    public amount?: number,
    public delay?: number,
    public payment?: Echeance,
    public delayed?: number,
    public reimbursement?: ModeRembourssement,
    public analyse?: any,
    public isStop?: boolean,
    public isDeleted?: boolean,
    public startDate?: Moment,
    public enDate?: Moment,
    public createdDate?: Moment,
    public updatedDate?: Moment,
    public deletedDate?: Moment,
    public stopedDate?: Moment,
    public currentTaskId?: number,
    public downgradings?: IDowngrading[],
    public rejects?: IReject[],
    public tasks?: ITask[],
    public notes?: INotes[],
    public typeLabel?: string,
    public typeId?: number,
    public statusLabel?: string,
    public statusId?: number,
    public decisionLabel?: string,
    public decisionId?: number,
    public createdByFirstName?: string,
    public createdById?: number,
    public clientNomCli?: string,
    public clientId?: number,
    public compteLibCpt?: string,
    public compteId?: number
  ) {
    this.isStop = this.isStop || false;
    this.isDeleted = this.isDeleted || false;
  }
}
