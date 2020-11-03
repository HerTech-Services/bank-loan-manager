export interface IRejectType {
  id?: number;
  label?: string;
  identifier?: string;
  scoringLabel?: string;
  scoringId?: number;
}

export class RejectType implements IRejectType {
  constructor(
    public id?: number,
    public label?: string,
    public identifier?: string,
    public scoringLabel?: string,
    public scoringId?: number
  ) {}
}
