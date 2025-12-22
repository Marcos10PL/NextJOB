import type { Application } from "~/types";

export const useApplications = () => {
  const { session } = useAuth();
  const applications = useState<Application[]>("applications", () => []);
  const checked = useState<boolean>("applications-checked", () => false);

  const fetchApplications = async () => {
    if (checked.value) return;

    if (!session.value.token) return [];

    try {
      const applicationsData = await $fetch<Application[]>(
        "/api/applications/me",
        {
          headers: { Authorization: `Bearer ${session.value.token}` },
        }
      );

      applications.value = applicationsData;
    } catch (error) {
      console.error("Error fetching applications:", error);
      applications.value = [];
    } finally {
      checked.value = true;
    }
  };

  return {
    applications,
    fetchApplications,
    checked,
  };
};
