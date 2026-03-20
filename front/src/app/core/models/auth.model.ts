export interface LoginRequest {
  identifiant: string;
  password: string;
}

export interface RegisterRequest {
  name: string;
  email: string;
  password: string;
}

export interface AuthResponse {
  token: string;
}

export interface ArticleRequest {
  theme: string;
  title: string;
  content: string;
}