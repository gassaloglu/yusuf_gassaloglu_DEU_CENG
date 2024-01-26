import { useAuth } from "../hooks/useAuth";
import { useOutlet, Navigate, useLocation } from "react-router-dom";

export function Protected({ Layout }) {
  const { user } = useAuth();
  const outlet = useOutlet();
  const location = useLocation();

  if (!user) return <Navigate to="/login" state={{ redirect: location.pathname }} replace />;

  return <Layout outlet={outlet} />
}

export function EmployeeOnly({ Layout }) {
  const { user } = useAuth();
  const outlet = useOutlet();
  const location = useLocation();

  if (!user || !user.is_employee)
    return <Navigate to="/login" state={{ redirect: location.pathname }} replace />;

  return <Layout outlet={outlet} />
}