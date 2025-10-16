import { joinURL } from "ufo";

export default defineEventHandler(event => {
  const apiUrl = useRuntimeConfig().apiUrl;
  const target = joinURL(apiUrl, event.path);

  return proxyRequest(event, target);
});
