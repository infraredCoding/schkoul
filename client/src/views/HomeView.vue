<script setup>
import api from '@/axios'
import axios from 'axios'
import { computed, onMounted, ref, defineProps } from 'vue'

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
const initMonthlyValue = ref([])

onMounted(async () => {
  await api
    .get('/dashboard')
    .then((res) => {
      console.log(res.data)
      monthlyDue.value = res.data
      initMonthlyValue.value = res.data
    })
    .catch((err) => console.log(err))
})

const calendarAttributes = computed(() =>
  monthlyDue.value.map((task) => ({
    dates: task.date,
    highlight: {
      color: task.type === 'assignment' ? 'indigo' : 'purple',
      ...(task.completed && task.type === 'assignment' && { class: 'opacity-75' }),
    },
    popover: {
      label: task.title,
    },
  })),
)

const fetchAgenda = (e) => {
  api
    .get(`agenda-of-month/${e[0].year}/${e[0].month}`)
    .then((res) => {
      monthlyDue.value = res.data
    })
    .catch((err) => console.log(err))
}

const weeklyDue = computed(() => {
  const currMonthVal = [...initMonthlyValue.value]
  return currMonthVal.filter((el) => {
    const d = new Date(el.date)
    return d.getDate() >= startOfWeek.getDate() && d.getDate() <= endOfWeek.getDate()
  })
})

const username = ref(localStorage.getItem('username') || 'N/A')
const props = defineProps(['isSidebarOpen'])
const emit = defineEmits(['toggleSidebar'])
</script>

<template>
  <div class="w-full">
    <div class="flex justify-between gap-4 mb-5">
      <div class="flex gap-2">
        <button
          class="bg-primary text-zinc-50 p-2 rounded-full px-3 shadow-md z-40"
          @click="emit('toggleSidebar')"
        >
          <span v-if="props.isSidebarOpen">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              class="w-5 h-4"
              viewBox="0 0 24 24"
              fill="currentColor"
            >
              <path
                d="M4.83582 12L11.0429 18.2071L12.4571 16.7929L7.66424 12L12.4571 7.20712L11.0429 5.79291L4.83582 12ZM10.4857 12L16.6928 18.2071L18.107 16.7929L13.3141 12L18.107 7.20712L16.6928 5.79291L10.4857 12Z"
              ></path>
            </svg>
          </span>
          <span v-else>
            <svg
              xmlns="http://www.w3.org/2000/svg"
              class="w-5 h-4"
              viewBox="0 0 24 24"
              fill="currentColor"
            >
              <path
                d="M19.1642 12L12.9571 5.79291L11.5429 7.20712L16.3358 12L11.5429 16.7929L12.9571 18.2071L19.1642 12ZM13.5143 12L7.30722 5.79291L5.89301 7.20712L10.6859 12L5.89301 16.7929L7.30722 18.2071L13.5143 12Z"
              ></path>
            </svg>
          </span>
        </button>
        <h1 class="font-brand text-4xl text-slate-900">Schkoul</h1>
      </div>
      <span class="">{{ username }}</span>
    </div>
    <div class="flex gap-5 items-start flex-col lg:flex-row">
      <div class="lg:w-3/5 w-full card bg-base-100 sm:text-xs">
        <div class="card-body">
          <h2 class="card-title">Agenda This Week</h2>
          <div class="overflow-x-auto">
            <table class="table">
              <!-- head -->
              <thead>
                <tr>
                  <th>Title</th>
                  <th>Course</th>
                  <th>Type</th>
                  <th>Date</th>
                </tr>
              </thead>
              <tbody>
                <tr
                  class="hover:bg-base-300"
                  v-for="task in weeklyDue"
                  :key="task.title"
                  :class="{
                    'line-through':
                      (task.type == 'assignment' && task.completed) ||
                      (task.type == 'quiz' && calculateDateDiff(task.date) < 0),
                  }"
                >
                  <th>{{ task.title }}</th>
                  <td>{{ task.course.code }}</td>
                  <td>
                    <div
                      class="badge my-auto"
                      :class="{
                        'badge-primary': task.type == 'assignment',
                        'bg-indigo-700 text-zinc-50': task.type == 'quiz',
                      }"
                    >
                      {{ task.type }}
                    </div>
                  </td>
                  <td>
                    <div class="flex flex-col gap-1 my-auto">
                      {{ new Date(task.date).toDateString() }}
                      <div
                        class="badge badge-warning"
                        v-if="
                          (task.type == 'assignment' &&
                            calculateDateDiff(task.date) > 0 &&
                            calculateDateDiff(task.date) < 3 &&
                            !task.completed) ||
                          (task.type == 'quiz' &&
                            calculateDateDiff(task.date) > 0 &&
                            calculateDateDiff(task.date) < 3)
                        "
                      >
                        {{ calculateDateDiff(task.date) }}
                        days remaining
                      </div>
                      <div
                        class="badge badge-success"
                        v-if="
                          (task.type == 'assignment' &&
                            calculateDateDiff(task.date) == 0 &&
                            !task.completed) ||
                          (task.type == 'quiz' && calculateDateDiff(task.date) == 0)
                        "
                      >
                        Due Today
                      </div>
                      <div
                        class="badge badge-error"
                        v-if="
                          task.type == 'assignment' &&
                          calculateDateDiff(task.date) < 0 &&
                          !task.completed
                        "
                      >
                        Overdue
                      </div>
                    </div>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
          <!-- <div class="flex flex-col gap-2">
            <div class="flex justify-between gap-10" v-for="task in weeklyDue" :key="task.title">
              <h3 class="text-lg my-auto">
                {{ task.title }}
              </h3>
              <h3 class="my-auto">{{ task.course.code }}</h3>
              <div
                class="badge my-auto"
                :class="{
                  'badge-primary': task.type == 'assignment',
                  'bg-indigo-700 text-zinc-50': task.type == 'quiz',
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
          </div> -->
        </div>
      </div>

      <div class="lg:w-2/5 w-full card bg-base-100">
        <VCalendar expanded :attributes="calendarAttributes" @did-move="fetchAgenda" />
      </div>
    </div>
  </div>
</template>
