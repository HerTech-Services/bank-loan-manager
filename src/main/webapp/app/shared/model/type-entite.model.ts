export interface ITypeEntite {
  id?: number;
  identifier?: string;
  label?: string;
  shortLabel?: string;
}

export class TypeEntite implements ITypeEntite {
  constructor(public id?: number, public identifier?: string, public label?: string, public shortLabel?: string) {}
}
