import type {ReactNode} from "react";
import {useAuth} from "./useAuth.ts";

interface PermissionGuardProps {
    permissions?: string[];
    children: ReactNode;
}

const PermissionGuard = ({
                             permissions = [],
                             children,
                         }: PermissionGuardProps) => {
    const { getUserRoles } = useAuth();

    const hasAccess = permissions.some((perm) => getUserRoles().includes(perm));

    if (!permissions.length || !hasAccess) {
        return null;
    }

    return <>{children}</>;
};

export default PermissionGuard;