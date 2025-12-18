export function parseTimestamp(timestamp: string): string {
  const parsedDate = new Date(timestamp);
  const formattedTimestamp =
    `${parsedDate.getFullYear()}-` +
    `${(parsedDate.getMonth() + 1).toString().padStart(2, "0")}-` +
    `${parsedDate.getDate().toString().padStart(2, "0")} ` +
    `${parsedDate.getHours().toString().padStart(2, "0")}:` +
    `${parsedDate.getMinutes().toString().padStart(2, "0")}:` +
    `${parsedDate.getSeconds().toString().padStart(2, "0")}`;

  return formattedTimestamp;
}

export function parseDate(date: string): string {
  const parsedDate = new Date(date);
  const options: Intl.DateTimeFormatOptions = {
    month: "long",
    day: "numeric",
    year: "numeric",
  };
  return parsedDate.toLocaleDateString("en-US", options);
}

export function parseReviewStatus(status: "approved" | "pending" | "rejected"): {style: string, text: string} {
  switch (status) {
    case "approved":
      return { style: "text-green-600", text: "Approved" };
    case "pending":
      return { style: "text-amber-400", text: "Pending" };
    case "rejected":
      return { style: "text-red-600", text: "Rejected" };
  }
}
