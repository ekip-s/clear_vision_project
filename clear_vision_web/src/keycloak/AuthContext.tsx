import keycloak from "./keycloak";
import {createContext} from "react";

export interface User {
    id: string;
    username: string;
    email: string;
    firstName: string;
    lastName: string;
    token: string;
    roles: string[];
}

interface AuthContextType {
    isAuthenticated: boolean;
    user: User | null;
    keycloak: typeof keycloak;
    logout: () => void;
    loading: boolean;
    getToken: () => string | null;
    getUserId: () => string | null;
    getUsername: () => string | null;
    getUserRoles: () => string[];
    login: () => Promise<void>;
}

export const AuthContext = createContext<AuthContextType | null>(null);