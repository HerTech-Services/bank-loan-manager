import { Component, OnInit, OnDestroy, ViewChild } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, ParamMap, Router, Data } from '@angular/router';
import { Subscription, combineLatest } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IEntite } from 'app/shared/model/entite.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { EntiteService } from './entite.service';
import { EntiteDeleteDialogComponent } from './entite-delete-dialog.component';
import { LogService } from 'app/log.service';
import { ITreeOptions, TreeComponent, TreeNode, TREE_ACTIONS } from '@circlon/angular-tree-component';

@Component({
  selector: 'jhi-entite',
  templateUrl: './entite.component.html',
})
export class EntiteComponent implements OnInit, OnDestroy {
  entites?: IEntite[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;
  breadCrumbItems?: Array<{}>;
  selectedNode: any;
  deactivateNode: any;
  nodes: any[] = [];
  @ViewChild('entiteTreeComponent') entiteTreeComponent!: TreeComponent;

  options: ITreeOptions = {};

  constructor(
    protected entiteService: EntiteService,
    protected activatedRoute: ActivatedRoute,
    protected dataUtils: JhiDataUtils,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected logService: LogService
  ) {}

  loadPage(page?: number, dontNavigate?: boolean): void {
    /*
    const pageToLoad: number = page || this.page || 1;

    this.entiteService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe(
        (res: HttpResponse<IEntite[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
        () => this.onError()
      );
      */
    this.entiteService.getEntiteAsTree().subscribe(
      (entites: any[]) => this.onSuccessEntiteAsTree(entites),
      () => this.onError()
    );
  }

  ngOnInit(): void {
    this.breadCrumbItems = [{ label: 'global.menu.admin.main' }, { label: 'bankLoanManagerApp.entite.home.title', active: true }];
    this.entiteService.getEntiteAsTree().subscribe(
      (entites: any[]) => this.onSuccessEntiteAsTree(entites),
      () => this.onError()
    );
    // this.handleNavigation();
    this.registerChangeInEntites();
  }

  protected handleNavigation(): void {
    combineLatest(this.activatedRoute.data, this.activatedRoute.queryParamMap, (data: Data, params: ParamMap) => {
      const page = params.get('page');
      const pageNumber = page !== null ? +page : 1;
      const sort = (params.get('sort') ?? data['defaultSort']).split(',');
      const predicate = sort[0];
      const ascending = sort[1] === 'asc';
      if (pageNumber !== this.page || predicate !== this.predicate || ascending !== this.ascending) {
        this.predicate = predicate;
        this.ascending = ascending;
        this.loadPage(pageNumber, true);
      }
    }).subscribe();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IEntite): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInEntites(): void {
    this.eventSubscriber = this.eventManager.subscribe('entiteListModification', () => this.loadPage());
  }

  delete(node: any): void {
    if (node !== undefined) {
      const modalRef = this.modalService.open(EntiteDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
      modalRef.componentInstance.entite = node;
    }
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IEntite[] | null, headers: HttpHeaders, page: number, navigate: boolean): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    if (navigate) {
      this.router.navigate(['/entite'], {
        queryParams: {
          page: this.page,
          size: this.itemsPerPage,
          sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc'),
        },
      });
    }
    this.entites = data || [];
    this.ngbPaginationPage = this.page;
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page ?? 1;
  }

  protected onSuccessEntiteAsTree(data: any[] | null): void {
    this.nodes = data || [];
    setTimeout(() => this.onExpandAll(), 0);
  }

  onActivate(event: any) {
    this.selectedNode = event.node.data;
    this.entiteTreeComponent.treeModel.doForAll((node: TreeNode) => {
      if (node.data.id !== event.node.data.id) {
        if (node.isActive) {
          node.toggleActivated();
        }
      } else {
        // event.node.setActiveAndVisible();
      }
    });
  }

  onDesactivate(event: any) {
    this.deactivateNode = event.node.dada;
    this.selectedNode = undefined;
  }

  onExpandAll() {
    this.entiteTreeComponent.treeModel.expandAll();
    TREE_ACTIONS.TOGGLE_ACTIVE_MULTI;
  }

  onChange(cur: TreeNode) {
    this.selectedNode = cur.data;
    this.entiteTreeComponent.treeModel.doForAll((node: TreeNode) => {
      if (node.data.id !== cur.data.id) {
        if (node.isActive) {
          node.toggleActivated(true);
        }
      } else {
        cur.toggleActivated(true);
      }
    });
  }
}
