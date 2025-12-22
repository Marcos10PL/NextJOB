import { joinURL } from "ufo";

export default defineEventHandler(event => {
  const apiUrl = process.env.API_URL || "http://localhost:8080";
  const target = joinURL(apiUrl, event.path);

  return proxyRequest(event, target);
});
