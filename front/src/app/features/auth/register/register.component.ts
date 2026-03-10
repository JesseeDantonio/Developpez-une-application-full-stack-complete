import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, ReactiveFormsModule, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { RegisterRequest } from 'src/app/core/models/auth.model';
import { AuthService } from 'src/app/core/services/auth/auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
  standalone: true,
  imports: [ReactiveFormsModule]
})
export class RegisterComponent implements OnInit {

  registerForm!: FormGroup;

  constructor(private fb: FormBuilder, private authService: AuthService) {
    this.registerForm = this.fb.group({
      username: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', {
        validators: [Validators.required, this.passwordStrengthValidator()]
      }]
    });
  }

  ngOnInit(): void {
  }

  public passwordStrengthValidator(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      const value = control.value;
      if (!value) return null; // On laisse le validateur 'required' gérer le champ vide

      const hasUpperCase = /[A-Z]+/.test(value);
      const hasLowerCase = /[a-z]+/.test(value);
      const hasNumeric = /[0-9]+/.test(value);
      const hasSpecial = /[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]+/.test(value);
      const isValidLength = value.length >= 8;

      const passwordValid = hasUpperCase && hasLowerCase && hasNumeric && hasSpecial && isValidLength;

      if (!passwordValid) {
        // Si invalide, on retourne un objet avec le détail de ce qui manque
        return {
          passwordStrength: {
            hasUpperCase,
            hasLowerCase,
            hasNumeric,
            hasSpecial,
            isValidLength
          }
        };
      }
      return null; // Le mot de passe est valide
    };
  }

  get passwordErrors() {
    return this.registerForm.get('password')?.errors?.['passwordStrength'];
  }

  onSubmit() {
    if (this.registerForm.invalid) {
      return;
    }

    const credentials: RegisterRequest = {
      email: this.registerForm.value.email,
      password: this.registerForm.value.password,
      username: this.registerForm.value.username
    };

    this.authService.register(credentials).subscribe({
      next: (response) => {
      },
      error: (err) => console.error('Erreur register', err)
    });
  }
}
