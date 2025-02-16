<script setup>
import api from '@/axios'
import { computed, onMounted, ref } from 'vue'

const today = new Date()
const dayOfWeek = today.getDay()
const startOfWeek = new Date(today)
startOfWeek.setDate(today.getDate() - ((dayOfWeek + 1) % 7))
const endOfWeek = new Date(startOfWeek)
endOfWeek.setDate(startOfWeek.getDate() + 6)

const calculateDateDiff = (date) => {
  return Math.ceil((new Date(date) - new Date()) / (1000 * 60 * 60 * 24))
}

const monthlyDue = ref([])

onMounted(async () => {
  await api
    .get('/dashboard')
    .then((res) => (monthlyDue.value = res.data))
    .catch((err) => console.log(err))
})

const calendarAttributes = computed(() =>
  monthlyDue.value.map((task) => ({
    dates: task.date,
    highlight: { color: 'purple' },
    popover: {
      label: task.title,
    },
  })),
)

const weeklyDue = computed(() => {
  return monthlyDue.value.filter((el) => {
    const d = new Date(el.date)
    return d.getDate() >= startOfWeek.getDate() && d.getDate() <= endOfWeek.getDate()
  })
})

const username = ref(localStorage.getItem('username') || 'N/A')
</script>

<template>
  <div class="w-full">
    <div class="flex justify-end gap-4 mb-5">
      <span class="">{{ username }}</span>
    </div>
    <div class="flex gap-5 items-start">
      <div class="w-3/5 card bg-base-100">
        <div class="card-body">
          <h2 class="card-title">Agenda This Week</h2>
          <div class="flex flex-col gap-5">
            <div class="flex justify-between" v-for="task in weeklyDue" :key="task.title">
              <h3 class="text-lg my-auto" :class="{ 'line-through': task.completed }">
                {{ task.title }}
              </h3>
              <h3 class="my-auto">{{ task.course.code }}</h3>
              <div
                class="badge my-auto"
                :class="{
                  'badge-primary': task.type == 'assignment',
                  'badge-info': task.type == 'quiz',
                }"
              >
                {{ task.type }}
              </div>
              <div class="flex flex-col gap-1 my-auto">
                {{ new Date(task.date).toDateString() }}
                <div
                  class="badge badge-warning"
                  v-if="
                    calculateDateDiff(task.date) > 0 &&
                    calculateDateDiff(task.date) < 3 &&
                    !task.completed
                  "
                >
                  {{ calculateDateDiff(task.date) }}
                  days remaining
                </div>
                <div
                  class="badge badge-success"
                  v-if="(calculateDateDiff(task.date) == 0) & !task.completed"
                >
                  Due Today
                </div>
                <div
                  class="badge badge-error"
                  v-if="(calculateDateDiff(task.date) < 0) & !task.completed"
                >
                  Overdue
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="w-2/5 card bg-base-100">
        <VCalendar expanded :attributes="calendarAttributes" />
      </div>
    </div>
  </div>
</template>
