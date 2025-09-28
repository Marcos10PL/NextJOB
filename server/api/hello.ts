export default defineEventHandler(async event => {
  // const { category, limit } = getQuery(event);

  // let query = serverSupabase().from("instruments").select("*");

  // if (category) {
  //   query = query.eq("category", category);
  // }

  // if (limit) {
  //   query = query.limit(Number(limit));
  // }

  // const { data, error } = await query;

  // if (error) {
  //   throw createError({
  //     statusCode: 400,
  //     statusMessage: error.message,
  //   });
  // }

  // return data;
  return { message: 'Hello World' };
});
