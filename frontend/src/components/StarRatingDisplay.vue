<script setup lang="ts">
import { computed } from "vue";

const props = defineProps<{ rating: number }>();
const widths = computed<number[]>(() => {
  let calcedRating = Math.round(props.rating * 100);
  const res = [];
  for (let i = 0; i < 5; i++) {
    if (calcedRating >= 100) res.push(100);
    else if (calcedRating > 0) res.push(calcedRating);
    else res.push(0);

    calcedRating -= 100;
  }
  return res;
});
console.log(widths);
</script>

<template>
  <div class="flex gap-1 items-center">
    <div
      v-for="i in 5"
      class="star-container"
    >
      <div
        class="star-fill"
        :style="{ width: `${widths[i - 1]}%` }"
      ></div>
    </div>
    <h3 class="text-2xl font-black ml-2">{{ rating.toFixed(2) }}</h3>
  </div>
</template>

<style scoped>
.star-container {
  width: 24px;
  height: 24px;
  background-color: #e0e0e0;
  position: relative;
  clip-path: path(
    "M12.001.63l2.903 8.35l8.839.181l-7.045 5.341l2.56 8.462L12 17.914l-7.256 5.05l2.56-8.462L.26 9.161l8.839-.18z"
  );
  padding: 0 10px;
}

.star-fill {
  position: absolute;
  top: 0;
  left: 0;
  height: 100%;
  background-color: #f1c40f;
}
</style>
