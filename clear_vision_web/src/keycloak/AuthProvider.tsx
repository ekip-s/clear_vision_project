import keycloak from "./keycloak";
import {type FC, type ReactNode, useCallback, useEffect, useRef, useState} from "react";
import Loading from "../modules/molecules/loading/Loading.tsx";
import {AuthContext, type User} from "./AuthContext.tsx";



export const AuthProvider: FC<{ children: ReactNode }> = ({ children }) => {
    const [isAuthenticated, setIsAuthenticated] = useState(false);
    const [user, setUser] = useState<User | null>(null);
    const [loading, setLoading] = useState(true);
    const [authCheckCompleted, setAuthCheckCompleted] = useState(false);
    const isInitialized = useRef(false);
    const tokenRefreshInterval = useRef<number | undefined>(undefined);

    const updateUserData = useCallback(() => {
        const userData: User = {
            id: keycloak.subject || '',
            username: keycloak.tokenParsed?.preferred_username || '',
            email: keycloak.tokenParsed?.email || '',
            firstName: keycloak.tokenParsed?.given_name || '',
            lastName: keycloak.tokenParsed?.family_name || '',
            token: keycloak.token || '',
            roles: keycloak.tokenParsed?.realm_access?.roles || [],
        };
        setUser(userData);
        setIsAuthenticated(true);
    }, []);

    const logout = useCallback(() => {
        setLoading(true);
        keycloak.logout().finally(() => {
            setIsAuthenticated(false);
            setUser(null);
            setLoading(false);
        });
    }, []);

    useEffect(() => {
        if (!isInitialized.current) {
            isInitialized.current = true;

            const initializeKeycloak = async () => {
                try {
                    setLoading(true);
                    const authenticated = await keycloak.init({
                        onLoad: 'check-sso',
                        pkceMethod: 'S256',
                        checkLoginIframe: false,
                    });

                    if (authenticated) {
                        updateUserData();
                        keycloak.onTokenExpired = () => {
                            keycloak.updateToken(30)
                                .then(refreshed => {
                                    if (refreshed) {
                                        updateUserData();
                                        console.log('Token auto-refreshed');
                                    }
                                })
                                .catch(logout);
                        };

                        tokenRefreshInterval.current = window.setInterval(() => {
                            keycloak.updateToken(70)
                                .then(refreshed => {
                                    if (refreshed) {
                                        updateUserData();
                                        console.log('Token refreshed by interval');
                                    }
                                })
                                .catch(logout);
                        }, 60000);
                    }
                } catch (error) {
                    console.error('Keycloak init error:', error);
                } finally {
                    setLoading(false);
                    setAuthCheckCompleted(true);
                }
            };

            initializeKeycloak();

            keycloak.onAuthSuccess = () => {
                console.log('Auth Success');
                updateUserData();
                setLoading(false);
            };

            keycloak.onAuthError = () => {
                console.error('Auth Error');
                setLoading(false);
            };
        }

        return () => {
            if (tokenRefreshInterval.current) {
                clearInterval(tokenRefreshInterval.current);
            }
        };
    }, [updateUserData, logout]);

    const login = useCallback(async () => {
        try {
            setLoading(true);
            await keycloak.login();
        } catch (error) {
            console.error('Login failed:', error);
            setLoading(false);
        }
    }, []);

    const getToken = useCallback(() => user?.token || null, [user]);
    const getUserId = useCallback(() => user?.id || null, [user]);
    const getUsername = useCallback(() => user?.firstName || null, [user]);
    const getUserRoles = useCallback(() => user?.roles || [], [user]);

    return (
        <AuthContext.Provider
            value={{
                isAuthenticated: authCheckCompleted && isAuthenticated,
                user,
                keycloak,
                logout,
                loading,
                getToken,
                getUserId,
                getUsername,
                getUserRoles,
                login,
            }}
        >
            {loading ? <Loading /> : children}
        </AuthContext.Provider>
    );
};