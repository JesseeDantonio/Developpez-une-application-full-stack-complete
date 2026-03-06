export interface CreateArticleDto {
    title: String,
    content: String,
    userId: String,
    themeIds: number[]
}