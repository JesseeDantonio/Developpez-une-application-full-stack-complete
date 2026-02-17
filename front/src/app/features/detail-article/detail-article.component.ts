import { Location } from '@angular/common';
import { Component } from '@angular/core';

@Component({
  selector: 'app-detail-article',
  imports: [],
  templateUrl: './detail-article.component.html',
  styleUrl: './detail-article.component.scss',
})
export class DetailArticleComponent {
  constructor(private location: Location) {}

  public goBack() {
    this.location.back();
  }
}
