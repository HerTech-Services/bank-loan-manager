export interface IScoring {
  id?: number;
  label?: string;
  score?: number;
}

export class Scoring implements IScoring {
  constructor(public id?: number, public label?: string, public score?: number) {}
}
