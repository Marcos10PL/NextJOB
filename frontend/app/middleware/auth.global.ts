export default defineNuxtRouteMiddleware(async to => {
  const { user, fetchUser } = useAuth();

  if (!user.value) {
    await fetchUser();
  }

  const protectedRoutes = [
    "/post-job",
    "/settings",
    "/settings/profile",
    "/settings/delete-account",
    "/settings/job-seeker-profile",
  ];

  const isAdminRoute = to.path.startsWith("/admin");

  if (isAdminRoute) {
    if (!user.value || user.value.role !== "ADMIN") {
      return createError({
        statusCode: 404,
        statusMessage: "Page not found: " + to.path,
      });
    }
  }

  if (!user.value && protectedRoutes.includes(to.path)) {
    return createError({
      statusCode: 404,
      statusMessage: "Page not found: " + to.path,
    });
  }
});
