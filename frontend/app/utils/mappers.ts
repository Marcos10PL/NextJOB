export type Option = {
  value: number;
  label: string;
};

export function mapToOptions(
  data: { id: number; name: string }[],
  labels: Record<string, string>
): Option[] {
  return data.map(item => ({
    value: Number(item.id),
    label: labels[item.name] || item.name,
  }));
}
