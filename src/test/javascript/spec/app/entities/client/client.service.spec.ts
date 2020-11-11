import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { ClientService } from 'app/entities/client/client.service';
import { IClient, Client } from 'app/shared/model/client.model';
import { MaritalStatus } from 'app/shared/model/enumerations/marital-status.model';
import { Sex } from 'app/shared/model/enumerations/sex.model';

describe('Service Tests', () => {
  describe('Client Service', () => {
    let injector: TestBed;
    let service: ClientService;
    let httpMock: HttpTestingController;
    let elemDefault: IClient;
    let expectedResult: IClient | IClient[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ClientService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Client(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        MaritalStatus.CELIBATIRE,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        Sex.MASCULIN,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Client', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Client()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Client', () => {
        const returnedFromService = Object.assign(
          {
            codBnk: 'BBBBBB',
            codCli: 'BBBBBB',
            nomCli: 'BBBBBB',
            mendCli: 'BBBBBB',
            sfCli: 'BBBBBB',
            titre: 'BBBBBB',
            datNai: 'BBBBBB',
            lieuNai: 'BBBBBB',
            natCli: 'BBBBBB',
            lngCli: 'BBBBBB',
            socCli: 'BBBBBB',
            emploi: 'BBBBBB',
            sexe: 'BBBBBB',
            numCni: 'BBBBBB',
            datCni: 'BBBBBB',
            finCni: 'BBBBBB',
            lieuCni: 'BBBBBB',
            autoCni: 'BBBBBB',
            adr: 'BBBBBB',
            tel: 'BBBBBB',
            ville: 'BBBBBB',
            site: 'BBBBBB',
            loc: 'BBBBBB',
            fax: 'BBBBBB',
            agnce: 'BBBBBB',
            mail: 'BBBBBB',
            pays: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Client', () => {
        const returnedFromService = Object.assign(
          {
            codBnk: 'BBBBBB',
            codCli: 'BBBBBB',
            nomCli: 'BBBBBB',
            mendCli: 'BBBBBB',
            sfCli: 'BBBBBB',
            titre: 'BBBBBB',
            datNai: 'BBBBBB',
            lieuNai: 'BBBBBB',
            natCli: 'BBBBBB',
            lngCli: 'BBBBBB',
            socCli: 'BBBBBB',
            emploi: 'BBBBBB',
            sexe: 'BBBBBB',
            numCni: 'BBBBBB',
            datCni: 'BBBBBB',
            finCni: 'BBBBBB',
            lieuCni: 'BBBBBB',
            autoCni: 'BBBBBB',
            adr: 'BBBBBB',
            tel: 'BBBBBB',
            ville: 'BBBBBB',
            site: 'BBBBBB',
            loc: 'BBBBBB',
            fax: 'BBBBBB',
            agnce: 'BBBBBB',
            mail: 'BBBBBB',
            pays: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Client', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
