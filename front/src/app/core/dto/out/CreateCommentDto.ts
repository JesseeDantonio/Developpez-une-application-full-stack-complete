export interface CreateCommentDto {
    articleId: number | undefined;
    userId: number | undefined;
    content: string;
}