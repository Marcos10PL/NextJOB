import type { User } from "~/types";

export const useAuth = () => {
  const token = useCookie("auth-token", {
    maxAge: 60 * 60 * 24 * 1, // 1 day
    secure: true,
    sameSite: "strict",
  });

  const user = useState<User | null>("auth-user", () => null);

  const setSession = (newToken: string) => {
    token.value = newToken;
  };

  const clearSession = () => {
    token.value = null;
    user.value = null;
  };

  const session = computed(() => ({
    token: token.value || null,
  }));

  const fetchUser = async () => {
    if (!token.value) return;

    try {
      const userData = await $fetch<User>("/api/users/me", {
        headers: { Authorization: `Bearer ${token.value}` },
      });
      user.value = userData;
    } catch (error) {
      console.error("Error fetching user data:", error);
      clearSession();
    }
  };

  return {
    setSession,
    clearSession,
    fetchUser,
    user,
    session,
  };
};
