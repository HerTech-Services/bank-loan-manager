import { TreeItem, TreeviewItem } from 'ngx-treeview';
import { LogService } from 'app/log.service';

export class CustomTreeviewItem extends TreeviewItem {
  logService: LogService = new LogService();
  constructor(item: TreeItem, autoCorrectChecked?: boolean) {
    super(item, autoCorrectChecked);
  }

  getSelection() {
    const items = super.getSelection();
    this.logService.log('items selected');
    this.logService.log(this.value);
    this.logService.log('checkeds items');
    items['checkedItems'].forEach(item => {
      this.logService.log(item.value);
    });

    this.logService.log('Uncheckeds items');
    items['uncheckedItems'].forEach(item => {
      this.logService.log(item.value);
    });
    if (this.checked && this.children) {
      items['checkedItems'].push(this);
    } else {
      items['uncheckedItems'].push(this);
    }
    return items;
  }
}
