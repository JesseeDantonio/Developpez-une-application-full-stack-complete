import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-feed',
  imports: [],
  templateUrl: './feed.component.html',
  styleUrl: './feed.component.scss',
  standalone: true,
})
export class FeedComponent {

  
  constructor(private router: Router) {}
  public goToHome() {
    this.router.navigate(['/feed']);
  }
}
