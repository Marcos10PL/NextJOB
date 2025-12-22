export const useAPI = <T>(
  url: string,
  options: Record<string, unknown> = {}
) => {
  const { session } = useAuth();

  const headers = computed<HeadersInit>(() => {
    const baseHeaders = (options.headers as HeadersInit) || {};
    if (session.value?.token) {
      return {
        ...baseHeaders,
        Authorization: `Bearer ${session.value.token}`,
      };
    }
    return baseHeaders;
  });

  return useFetch<T>(url, {
    ...options,
    headers: headers.value,
    baseURL: process.env.API_BASE_URL || "http://localhost:3000",
  });
};
