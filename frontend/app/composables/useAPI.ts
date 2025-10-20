export const useAPI = (url: string, options: Record<string, unknown> = {}) => {
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

  return useFetch(url, {
    ...options,
    headers: headers.value,
    baseURL: useRuntimeConfig().apiUrl,
  });
};
