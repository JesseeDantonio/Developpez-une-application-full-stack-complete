export interface Payload {
    sub: number; // ID de l'utilisateur
    name: string; // Nom de l'utilisateur
    email: string; // Email de l'utilisateur
    exp: number; // Date d'expiration du token (timestamp)
}