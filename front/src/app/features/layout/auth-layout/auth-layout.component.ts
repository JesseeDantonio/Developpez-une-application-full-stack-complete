import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { AsyncPipe } from '@angular/common';
import { Component, inject } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { map } from 'rxjs';

@Component({
  selector: 'app-auth-layout',
  imports: [RouterModule, AsyncPipe],
  templateUrl: './auth-layout.component.html',
  styleUrl: './auth-layout.component.scss',
  standalone: true,
})
export class AuthLayoutComponent {
  public menuOpen = false;
  private breakpointObserver = inject(BreakpointObserver);

  isDesktop$ = this.breakpointObserver
    .observe([Breakpoints.Medium, Breakpoints.Large, Breakpoints.XLarge])
    .pipe(map((result) => result.matches));

  constructor(private router: Router) {}

  public closeMenu() {
    this.menuOpen = false;
  }

  public goToHome() {
    this.router.navigate(['/feed']);
  }
}
