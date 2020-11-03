export interface IUserPoste {
  id?: number;
  role?: string;
  isPrimary?: boolean;
  userFirstName?: string;
  userId?: number;
  entitiesLabel?: string;
  entitiesId?: number;
}

export class UserPoste implements IUserPoste {
  constructor(
    public id?: number,
    public role?: string,
    public isPrimary?: boolean,
    public userFirstName?: string,
    public userId?: number,
    public entitiesLabel?: string,
    public entitiesId?: number
  ) {
    this.isPrimary = this.isPrimary || false;
  }
}
