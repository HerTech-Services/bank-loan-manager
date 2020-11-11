export interface IEmployeEntite {
  id?: number;
  role?: string;
  isPrimary?: boolean;
  isChief?: boolean;
  employeNomEmp?: string;
  employeId?: number;
  entiteLabel?: string;
  entiteId?: number;
}

export class EmployeEntite implements IEmployeEntite {
  constructor(
    public id?: number,
    public role?: string,
    public isPrimary?: boolean,
    public isChief?: boolean,
    public employeNomEmp?: string,
    public employeId?: number,
    public entiteLabel?: string,
    public entiteId?: number
  ) {
    this.isPrimary = this.isPrimary || false;
    this.isChief = this.isChief || false;
  }
}
