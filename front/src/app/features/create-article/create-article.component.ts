import { Component } from '@angular/core';
import { AsyncPipe, Location } from '@angular/common';

@Component({
  selector: 'app-create-article',
  imports: [],
  templateUrl: './create-article.component.html',
  styleUrl: './create-article.component.scss',
})
export class CreateArticleComponent {


constructor(private location: Location) {}



  public goBack() {
    this.location.back();
  }
}
