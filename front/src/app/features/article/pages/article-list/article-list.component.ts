import { Component, OnInit} from '@angular/core';
import { Router } from '@angular/router';
import { ArticleService } from '../../services/article.service';
import { ArticleDto } from 'src/app/core/dto/in/articleDto';
import { CommonModule } from '@angular/common';

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
    private articleService: ArticleService
  ) {}

  ngOnInit() {
    this.articleService.getAll().subscribe((articles) => {
      for (let article of articles) {
        // Afficher le nom des utilisateurs au lieu de l'id
      }
      this.articles = articles;
    });
  }

  public goCreateArticle() {
    this.router.navigate(['/create-article']);
  }

  public goArticleDetail(id: number) {
    this.router.navigate(['/detail-article', id]);
  }


}
