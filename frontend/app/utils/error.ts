type Options = {
  login?: boolean;
  register?: boolean;
};

export const mapError = (errorCode: number, options?: Options) => {
  if (options?.login) {
    switch (errorCode) {
      case 400:
        return "Invalid email or password.";
      case 401:
        return "Unauthorized access. Please check your credentials.";
    }
  }
  if (options?.register) {
    switch (errorCode) {
      case 400:
        return "Invalid registration details.";
      case 409:
        return "Email already in use.";
      case 500:
        return "Server error. Please try again later.";
    }
  }
  return "An unexpected error occurred. Please try again.";
};
