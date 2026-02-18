import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './features/auth/login/login.component';
import { RegisterComponent } from './features/auth/register/register.component';
import { HomeComponent } from './features/home/home.component';
import { LayoutComponent } from './features/layout/layout.component';
import { AuthLayoutComponent } from './features/layout/auth-layout/auth-layout.component';
import { ArticleComponent } from './features/article/article.component';
import { ThemeComponent } from './features/theme/theme.component';
import { CreateArticleComponent } from './features/create-article/create-article.component';
import { DetailArticleComponent } from './features/detail-article/detail-article.component';

// consider a guard combined with canLoad / canActivate route option
// to manage unauthenticated user to access private routes
export const routes: Routes = [
  {
    path: '',
    component: HomeComponent,
    pathMatch: 'full' // <--- C'est la clé ! Cela signifie "Uniquement si l'URL est exactement vide"
  },

  // Le Layout (Parent des pages d'auth)
  {
    path: '', // Ce path vide sert de "préfixe" pour les enfants
    component: LayoutComponent,
    children: [
      { path: 'login', component: LoginComponent },
      { path: 'register', component: RegisterComponent },
    ]
  },

  {
    path: '',
    component: AuthLayoutComponent,
    children: [
      { path: 'article', component: ArticleComponent },
      { path: 'theme', component: ThemeComponent },
      { path: 'create-article', component: CreateArticleComponent },
      { path: 'detail-article', component: DetailArticleComponent }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
