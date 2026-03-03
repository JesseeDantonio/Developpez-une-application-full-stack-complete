import { Component } from '@angular/core';
import { AsyncPipe, Location } from '@angular/common';

@Component({
  selector: 'app-create-article',
  imports: [],
  templateUrl: './article-create.component.html',
  styleUrl: './article-create.component.scss',
})
export class CreateArticleComponent {


constructor(private location: Location) {}



  public goBack() {
    this.location.back();
  }
}
