import { Component, OnInit } from '@angular/core';
import { Payload } from 'src/app/core/models/payload.interface';
import { Theme } from 'src/app/core/models/theme.interface';
import { TokenService } from 'src/app/core/services/token.service';
import { ThemeService } from 'src/app/features/theme/services/theme.service';

@Component({
  selector: 'app-profil',
  imports: [],
  templateUrl: './profil-edit.component.html',
  styleUrl: './profil-edit.component.scss',
})
export class ProfilComponent implements OnInit {
  public themes: Theme[] = [];

  constructor(private themeService: ThemeService, private tokenService: TokenService) { }

  ngOnInit(): void {
    const payload: Payload | null = this.tokenService.getPayloadFromToken();

    if (payload == null) {
      return;
    }

    this.themeService.getAll().subscribe((themes) => {
      this.themeService.getSubscribedThemes(payload.sub).subscribe((subscribedThemes) => {
        themes.forEach((theme) => {
          const subscribed = subscribedThemes.some((subscribedTheme) => subscribedTheme.id == theme.id);
          if (subscribed) {
            this.themes.push(
              {
                id: theme.id,
                name: theme.name.toString(),
                description: theme.description.toString(),
                isSubscribed: subscribed
              }
            );
          }
        });
      });
    });
  }

}
