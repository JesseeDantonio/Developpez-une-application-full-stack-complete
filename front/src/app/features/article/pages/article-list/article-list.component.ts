import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ArticleService } from '../../services/article.service';
import { UserService } from 'src/app/features/user/services/user.service';
import { Article } from 'src/app/core/models/article.model';

@Component({
  selector: 'app-article',
  imports: [],
  templateUrl: './article-list.component.html',
  styleUrl: './article-list.component.scss',
})
export class ArticleComponent implements OnInit {

  public articles: Article[] = [];

  constructor(
    private router: Router,
    private articleService: ArticleService,
    private userService: UserService
  ) { }

  ngOnInit() {
    this.articleService.getAll().subscribe((articles) => {
      this.userService.getAll().subscribe((users) => {
        for (let article of articles) {
          const user = users.find((u) => u.id === article.userId as unknown as number);
          if (user) {
            this.articles.push({
              id: article.id,
              title: article.title,
              content: article.content,
              userId: article.userId,
              createdAt: article.createdAt,
              userName: user.name
            })
          }
        }
      });
    });
  }

  public goCreateArticle() {
    this.router.navigate(['/create-article']);
  }

  public goArticleDetail(id: number) {
    this.router.navigate(['/detail-article', id]);
  }
}
