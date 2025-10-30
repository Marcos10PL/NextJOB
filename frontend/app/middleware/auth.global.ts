export default defineNuxtRouteMiddleware(async to => {
  const { user, fetchUser } = useAuth();

  if (!user.value) {
    await fetchUser();
  }

  const protectedRoutes = ["/post-job", "/settings"];

  const isAdminRoute = to.path.startsWith("/admin");

  if (isAdminRoute) {
    if (!user.value || user.value.role !== "ADMIN") {
      return createError({
        statusCode: 404,
        statusMessage: "Page not found: " + to.path,
      });
    }
  }

  if (!user.value && protectedRoutes.some(route => to.path.startsWith(route))) {
    return createError({
      statusCode: 404,
      statusMessage: "Page not found: " + to.path,
    });
  }
});
