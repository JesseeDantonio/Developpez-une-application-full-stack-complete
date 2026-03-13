import { Component, OnInit } from '@angular/core';
import { LoginRequest } from 'src/app/core/models/auth.model';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthService } from 'src/app/core/services/auth/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
  imports: [ReactiveFormsModule],
  standalone: true
})
export class LoginComponent implements OnInit {
  loginForm!: FormGroup;

  constructor(private fb: FormBuilder, private authService: AuthService) {
    this.loginForm = this.fb.group({
      identifiant: ['', [Validators.required]],
      password: ['', Validators.required]
    });
  }

  ngOnInit(): void { }

  onSubmit() {
    console.log("Form submitted with values:", this.loginForm.value);
    if (this.loginForm.invalid) {
      return; 
    }
    const credentials: LoginRequest = {
      identifiant: this.loginForm.value.identifiant,
      password: this.loginForm.value.password
    };

    this.authService.login(credentials).subscribe({
      next: (response) => {
        console.log('Login successful');
      },
      error: (err) => console.error('Erreur login', err)
    });
  }
}
