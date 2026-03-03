export interface ArticleDto {
    id: number,
    title: String,
    content: String,
    userId: String,
    createdAt: String,
    updatedAt: String,

    themeIds: number[]
}