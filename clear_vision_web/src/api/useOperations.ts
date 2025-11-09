import axios from "axios";
import {useMutation, useQuery, useQueryClient} from "@tanstack/react-query";
import {useAuth} from "../keycloak/useAuth.ts";
import type {AddOperation, Operation} from "./model/OperationModel.ts";

const operationService = axios.create({
    baseURL: import.meta.env.VITE_ACCOUNT_SERVICE_URL,
    timeout: 10000,
});

export const useCreateOperation = (accountId: string | undefined) => {
    const queryClient = useQueryClient();
    const {getToken} = useAuth();

    return useMutation({
        mutationFn: async (data: AddOperation) => {
            const token = getToken();
            const response = await operationService.post(`/operation/api/v1/account/${accountId}`,
                data,
                {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                });
            return response.data;
        },
        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: ['accounts'] });
            queryClient.invalidateQueries({ queryKey: ['allOperations'] });
        },
    });
};

interface Pageable {
    page: number;
    size: number;
    sort?: string;
}

interface PaginatedResponse {
    content: Operation[];
    page: {
        totalElements: number;
        totalPages: number;
        number: number;
        size: number;
    }
}

export const useOperations = (pageable: Pageable) => {
    const { getToken } = useAuth();

    return useQuery<PaginatedResponse>({
        queryKey: ['allOperations', pageable.page, pageable.size, pageable.sort],
        queryFn: async () => {
            const token = getToken();

            const params: { [key: string]: string | number } = {
                page: pageable.page,
                size: pageable.size,
                sort: pageable.sort || 'createdAt,desc'
            };

            const response = await operationService.get(
                `/operation/api/v1/all`,
                {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                    params,
                }
            );
            return response.data;
        },
        placeholderData: (previousData) => previousData,
    });
};