export default defineNuxtRouteMiddleware(async to => {
  const user = useSupabaseUser();

  const protectedRoutes = ["/"];

  if (user.value && !protectedRoutes.includes(to.path)) {
    return navigateTo("/");
  }

  if (!user.value && protectedRoutes.includes(to.path)) {
    return navigateTo("/login");
  }
});
