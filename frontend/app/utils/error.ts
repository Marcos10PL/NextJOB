type Options = {
  login?: boolean;
  register?: boolean;
};

export const mapError = (errorCode: number, options?: Options) => {
  if (options?.login) {
    switch (errorCode) {
      case 400:
      case 401:
        return "Invalid email or password.";
    }
  }
  if (options?.register) {
    switch (errorCode) {
      case 400:
        return "Invalid registration details.";
      case 409:
        return "Email already in use.";
    }
  }
  return "An unexpected error occurred. Please try again.";
};
