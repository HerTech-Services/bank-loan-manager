import { IFilelistEgagementType } from 'app/shared/model/filelist-egagement-type.model';

export interface IFilelist {
  id?: number;
  label?: string;
  identifier?: string;
  description?: string;
  filelistEngagementTypes?: IFilelistEgagementType[];
}

export class Filelist implements IFilelist {
  constructor(
    public id?: number,
    public label?: string,
    public identifier?: string,
    public description?: string,
    public filelistEngagementTypes?: IFilelistEgagementType[]
  ) {}
}
