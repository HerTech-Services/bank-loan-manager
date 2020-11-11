import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { EngagementService } from 'app/entities/engagement/engagement.service';
import { IEngagement, Engagement } from 'app/shared/model/engagement.model';
import { Echeance } from 'app/shared/model/enumerations/echeance.model';
import { ModeRembourssement } from 'app/shared/model/enumerations/mode-rembourssement.model';

describe('Service Tests', () => {
  describe('Engagement Service', () => {
    let injector: TestBed;
    let service: EngagementService;
    let httpMock: HttpTestingController;
    let elemDefault: IEngagement;
    let expectedResult: IEngagement | IEngagement[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(EngagementService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Engagement(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        0,
        0,
        Echeance.MENSUELLE,
        0,
        ModeRembourssement.CONSTANT,
        'AAAAAAA',
        false,
        false,
        currentDate,
        currentDate,
        currentDate,
        currentDate,
        currentDate,
        currentDate,
        currentDate
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            startDate: currentDate.format(DATE_FORMAT),
            enDate: currentDate.format(DATE_FORMAT),
            createdDate: currentDate.format(DATE_TIME_FORMAT),
            updatedDate: currentDate.format(DATE_TIME_FORMAT),
            deletedDate: currentDate.format(DATE_TIME_FORMAT),
            stopedDate: currentDate.format(DATE_TIME_FORMAT),
            submissionDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Engagement', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            startDate: currentDate.format(DATE_FORMAT),
            enDate: currentDate.format(DATE_FORMAT),
            createdDate: currentDate.format(DATE_TIME_FORMAT),
            updatedDate: currentDate.format(DATE_TIME_FORMAT),
            deletedDate: currentDate.format(DATE_TIME_FORMAT),
            stopedDate: currentDate.format(DATE_TIME_FORMAT),
            submissionDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            startDate: currentDate,
            enDate: currentDate,
            createdDate: currentDate,
            updatedDate: currentDate,
            deletedDate: currentDate,
            stopedDate: currentDate,
            submissionDate: currentDate,
          },
          returnedFromService
        );

        service.create(new Engagement()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Engagement', () => {
        const returnedFromService = Object.assign(
          {
            identifier: 'BBBBBB',
            scoring: 'BBBBBB',
            subject: 'BBBBBB',
            amount: 1,
            delay: 1,
            payment: 'BBBBBB',
            delayed: 1,
            reimbursement: 'BBBBBB',
            analyse: 'BBBBBB',
            isStop: true,
            isDeleted: true,
            startDate: currentDate.format(DATE_FORMAT),
            enDate: currentDate.format(DATE_FORMAT),
            createdDate: currentDate.format(DATE_TIME_FORMAT),
            updatedDate: currentDate.format(DATE_TIME_FORMAT),
            deletedDate: currentDate.format(DATE_TIME_FORMAT),
            stopedDate: currentDate.format(DATE_TIME_FORMAT),
            submissionDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            startDate: currentDate,
            enDate: currentDate,
            createdDate: currentDate,
            updatedDate: currentDate,
            deletedDate: currentDate,
            stopedDate: currentDate,
            submissionDate: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Engagement', () => {
        const returnedFromService = Object.assign(
          {
            identifier: 'BBBBBB',
            scoring: 'BBBBBB',
            subject: 'BBBBBB',
            amount: 1,
            delay: 1,
            payment: 'BBBBBB',
            delayed: 1,
            reimbursement: 'BBBBBB',
            analyse: 'BBBBBB',
            isStop: true,
            isDeleted: true,
            startDate: currentDate.format(DATE_FORMAT),
            enDate: currentDate.format(DATE_FORMAT),
            createdDate: currentDate.format(DATE_TIME_FORMAT),
            updatedDate: currentDate.format(DATE_TIME_FORMAT),
            deletedDate: currentDate.format(DATE_TIME_FORMAT),
            stopedDate: currentDate.format(DATE_TIME_FORMAT),
            submissionDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            startDate: currentDate,
            enDate: currentDate,
            createdDate: currentDate,
            updatedDate: currentDate,
            deletedDate: currentDate,
            stopedDate: currentDate,
            submissionDate: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Engagement', () => {
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
