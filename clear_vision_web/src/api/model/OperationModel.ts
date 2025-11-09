export interface AddOperation {
    categoryId: string | null;
    amount: number | null | undefined;
    description: string | null;
}

export interface Operation {
    id: string;
    accountName: string;
    currency: string;
    categoryName: string;
    status: string;
    amount: number;
    description: string | null;
    createdAt: string;
}