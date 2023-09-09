import {Component} from '@angular/core';
import {ExportService} from "../model/export.service";
import {HttpResponse} from "@angular/common/http";
import FileSaver from "file-saver";

@Component({
  selector: 'app-board-card-export',
  templateUrl: './board-card-export.component.html',
  styleUrls: ['./board-card-export.component.scss']
})
export class BoardCardExportComponent {
  exporting: boolean = false

  constructor(private exportService: ExportService) {
  }

  export() {
    if (this.exporting) return
    this.exporting = true
    this.exportService.exportEvents().subscribe(r => this.handleExportResponse(r))
  }

  private handleExportResponse(response: HttpResponse<Blob>) {
    let contentDispositionHeader = response.headers.get("content-disposition")
    if (contentDispositionHeader) {
      let fileName = contentDispositionHeader.split(';')[1].trim().split('=')[1].replace(/"/g, '')
      let content = response.body
      if (content) FileSaver.saveAs(content, fileName)
    }
    this.exporting = false
  }
}
