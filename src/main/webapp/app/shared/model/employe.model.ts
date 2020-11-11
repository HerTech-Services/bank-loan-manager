export interface IEmploye {
  id?: number;
  codBnk?: string;
  codEmp?: string;
  rsEmp?: string;
  nomEmp?: string;
  prenomEmp?: string;
  fctEmp?: string;
  adrEmp?: string;
  teEmp?: string;
  typEnmp?: string;
  numMat?: string;
  userLogin?: string;
  userId?: number;
}

export class Employe implements IEmploye {
  constructor(
    public id?: number,
    public codBnk?: string,
    public codEmp?: string,
    public rsEmp?: string,
    public nomEmp?: string,
    public prenomEmp?: string,
    public fctEmp?: string,
    public adrEmp?: string,
    public teEmp?: string,
    public typEnmp?: string,
    public numMat?: string,
    public userLogin?: string,
    public userId?: number
  ) {}
}
