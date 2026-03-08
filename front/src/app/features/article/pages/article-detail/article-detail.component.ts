import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ArticleService } from '../../services/article.service';
import { UserService } from 'src/app/features/user/services/user.service';
import { Article } from 'src/app/core/models/article.model';

@Component({
  selector: 'app-detail-article',
  imports: [],
  templateUrl: './article-detail.component.html',
  styleUrl: './article-detail.component.scss',
})
export class DetailArticleComponent implements OnInit {

  article: Article | null = null;

  constructor(private location: Location, private articleService: ArticleService, private userService: UserService) { }

  public goBack() {
    this.location.back();
  }

  ngOnInit(): void {
    const articleId = this.getArticleIdFromUrl();
    this.articleService.getById(articleId).subscribe((article) => {
      this.userService.getById(article.userId as unknown as number).subscribe((user) => {
        this.article = {
          id: article.id,
          title: article.title,
          content: article.content,
          userId: article.userId,
          createdAt: article.createdAt,
          updatedAt: article.updatedAt,
          themeIds: article.themeIds,
          userName: user.name
        }
      });
    });
  }


  public getArticleIdFromUrl(): number {
    const url = this.location.path();
    const segments = url.split('/');
    const id = segments[segments.length - 1];
    return parseInt(id, 10);
  }
}
