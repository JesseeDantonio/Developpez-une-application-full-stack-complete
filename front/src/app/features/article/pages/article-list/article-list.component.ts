import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ArticleService } from '../../services/article.service';
import { UserService } from 'src/app/features/user/services/user.service';
import { Article } from 'src/app/core/models/article.model';
import { ThemeService } from 'src/app/features/theme/services/theme.service';
import { TokenService } from 'src/app/core/services/token.service';
import { Payload } from 'src/app/core/models/payload.interface';

@Component({
  selector: 'app-article',
  imports: [],
  templateUrl: './article-list.component.html',
  styleUrl: './article-list.component.scss',
})
export class ArticleComponent implements OnInit {

  public articles: Article[] = [];
  public sortByAsc: boolean = true;

  constructor(
    private router: Router,
    private articleService: ArticleService,
    private userService: UserService,
    private themeService: ThemeService,
    private tokenService: TokenService
  ) { }

  sortByDateDesc(articles: Article[]): Article[] {
    return articles.sort((a, b) => new Date(b.createdAt.toString()).getTime() - new Date(a.createdAt.toString()).getTime());
  }

  sortByDateAsc(articles: Article[]): Article[] {
    return articles.sort((a, b) => new Date(a.createdAt.toString()).getTime() - new Date(b.createdAt.toString()).getTime());
  }

  toggleSortOrder() {
    this.sortByAsc = !this.sortByAsc;

    if (this.sortByAsc) {
      this.articles = this.sortByDateAsc(this.articles);
    } else {
      this.articles = this.sortByDateDesc(this.articles);
    }
  }

  ngOnInit() {

    const payload: Payload | null = this.tokenService.getPayloadFromToken();

    if (payload == null) {
      return;
    }

    this.articleService.getAll().subscribe((articles) => {
      this.userService.getAll().subscribe((users) => {
        for (let article of articles) {

          this.themeService.getSubscribedThemes(payload.sub).subscribe((themes) => {
            if (themes.some((t) => article.themeIds.includes(t.id))) {
              const user = users.find((u) => u.id === article.userId as unknown as number);
              if (user) {
                this.articles.push({
                  id: article.id,
                  title: article.title,
                  content: article.content,
                  userId: article.userId,
                  createdAt: article.createdAt,
                  updatedAt: article.updatedAt,
                  themeIds: article.themeIds,
                  userName: user.name
                })
              }
            }
          });
        }
      });
      if (this.sortByAsc) {
        this.articles = this.sortByDateAsc(this.articles);
      }
    });
  }

  public goCreateArticle() {
    this.router.navigate(['/create-article']);
  }

  public goArticleDetail(id: number) {
    this.router.navigate(['/detail-article', id]);
  }
}
