import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { EngagementTypeService } from 'app/entities/engagement-type/engagement-type.service';
import { IEngagementType, EngagementType } from 'app/shared/model/engagement-type.model';

describe('Service Tests', () => {
  describe('EngagementType Service', () => {
    let injector: TestBed;
    let service: EngagementTypeService;
    let httpMock: HttpTestingController;
    let elemDefault: IEngagementType;
    let expectedResult: IEngagementType | IEngagementType[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(EngagementTypeService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new EngagementType(0, 'AAAAAAA', false, 0, 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a EngagementType', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new EngagementType()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a EngagementType', () => {
        const returnedFromService = Object.assign(
          {
            label: 'BBBBBB',
            isEnabled: true,
            processDelay: 1,
            delay1: 1,
            delay2: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of EngagementType', () => {
        const returnedFromService = Object.assign(
          {
            label: 'BBBBBB',
            isEnabled: true,
            processDelay: 1,
            delay1: 1,
            delay2: 1,
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

      it('should delete a EngagementType', () => {
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
