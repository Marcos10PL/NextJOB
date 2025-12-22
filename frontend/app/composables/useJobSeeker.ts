import type { JobSeeker } from "~/types";

export const useJobSeeker = () => {
  const { session } = useAuth();
  const jobSeeker = useState<JobSeeker | null>("job-seeker", () => null);
  const checked = useState<boolean>("job-seeker-checked", () => false);

  const isJobSeekerExists = computed({
    get: () => !!jobSeeker.value,
    set: (value: boolean) => {
      if (!value) {
        jobSeeker.value = null;
      }
    },
  });

  const fetchJobSeeker = async () => {
    if (checked.value || !session.value.token) return;

    try {
      const jobSeekerData = await $fetch<JobSeeker>("/api/job-seekers/me", {
        headers: { Authorization: `Bearer ${session.value.token}` },
      });
      jobSeeker.value = jobSeekerData;
    } catch (error) {
      console.error("Error fetching user data:", error);
      jobSeeker.value = null;
    } finally {
      checked.value = true;
    }
  };

  return {
    jobSeeker,
    fetchJobSeeker,
    isJobSeekerExists,
  };
};
