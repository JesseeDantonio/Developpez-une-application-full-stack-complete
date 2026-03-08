import { Component } from '@angular/core';
import { ThemeDto } from 'src/app/core/dto/in/ThemeDto';
import { ThemeService } from '../../services/theme.service';

@Component({
  selector: 'app-theme',
  imports: [],
  templateUrl: './theme-list.component.html',
  styleUrl: './theme-list.component.scss',
})
export class ThemeComponent {

  public themes: ThemeDto[] = [];

  constructor(private themeService: ThemeService) { }

  ngOnInit(): void {
    this.themeService.getAll().subscribe((themes) => {
      this.themes = themes;
    });
  }

}
