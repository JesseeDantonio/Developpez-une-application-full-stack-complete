import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ArticleService } from '../../services/article.service';
import { UserService } from 'src/app/features/user/services/user.service';
import { Article } from 'src/app/core/models/article.model';
import { ThemeService } from 'src/app/features/theme/services/theme.service';
import { CommentService } from 'src/app/features/comments/services/comment.service';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthService } from 'src/app/core/services/auth/auth.service';
import { Comment } from 'src/app/core/models/comment.model';
import { Payload } from 'src/app/core/models/payload.interface';

@Component({
  selector: 'app-detail-article',
  imports: [ReactiveFormsModule],
  templateUrl: './article-detail.component.html',
  styleUrl: './article-detail.component.scss',
})
export class DetailArticleComponent implements OnInit {

  public themeNames: String[] = [];
  public comments: Comment[] = [];
  public commentCreateForm!: FormGroup;

  article: Article | null = null;

  constructor(private location: Location,
    private articleService: ArticleService,
    private userService: UserService,
    private themeService: ThemeService,
    private commentService: CommentService,
    private authService: AuthService,
    private fb: FormBuilder,
  ) {
    this.commentCreateForm = this.fb.group({
      content: ['', Validators.required],
    });
  }

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

      this.themeService.getAll().subscribe((themes) => {
        if (this.article) {
          this.article.themeIds?.forEach((themeId) => {
            const theme = themes.find(t => t.id === themeId);
            if (theme) {
              this.themeNames.push(theme.name);
            }
          });
        }
      });

      this.commentService.getAll().subscribe((comments) => {
        for (let comment of comments) {
          if (comment.articleId === articleId) {

            this.userService.getById(comment.userId as unknown as number).subscribe((user) => {
              this.comments.push({
                id: comment.id,
                content: comment.content,
                userId: comment.userId,
                articleId: comment.articleId,
                createdAt: comment.createdAt,
                userName: user.name
              });
            });
          };
        }
      });
    });
  }

  onSubmit() {
    if (this.commentCreateForm.invalid) {
      return;
    }

    const payload : Payload | null = this.authService.getPayloadFromToken();;
    if (payload === null) {
      return;
    }

    this.userService.getById(payload.sub).subscribe((user) => {
      if (!user) {
        return;
      }
    });

    const commentData = {
      articleId: this.article?.id,
      userId: payload.sub.toString(),
      content: this.commentCreateForm.value.content,
    };

    this.commentService.create(commentData).subscribe((newComment) => {
      this.userService.getById(newComment.userId as unknown as number).subscribe((user) => {

        this.comments.push({
          id: newComment.id,
          content: newComment.content,
          userId: newComment.userId,
          articleId: newComment.articleId,
          createdAt: newComment.createdAt,
          userName: user.name
        });
      });
      this.commentCreateForm.reset();
    });
  }

  private getArticleIdFromUrl(): number {
    const url = this.location.path();
    const segments = url.split('/');
    const id = segments[segments.length - 1];
    return parseInt(id, 10);
  }


}
