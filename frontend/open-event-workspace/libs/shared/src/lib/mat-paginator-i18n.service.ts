import {MatPaginatorIntl} from "@angular/material/paginator";
import {TranslateService} from "@ngx-translate/core";
import {Injectable} from "@angular/core";

const ITEMS_PER_PAGE = 'paginator.items';
const NEXT_PAGE = 'paginator.next';
const PREV_PAGE = 'paginator.prev';
const FIRST_PAGE = 'paginator.first';
const LAST_PAGE = 'paginator.last';

@Injectable({
  providedIn: 'root'
})
export class MatPaginatorI18nService extends MatPaginatorIntl {

  public constructor(private translate: TranslateService) {
    super();

    this.translate.onLangChange
      .subscribe((e: Event) => {
        this.getAndInitTranslations();
      });

    this.getAndInitTranslations();
  }

  public override getRangeLabel = (page: number, pageSize: number, length: number): string => {
    if (length === 0 || pageSize === 0) {
      return `0 / ${length}`;
    }

    length = Math.max(length, 0);

    const startIndex: number = page * pageSize;
    const endIndex: number = startIndex < length
      ? Math.min(startIndex + pageSize, length)
      : startIndex + pageSize;

    return `${startIndex + 1} - ${endIndex} / ${length}`;
  };

  public getAndInitTranslations(): void {
    this.translate.get([
      ITEMS_PER_PAGE,
      NEXT_PAGE,
      PREV_PAGE,
      FIRST_PAGE,
      LAST_PAGE,
    ])
      .subscribe((translation: any) => {
        this.itemsPerPageLabel = translation[ITEMS_PER_PAGE];
        this.nextPageLabel = translation[NEXT_PAGE];
        this.previousPageLabel = translation[PREV_PAGE];
        this.firstPageLabel = translation[FIRST_PAGE];
        this.lastPageLabel = translation[LAST_PAGE];

        this.changes.next();
      });
  }
}
