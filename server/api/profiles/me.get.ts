import { serverSupabaseClient } from "#supabase/server";

export default defineEventHandler(async event => {
  const supabase = await serverSupabaseClient(event);

  const userId = event.context.user.id;

  const { data } = await supabase
    .from("profiles")
    .select("*, locations:locations!inner(*)")
    .eq("id", userId)
    .eq("locations.type", "profile");

  return data?.[0] || null;
});
