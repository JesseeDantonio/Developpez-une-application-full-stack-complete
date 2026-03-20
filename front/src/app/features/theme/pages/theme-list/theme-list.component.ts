import { Component } from '@angular/core';
import { ThemeService } from '../../services/theme.service';
import { TokenService } from 'src/app/core/services/token.service';
import { Theme } from 'src/app/core/models/theme.model';

@Component({
  selector: 'app-theme',
  imports: [],
  templateUrl: './theme-list.component.html',
  styleUrl: './theme-list.component.scss',
})
export class ThemeComponent {
  public loadingThemes = new Set<number>();
  public themes: Theme[] = [];

  constructor(
    private themeService: ThemeService,
    private tokenService: TokenService
  ) { }

  ngOnInit(): void {
    const payload = this.tokenService.getPayloadFromToken();

    if (payload == null) {
      return;
    }

    this.themeService.getAll().subscribe((themes) => {
      this.themeService.getSubscribedThemes(payload.sub).subscribe((subscribedThemes) => {
        themes.forEach((theme) => {
          this.themes.push(
            {
              id: theme.id,
              name: theme.name.toString(),
              description: theme.description.toString(),
              isSubscribed: subscribedThemes.some((subscribedTheme) => subscribedTheme.id == theme.id)
            }
          );
        });
      });
    });
  }

  public subscribeToTheme(themeId: number): void {
    const payload = this.tokenService.getPayloadFromToken();

    if (payload == null) {
      return;
    }

    this.loadingThemes.add(Number(themeId));

    try {
      this.themeService.subscribe(Number(themeId)).subscribe(() => {
        const theme = this.themes.find((t) => t.id == themeId);
        if (theme) {
          theme.isSubscribed = true;
          this.loadingThemes.delete(Number(themeId));
        }
      });
    }
    catch (e) {
      console.error('Error subscribing to theme', e);
    }
    finally {
      this.loadingThemes.delete(Number(themeId));
    }
  }

  public unsubscribeFromTheme(themeId: number): void {
    const payload = this.tokenService.getPayloadFromToken();

    if (payload == null) {
      return;
    }

    this.loadingThemes.add(Number(themeId));

    try {
      this.themeService.unsubscribe(Number(themeId)).subscribe(() => {
        const theme = this.themes.find((t) => t.id == themeId);
        if (theme) {
          theme.isSubscribed = false;
          this.loadingThemes.delete(Number(themeId));
        }
      });
    } catch (e) {
      console.error('Error unsubscribing from theme', e);
    } finally {
      this.loadingThemes.delete(Number(themeId));
    }

  }
}
