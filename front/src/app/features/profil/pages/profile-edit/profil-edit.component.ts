import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { RegisterRequest } from 'src/app/core/models/auth.model';
import { Payload } from 'src/app/core/models/payload.interface';
import { Theme } from 'src/app/core/models/theme.interface';
import { TokenService } from 'src/app/core/services/token.service';
import { ThemeService } from 'src/app/features/theme/services/theme.service';
import { UserService } from 'src/app/features/user/services/user.service';

@Component({
  selector: 'app-profil',
  imports: [ReactiveFormsModule],
  templateUrl: './profil-edit.component.html',
  styleUrl: './profil-edit.component.scss',
})
export class ProfilComponent implements OnInit {
  public themes: Theme[] = [];
  public formGroup!: FormGroup;

  constructor(private themeService: ThemeService, private tokenService: TokenService, private fb: FormBuilder, private userService: UserService) {
    this.formGroup = this.fb.group({
      name: [''],
      email: [''],
      password: ['']
    });
  }

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

  onSubmit(): void {
    const payload: Payload | null = this.tokenService.getPayloadFromToken();

    if (payload == null) {
      return;
    }

    const updatedUserData = {
      name: this.formGroup.get('name')?.value,
      email: this.formGroup.get('email')?.value,
      password: this.formGroup.get('password')?.value
    };

    this.userService.updateProfile(updatedUserData).subscribe({
      next: (response) => {
        alert('Profil mis à jour avec succès !');
      },
      error: (error) => {
        alert('Erreur lors de la mise à jour du profil. Veuillez réessayer.');
      }
    });
  }
}
