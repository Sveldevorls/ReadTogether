import { useToast, type ToastMessageOptions } from "primevue";

function useSingularToast() {
  const toast = useToast();
  let isShowing = false;
  let timerId: number | null = null;

  return (message: ToastMessageOptions) => {
    if (!isShowing) {
      toast.add({ ...message, group: "message" });
      isShowing = true;
    } else {
      if (timerId != null) {
        clearTimeout(timerId);
      }
      toast.removeGroup("message");
      setTimeout(() => {
        toast.add({ ...message, group: "message" });
      }, 200);
    }
    timerId = setTimeout(() => {
      isShowing = false;
      timerId = null;
    }, (message.life ?? 3000) + (isShowing ? 200 : 0));
  };
}

export { useSingularToast };
