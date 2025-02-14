<script setup>
import { useRoute } from 'vue-router'

const route = useRoute()
</script>
<template>
  <main class="mix-w-screen min-h-screen px-5 py-5 flex gap-10 bg-base-200">
    <div class="w-[350px] bg-base-100 shadow-lg min-h-full flex flex-col rounded-2xl gap-5 pt-5">
      <RouterLink
        to="./"
        class="text-md text-center rounded-2xl px-5 py-3 mx-5"
        :class="{ 'bg-accent text-white': route.path === '/' }"
        >Dashboard</RouterLink
      >

      <RouterLink
        :to="{ name: 'courses' }"
        class="text-md text-center rounded-2xl px-5 py-3 mx-5 cursor-pointer"
        :class="{ 'bg-accent text-white': route.path === '/courses' }"
        >Courses</RouterLink
      >
    </div>

    <RouterView />
  </main>
</template>

<style scoped></style>
