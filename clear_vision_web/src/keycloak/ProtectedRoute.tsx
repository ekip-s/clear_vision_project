import {useEffect} from "react";
import {Outlet} from "react-router";
import Loading from "../modules/molecules/loading/Loading.tsx";
import {useAuth} from "./useAuth.ts";

const ProtectedRoute = () => {
    const { isAuthenticated, login, loading } = useAuth();

    useEffect(() => {
        if (!loading && !isAuthenticated) {
            login();
        }
    }, [isAuthenticated, loading, login]);

    if (loading) {
        return <Loading />;
    }

    return isAuthenticated ? (<Outlet />) : (<Loading />);
};

export default ProtectedRoute;