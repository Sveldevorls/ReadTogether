<script lang="ts"></script>
<template>
  <section class="w-[min(100%,80em)] px-4">
    <div>
      <h1 class="text-3xl">Welcome to ReadTogether</h1>
      <p>
        Share your reading journey, discover your new favorite books, and
        explore what the world is reading.
      </p>
    </div>
    <div class="flex flex-col lg:grid lg:grid-cols-[2fr_1fr]">
      <div class="bg-green-200">Featured reviews</div>
      <div class="flex flex-wrap">
        <div class="flex grow w-80 bg-blue-200">Popular this week</div>
        <div class="flex grow w-80 bg-orange-200">Newly added</div>
      </div>
    </div>
  </section>
</template>
