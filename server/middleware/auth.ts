import { serverSupabaseUser } from "#supabase/server";

const apiProtectedRoutes = ["profiles"];

export default defineEventHandler(async event => {
  console.log("New request: " + getRequestURL(event));

  const urlName = getRequestURL(event).toString();

  if (urlName.includes("/api/")) {
    if (apiProtectedRoutes.some(route => urlName.includes(route))) {
      event.context.user = await serverSupabaseUser(event);

      if (!event.context.user) {
        throw createError({
          statusCode: 401,
          statusMessage: "Unauthorized",
        });
      }
    }
  }
});
