export interface IFilelistEgagementType {
  id?: number;
  mandatory?: boolean;
  engagementTypeLabel?: string;
  engagementTypeId?: number;
  filelistId?: number;
}

export class FilelistEgagementType implements IFilelistEgagementType {
  constructor(
    public id?: number,
    public mandatory?: boolean,
    public engagementTypeLabel?: string,
    public engagementTypeId?: number,
    public filelistId?: number
  ) {
    this.mandatory = this.mandatory || false;
  }
}
