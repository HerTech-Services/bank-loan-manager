export interface ISector {
  id?: number;
  label?: string;
  identifier?: string;
  scoringLabel?: string;
  scoringId?: number;
}

export class Sector implements ISector {
  constructor(
    public id?: number,
    public label?: string,
    public identifier?: string,
    public scoringLabel?: string,
    public scoringId?: number
  ) {}
}
