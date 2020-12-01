import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { EntiteService } from 'app/admin/entite/entite.service';
import { IEntite, Entite } from 'app/shared/model/entite.model';

describe('Service Tests', () => {
  describe('Entite Service', () => {
    let injector: TestBed;
    let service: EntiteService;
    let httpMock: HttpTestingController;
    let elemDefault: IEntite;
    let expectedResult: IEntite | IEntite[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(EntiteService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Entite(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        0,
        false,
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

      it('should create a Entite', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Entite()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Entite', () => {
        const returnedFromService = Object.assign(
          {
            identifier: 'BBBBBB',
            label: 'BBBBBB',
            shortLabel: 'BBBBBB',
            parent: 1,
            isEnabled: true,
            adrs1: 'BBBBBB',
            adrs2: 'BBBBBB',
            adrs3: 'BBBBBB',
            zipcode: 'BBBBBB',
            city: 'BBBBBB',
            country: 'BBBBBB',
            email: 'BBBBBB',
            parameters: 'BBBBBB',
            parentId: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Entite', () => {
        const returnedFromService = Object.assign(
          {
            identifier: 'BBBBBB',
            label: 'BBBBBB',
            shortLabel: 'BBBBBB',
            parent: 1,
            isEnabled: true,
            adrs1: 'BBBBBB',
            adrs2: 'BBBBBB',
            adrs3: 'BBBBBB',
            zipcode: 'BBBBBB',
            city: 'BBBBBB',
            country: 'BBBBBB',
            email: 'BBBBBB',
            parameters: 'BBBBBB',
            parentId: 'BBBBBB',
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

      it('should delete a Entite', () => {
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
