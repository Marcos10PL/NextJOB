export function mapSalaryRange(
  salaryFrom: number,
  salaryTo: number,
  paymentType: string
): string {
  return salaryFrom === salaryTo
    ? `${salaryFrom} € / ${paymentType}`
    : `${salaryFrom} - ${salaryTo} € / ${paymentType}`;
}
