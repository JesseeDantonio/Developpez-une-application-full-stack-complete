import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ArticleService } from '../../services/article.service';
import { UserService } from 'src/app/features/user/services/user.service';
import { Article } from 'src/app/core/models/article.model';
import { ThemeService } from 'src/app/features/theme/services/theme.service';
import { TokenService } from 'src/app/core/services/token.service';
import { Payload } from 'src/app/core/models/payload.model';
import { forkJoin } from 'rxjs';

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

// forkJoin permet d'exécuter plusieurs requêtes en parallèle et d'attendre leurs résultats
    forkJoin({
      articles: this.articleService.getAll(),
      users: this.userService.getAll(),
      themes: this.themeService.getSubscribedThemes(payload.sub)
    }).subscribe({
      next: (result) => {
        const tempArticles: Article[] = [];

        // On parcourt les articles récupérés
        for (let article of result.articles) {
          // On vérifie si l'article correspond à un thème souscrit
          const hasMatchingTheme = result.themes.some((t) => article.themeIds.includes(t.id));
          
          if (hasMatchingTheme) {
            const user = result.users.find((u) => u.id === article.userId as unknown as number);
            
            if (user) {
              tempArticles.push({
                id: article.id,
                title: article.title,
                content: article.content,
                userId: article.userId,
                createdAt: article.createdAt,
                updatedAt: article.updatedAt,
                themeIds: article.themeIds,
                userName: user.name
              });
            }
          }
        }

        // On assigne le tableau final TRIÉ à this.articles
        // Le tri se fait de manière garantie APRES avoir construit tout le tableau
        this.articles = this.sortByDateAsc(tempArticles);
      },
      error: (err) => {
        console.error('Erreur lors de la récupération des données', err);
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
