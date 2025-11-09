import axios from "axios";
import {useAuth} from "../keycloak/useAuth.ts";
import {useQuery} from "@tanstack/react-query";
import type {Category} from "./model/CategoryModel.ts";

const categoryService = axios.create({
    baseURL: import.meta.env.VITE_ACCOUNT_SERVICE_URL,
    timeout: 10000,
});

export const useCategory = () => {
    const {getToken} = useAuth();

    return useQuery<Category[]>({
        queryKey: ['category'],
        queryFn: async () => {
            const token = getToken();

            const response = await categoryService.get('/category/api/v1', {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            });
            return response.data;
        },
    });
};