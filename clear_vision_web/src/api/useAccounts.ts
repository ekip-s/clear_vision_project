import {useMutation, useQuery, useQueryClient} from '@tanstack/react-query';
import axios from 'axios';
import type {Account} from "./model/AccountModel.ts";
import {useAuth} from "../keycloak/useAuth.ts";

const accountService = axios.create({
    baseURL: import.meta.env.VITE_ACCOUNT_SERVICE_URL,
    timeout: 10000,
});

export const useAccounts = () => {
    const {getToken} = useAuth();

    return useQuery<Account[]>({
        queryKey: ['accounts'],
        queryFn: async () => {
            const token = getToken();

            const response = await accountService.get('/account/api/v1', {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            });
            return response.data;
        },
    });
};

export const useCreateAccount = () => {
    const queryClient = useQueryClient();

    return useMutation({
        mutationFn: async (data) => {
            const response = await accountService.post('/api/v1/accounts', data);
            return response.data;
        },
        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: ['accounts'] });
        },
    });
};