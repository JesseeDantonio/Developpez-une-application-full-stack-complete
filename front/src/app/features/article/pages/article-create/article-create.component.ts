import { Component } from '@angular/core';
import { Location } from '@angular/common';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { ArticleService } from '../../services/article.service';
import { CreateArticleDto } from 'src/app/core/dto/out/CreateArticleDto';
import { ThemeService } from 'src/app/features/theme/services/theme.service';
import { ThemeDto } from 'src/app/core/dto/in/themeDto';

@Component({
  selector: 'app-create-article',
  imports: [ReactiveFormsModule],
  templateUrl: './article-create.component.html',
  styleUrl: './article-create.component.scss',
})
export class CreateArticleComponent {
  articleCreateForm!: FormGroup;
  themesList: ThemeDto[] = []; 
  constructor(
    private fb: FormBuilder,
    private location: Location,
    private articleService: ArticleService,
    private themeService: ThemeService,
  ) {
    this.articleCreateForm = this.fb.group({
      theme: ['', Validators.required],
      title: ['', Validators.required],
      content: ['', Validators.required],
    });
  }

  ngOnInit() {
    this.themeService.getAll().subscribe({
      next: (res) => {
        console.log('Themes fetched successfully:', res);
        this.themesList = res;
      },
      error: (error) => {
        console.error('Error fetching themes:', error);
      },
    });
  }

  public goBack() {
    this.location.back();
  }

  onSubmit() {
    // console.log("Form submitted with values:", this.articleCreateForm.value);
    if (this.articleCreateForm.invalid) {
      return;
    }

    const articleData: CreateArticleDto = {
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
  }
}
