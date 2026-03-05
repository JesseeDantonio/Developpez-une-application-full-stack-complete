import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './features/auth/login/login.component';
import { RegisterComponent } from './features/auth/register/register.component';
import { HomeComponent } from './features/home/home.component';
import { LayoutComponent } from './features/layout/layout.component';
import { AuthLayoutComponent } from './features/layout/auth-layout/auth-layout.component';
import { ArticleComponent } from './features/article/pages/article-list/article-list.component';
import { ThemeComponent } from './features/theme/theme.component';
import { CreateArticleComponent } from './features/article/pages/article-create/article-create.component';
import { DetailArticleComponent } from './features/article/pages/article-detail/article-detail.component';
import { ProfilComponent } from './features/profil/profil.component';
import { unauthGuard } from './core/guards/unauth.guard';
import { authGuard } from './core/guards/auth.guard';

// consider a guard combined with canLoad / canActivate route option
// to manage unauthenticated user to access private routes
export const routes: Routes = [
  {
    path: '',
    component: HomeComponent,
    canActivate: [unauthGuard],
    pathMatch: 'full' // <--- C'est la clé ! Cela signifie "Uniquement si l'URL est exactement vide"
  },

  // Le Layout (Parent des pages d'auth)
  {
    path: '', // Ce path vide sert de "préfixe" pour les enfants
    canActivate: [unauthGuard], // Seules les personnes non authentifiées peuvent accéder à ces routes
    component: LayoutComponent,
    children: [
      { path: 'login', component: LoginComponent },
      { path: 'register', component: RegisterComponent },
    ]
  },

  {
    path: '',
    canActivate: [authGuard], // Seules les personnes authentifiées peuvent accéder à ces routes
    component: AuthLayoutComponent,
    children: [
      { path: 'article', component: ArticleComponent },
      { path: 'theme', component: ThemeComponent },
      { path: 'create-article', component: CreateArticleComponent },
      { path: 'detail-article/:id', component: DetailArticleComponent },
      { path: 'profil', component: ProfilComponent }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
