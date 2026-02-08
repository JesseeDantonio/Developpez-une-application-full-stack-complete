import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { RegisterRequest } from 'src/app/core/models/auth.model';
import { AuthService } from 'src/app/core/services/auth.service';

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
      password: ['', Validators.required]
    });
  }

  ngOnInit(): void {
  }

  onSubmit() {
    console.log("Form submitted with values:", this.registerForm.value);
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
        console.log('Registration successful', response.status);
      },
      error: (err) => console.error('Erreur register', err)
    });
  }
}
