export interface IAction {
  id?: number;
  label?: string;
  keyword?: string;
  isSystem?: boolean;
  actionPage?: string;
  history?: boolean;
  composant?: string;
  parameters?: any;
  statusLabel?: string;
  statusId?: number;
}

export class Action implements IAction {
  constructor(
    public id?: number,
    public label?: string,
    public keyword?: string,
    public isSystem?: boolean,
    public actionPage?: string,
    public history?: boolean,
    public composant?: string,
    public parameters?: any,
    public statusLabel?: string,
    public statusId?: number
  ) {
    this.isSystem = this.isSystem || false;
    this.history = this.history || false;
  }
}
