import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ArticleDto } from 'src/app/core/dto/in/articleDto';
import { ArticleService } from '../../services/article.service';

@Component({
  selector: 'app-detail-article',
  imports: [],
  templateUrl: './article-detail.component.html',
  styleUrl: './article-detail.component.scss',
})
export class DetailArticleComponent implements OnInit {

  article: ArticleDto | null = null;

  constructor(private location: Location, private articleService: ArticleService) { }

  public goBack() {
    this.location.back();
  }

  ngOnInit(): void {
    const articleId = this.getArticleIdFromUrl();
    this.articleService.getById(articleId).subscribe((article) => {
      this.article = article;
    });
  }


  public getArticleIdFromUrl(): number {
    const url = this.location.path();
    const segments = url.split('/');
    const id = segments[segments.length - 1];
    return parseInt(id, 10);
  }
}
