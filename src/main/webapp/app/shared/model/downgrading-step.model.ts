export interface IDowngradingStep {
  id?: number;
  label?: string;
  identifier?: string;
  scoringLabel?: string;
  scoringId?: number;
}

export class DowngradingStep implements IDowngradingStep {
  constructor(
    public id?: number,
    public label?: string,
    public identifier?: string,
    public scoringLabel?: string,
    public scoringId?: number
  ) {}
}
