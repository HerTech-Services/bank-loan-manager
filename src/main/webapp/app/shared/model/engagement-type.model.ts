export interface IEngagementType {
  id?: number;
  label?: string;
  isEnabled?: boolean;
  processDelay?: number;
  delay1?: number;
  delay2?: number;
}

export class EngagementType implements IEngagementType {
  constructor(
    public id?: number,
    public label?: string,
    public isEnabled?: boolean,
    public processDelay?: number,
    public delay1?: number,
    public delay2?: number
  ) {
    this.isEnabled = this.isEnabled || false;
  }
}
