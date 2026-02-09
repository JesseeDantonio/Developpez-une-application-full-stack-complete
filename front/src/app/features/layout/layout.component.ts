import { Component, inject, OnInit } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { AsyncPipe, Location } from '@angular/common';
import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { map } from 'rxjs';
@Component({
  selector: 'app-layout',
  templateUrl: './layout.component.html',
  styleUrls: ['./layout.component.scss'],
  standalone: true,
  imports: [RouterModule, AsyncPipe],
})
export class LayoutComponent implements OnInit {
  private breakpointObserver = inject(BreakpointObserver);
  public menuOpen = false;

  isDesktop$ = this.breakpointObserver
    .observe([Breakpoints.Medium, Breakpoints.Large, Breakpoints.XLarge])
    .pipe(map((result) => result.matches));

  constructor(
    private location: Location,
    private router: Router,
  ) {}

  ngOnInit(): void {}

  public goToHome() {
    this.router.navigate(['/']);
  }

  public goBack() {
    this.location.back();
  }
}
