import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { ArticleService } from '../../services/article.service';
import { ArticleDto } from 'src/app/core/dto/in/articleDto';

@Component({
  selector: 'app-article',
  imports: [],
  templateUrl: './article-list.component.html',
  styleUrl: './article-list.component.scss',
})
export class ArticleComponent {

  public articlesList: ArticleDto[] = [];

  constructor(
    private router: Router,
    private articleService: ArticleService,
  ) {}

  ngOnInit() {
    const monObservable$ = this.articleService.getAll();

    monObservable$.subscribe((articles) => {
      console.log(articles);
      this.articlesList = articles;
    });
  }

  public goCreateArticle() {
    this.router.navigate(['/create-article']);
  }
}
