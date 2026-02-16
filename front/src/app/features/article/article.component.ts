import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-article',
  imports: [],
  templateUrl: './article.component.html',
  styleUrl: './article.component.scss',
})
export class ArticleComponent {

  constructor(private router: Router) {}

  public goCreateArticle() {
    this.router.navigate(['/create-article']);
  }
}
