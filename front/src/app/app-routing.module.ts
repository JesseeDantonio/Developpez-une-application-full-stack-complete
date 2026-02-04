import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './features/auth/login/login.component';
import { RegisterComponent } from './features/auth/register/register.component';
import { HomeComponent } from './features/feed/home/home.component';
import { LayoutComponent } from './features/feed/layout/layout.component';

// consider a guard combined with canLoad / canActivate route option
// to manage unauthenticated user to access private routes
export const routes: Routes = [
  {
    path: '',
    component: HomeComponent,
    pathMatch: 'full' // <--- C'est la clé ! Cela signifie "Uniquement si l'URL est exactement vide"
  },

  // 2. Le Layout (Parent des pages d'auth)
  {
    path: '', // Ce path vide sert de "préfixe" pour les enfants
    component: LayoutComponent,
    children: [
      { path: 'login', component: LoginComponent },
      { path: 'register', component: RegisterComponent },
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
