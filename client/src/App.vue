<script setup>
import { computed } from 'vue'
import { useRoute } from 'vue-router'
import BaseLayout from '@/layouts/BaseLayout.vue'

const route = useRoute()

const layout = computed(() => route.meta.layout || BaseLayout)
</script>

<template>
  <main class="mix-w-screen min-h-screen px-5 py-5 flex gap-10 bg-base-200">
    <component :is="layout">
      <RouterView />
    </component>
  </main>
</template>

<style scoped></style>
