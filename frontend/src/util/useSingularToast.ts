import { useToast, type ToastMessageOptions } from "primevue";

let isShowing = false;
let resetTimerId: number | null = null;
let addTimerId: number | null = null;

function useSingularToast() {
  const toast = useToast();

  return (message: ToastMessageOptions) => {
    if (resetTimerId != null) {
      clearTimeout(resetTimerId);
      resetTimerId = null;
    }
    if (addTimerId != null) {
      clearTimeout(addTimerId);
      addTimerId = null;
    }

    if (!isShowing) {
      toast.add({ ...message, group: "message" });
      isShowing = true;
    } else {
      toast.removeGroup("message");
      isShowing = false;

      addTimerId = setTimeout(() => {
        toast.add({ ...message, group: "message" });
        isShowing = true;
        addTimerId = null;
      }, 200);
    }

    const delay = (message.life ?? 3000) + (isShowing ? 200 : 0);
    resetTimerId = setTimeout(() => {
      isShowing = false;
      resetTimerId = null;
    }, delay)
  };
}

export { useSingularToast };