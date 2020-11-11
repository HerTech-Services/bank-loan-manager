export interface IStatus {
  id?: number;
  label?: string;
  isSystem?: boolean;
  imgFilename?: string;
  canBeSearched?: boolean;
  canBeModified?: boolean;
  isEnabled?: boolean;
}

export class Status implements IStatus {
  constructor(
    public id?: number,
    public label?: string,
    public isSystem?: boolean,
    public imgFilename?: string,
    public canBeSearched?: boolean,
    public canBeModified?: boolean,
    public isEnabled?: boolean
  ) {
    this.isSystem = this.isSystem || false;
    this.canBeSearched = this.canBeSearched || false;
    this.canBeModified = this.canBeModified || false;
    this.isEnabled = this.isEnabled || false;
  }
}
