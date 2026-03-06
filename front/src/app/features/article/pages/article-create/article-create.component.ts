import { Component, OnInit } from '@angular/core';
import { AsyncPipe, Location } from '@angular/common';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { ArticleService } from '../../services/article.service';
import { CreateArticleDto } from 'src/app/core/dto/out/CreateArticleDto';
import { ThemeService } from 'src/app/features/theme/services/theme.service';
import { ThemeDto } from 'src/app/core/dto/in/ThemeDto';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/core/services/auth/auth.service';
import { Observable } from 'rxjs/internal/Observable';

@Component({
  selector: 'app-create-article',
  imports: [ReactiveFormsModule, AsyncPipe],
  templateUrl: './article-create.component.html',
  styleUrl: './article-create.component.scss',
})
export class CreateArticleComponent implements OnInit {
  public articleCreateForm!: FormGroup;
  public themes: Observable<ThemeDto[]> | null = null;

  constructor(
    private fb: FormBuilder,
    private location: Location,
    private router: Router,
    private articleService: ArticleService,
    private themeService: ThemeService,
    private authService: AuthService
  ) {
    this.articleCreateForm = this.fb.group({
      theme: ['', Validators.required],
      title: ['', Validators.required],
      content: ['', Validators.required],
    });
  }

  ngOnInit() {
     this.themes = this.themeService.getAll();
  }

  public goBack() {
    this.location.back();
  }

  onSubmit() {
    if (this.articleCreateForm.invalid) {
      return;
    }

    const userId : string | null = this.authService.getUserIdFromToken();
    console.log('User ID extracted from token:', userId);
    if (userId === null) {
      console.error('User ID not found in token. Please log in again.');
      return;
    }

    const articleData: CreateArticleDto = {
      userId: userId,
      title: this.articleCreateForm.value.title,
      content: this.articleCreateForm.value.content,
      themeIds: this.articleCreateForm.value.theme
        .split(',')
        .map((id: string) => parseInt(id.trim())), // Convertit la chaîne de thèmes en tableau d'entiers
    };

    this.articleService.create(articleData).subscribe({
      next: (response) => {
        console.log('Article created successfully:', response);
        this.goBack();
      },
      error: (error) => {
        console.error('Error creating article:', error);
      },
    });

    this.router.navigate(['/articles']);
  }
}
