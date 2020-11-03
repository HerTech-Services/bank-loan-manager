export interface ITaskType {
  id?: number;
  label?: string;
  identifier?: string;
  isEnabled?: boolean;
}

export class TaskType implements ITaskType {
  constructor(public id?: number, public label?: string, public identifier?: string, public isEnabled?: boolean) {
    this.isEnabled = this.isEnabled || false;
  }
}
