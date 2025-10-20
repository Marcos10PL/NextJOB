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

  if (!user.value && protectedRoutes.includes(to.path)) {
    return createError({
      statusCode: 404,
      statusMessage: "Page not found: " + to.path,
    });
  }
});
