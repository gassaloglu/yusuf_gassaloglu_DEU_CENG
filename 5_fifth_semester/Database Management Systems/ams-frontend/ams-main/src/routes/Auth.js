import { AuthProvider } from "../hooks/useAuth";
import { useOutlet } from "react-router-dom";

/**
 * This route wraps the outlet with an `AuthProvider`,
 * and it must be the root route of the browser.
 * 
 * The reason why we need this is that `AuthProvider`
 * should be under `RouteProvider`, but it should also
 * be on top of every route in the application, because
 * it provides an authentication context. So this route
 * must be the root route.
 * 
 * @returns { AuthProvider } An `AuthProvider` wrapping its outlet.
 */
export default function Auth() {
  const outlet = useOutlet();
  return <AuthProvider> {outlet} </AuthProvider>
}