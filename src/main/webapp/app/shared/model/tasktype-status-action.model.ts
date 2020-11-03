export interface ITasktypeStatusAction {
  id?: number;
  tasktypeLabel?: string;
  tasktypeId?: number;
  actionLabel?: string;
  actionId?: number;
  statusLabel?: string;
  statusId?: number;
}

export class TasktypeStatusAction implements ITasktypeStatusAction {
  constructor(
    public id?: number,
    public tasktypeLabel?: string,
    public tasktypeId?: number,
    public actionLabel?: string,
    public actionId?: number,
    public statusLabel?: string,
    public statusId?: number
  ) {}
}
