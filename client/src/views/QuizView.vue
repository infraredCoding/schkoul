<script setup>
import api from '@/axios'
import axios from 'axios'
import { ref, onMounted } from 'vue'

const quizes = ref([])
const courses = ref([])

const newquizInfo = ref({
  title: null,
  syllabus: null,
  date: null,
  courseId: null,
  marksAchieved: null,
  totalMarks: null,
})

const selectedquiz = ref({
  id: null,
  title: null,
  syllabus: null,
  date: null,
  marksAchieved: null,
  totalMarks: null,
})

const toggleEditModal = (_selectedquiz) => {
  selectedquiz.value = _selectedquiz
  document.querySelector('#update_quiz')?.showModal()
}

const toggleDelModal = (_selectedquiz) => {
  selectedquiz.value = _selectedquiz
  document.querySelector('#del_quiz')?.showModal()
}

// utils
const getDateDiff = (d) => {
  return Math.ceil((new Date(d) - new Date()) / (1000 * 60 * 60 * 24))
}

onMounted(async () => {
  await api
    .get('/quiz')
    .then((res) => {
      res.data.forEach((el) => {
        el.date = el.date.split('T')[0]
      })
      quizes.value = res.data
    })
    .catch((err) => console.log(err))

  try {
    const [coursesRes, quizesRes] = await axios.all([api.get(`/course`), api.get('/quiz')])

    courses.value = coursesRes.data
    quizesRes.value = quizesRes.data
  } catch (error) {
    console.error(error)
  }
})

const createquiz = async () => {
  await api
    .post('/quiz', newquizInfo.value)
    .then((res) => {
      quizes.value.push(res.data)
      selectedquiz.value.title = null
      selectedquiz.value.details = null
      selectedquiz.value.date = null
      selectedquiz.value.courseId = null
      document.querySelector('#add_quiz')?.close()
    })
    .catch((err) => console.log(err))
}

const updatequiz = async () => {
  await api
    .patch(`/quiz/${selectedquiz.value.id}`, selectedquiz.value)
    .then((res) => {
      console.log(res)
      const index = quizes.value.findIndex((quiz) => quiz.id === selectedquiz.value.id)
      if (index !== -1) {
        quizes.value[index] = { ...quizes.value[index], ...selectedquiz.value }
      }
      selectedquiz.value = {
        id: null,
        title: null,
        syllabus: null,
        date: null,
        marksAchieved: null,
        totalMarks: null,
      }

      document.querySelector('#update_quiz')?.close()
    })
    .catch((err) => console.log(err))
}

const deletequiz = async () => {
  await api
    .delete(`/quiz/${selectedquiz.value.id}`)
    .then((res) => {
      quizes.value = quizes.value.filter((c) => c.id !== selectedquiz.value.id)

      selectedquiz.value = {
        id: null,
        title: null,
        syllabus: null,
        date: null,
        marksAchieved: null,
        totalMarks: null,
      }

      document.querySelector('#del_quiz')?.close()
    })
    .catch((err) => console.log(err))
}

const username = ref(localStorage.getItem('username') || 'N/A')
</script>

<template>
  <div class="w-full flex flex-col py-5">
    <div class="flex justify-between gap-4">
      <h1 class="font-brand text-4xl text-slate-900">Schkoul</h1>
      <span class="">{{ username }}</span>
    </div>
    <div
      class="h-full w-full bg-base-100 shadow-lg rounded-2xl mt-5 flex flex-col gap-10 px-10 py-5"
    >
      <div class="flex justify-between">
        <h2 class="text-3xl font-bold">Quizzes</h2>
        <button class="btn btn-primary" onclick="add_quiz.showModal()">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            class="w-5 h-5 fill-base-100"
            viewBox="0 0 24 24"
            fill="currentColor"
          >
            <path d="M11 11V5H13V11H19V13H13V19H11V13H5V11H11Z"></path>
          </svg>
          Add quiz
        </button>
      </div>

      <dialog id="add_quiz" class="modal">
        <div class="modal-box">
          <form method="dialog">
            <button class="btn btn-sm btn-circle btn-ghost absolute right-2 top-2">✕</button>
          </form>
          <h3 class="text-lg font-bold mb-5">Add New quiz</h3>
          <form @submit.prevent="createquiz()" class="flex flex-col gap-5">
            <input
              type="text"
              v-model="newquizInfo.title"
              placeholder="Title"
              class="input w-full"
            />
            <select class="select w-full" v-model="newquizInfo.courseId">
              <option :value="null">Select course</option>
              <option v-for="c in courses" :key="c.id" :value="c.id">
                {{ c.code }}: {{ c.title }}
              </option>
            </select>
            <input
              type="text"
              v-model="newquizInfo.syllabus"
              placeholder="Syllabus"
              class="input w-full"
            />
            <input
              type="text"
              v-model="newquizInfo.totalMarks"
              placeholder="Total Marks (Optional)"
              class="input w-full"
            />
            <input type="date" v-model="newquizInfo.date" placeholder="Date" class="input w-full" />

            <button class="btn btn-primary" type="submit">Add Quiz</button>
          </form>
        </div>
      </dialog>

      <dialog id="update_quiz" class="modal">
        <div class="modal-box">
          <form method="dialog">
            <button class="btn btn-sm btn-circle btn-ghost absolute right-2 top-2">✕</button>
          </form>
          <h3 class="text-lg font-bold mb-5">Edit Quiz</h3>
          <form @submit.prevent="updatequiz()" class="flex flex-col gap-5">
            <input
              type="text"
              v-model="selectedquiz.title"
              placeholder="Title"
              class="input w-full"
            />
            <input
              type="text"
              v-model="selectedquiz.syllabus"
              placeholder="Syllabus"
              class="input w-full"
            />
            <input
              type="text"
              v-model="selectedquiz.marksAchieved"
              placeholder="Marks Achieved (Optional)"
              class="input w-full"
            />

            <input
              type="text"
              v-model="selectedquiz.totalMarks"
              placeholder="Total Marks (Optional)"
              class="input w-full"
            />
            <input
              type="date"
              v-model="selectedquiz.date"
              placeholder="Date"
              class="input w-full"
            />

            <button class="btn btn-primary" type="submit">Save Changes</button>
          </form>
        </div>
      </dialog>

      <dialog id="del_quiz" class="modal">
        <div class="modal-box">
          <form method="dialog">
            <button class="btn btn-sm btn-circle btn-ghost absolute right-2 top-2">✕</button>
          </form>
          <h3 class="text-lg font-bold mb-5">Delete Course</h3>
          <p>Are you sure you want to permanently delete quiz {{ selectedquiz.title }} ?</p>
          <button class="btn btn-error" @click.prevent="deletequiz()">Yes Delete</button>
        </div>
      </dialog>

      <div class="overflow-x-auto">
        <table class="table">
          <!-- head -->
          <thead>
            <tr>
              <th>Title</th>
              <th>Course</th>
              <th>Marks</th>
              <th>Date</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            <!-- row 1 -->
            <tr class="hover:bg-base-300" v-for="quiz in quizes" :key="quiz.id">
              <td>
                {{ quiz.title }}
              </td>
              <td>{{ quiz.course.code }}: {{ quiz.course.title }}</td>
              <td>{{ quiz.marksAchieved }} / {{ quiz.totalMarks }}</td>
              <td class="flex flex-col">
                {{ new Date(quiz.date).toDateString() }}
                <div
                  class="badge badge-error"
                  v-if="getDateDiff(quiz.date) >= 0 && getDateDiff(quiz.date) < 3"
                >
                  {{ getDateDiff(quiz.date) }}
                  days remaining
                </div>
              </td>
              <td>
                <div class="flex gap-3">
                  <button class="btn btn-square btn-accent btn-sm" @click="toggleEditModal(quiz)">
                    <svg
                      xmlns="http://www.w3.org/2000/svg"
                      class="w-5 h-5 fill-base-100"
                      viewBox="0 0 24 24"
                      fill="currentColor"
                    >
                      <path
                        d="M15.7279 9.57627L14.3137 8.16206L5 17.4758V18.89H6.41421L15.7279 9.57627ZM17.1421 8.16206L18.5563 6.74785L17.1421 5.33363L15.7279 6.74785L17.1421 8.16206ZM7.24264 20.89H3V16.6473L16.435 3.21231C16.8256 2.82179 17.4587 2.82179 17.8492 3.21231L20.6777 6.04074C21.0682 6.43126 21.0682 7.06443 20.6777 7.45495L7.24264 20.89Z"
                      ></path>
                    </svg>
                  </button>
                  <button class="btn btn-square btn-sm btn-error" @click="toggleDelModal(quiz)">
                    <svg
                      xmlns="http://www.w3.org/2000/svg"
                      class="w-5 h-5 fill-base-100"
                      viewBox="0 0 24 24"
                      fill="currentColor"
                    >
                      <path
                        d="M17 6H22V8H20V21C20 21.5523 19.5523 22 19 22H5C4.44772 22 4 21.5523 4 21V8H2V6H7V3C7 2.44772 7.44772 2 8 2H16C16.5523 2 17 2.44772 17 3V6ZM18 8H6V20H18V8ZM9 11H11V17H9V11ZM13 11H15V17H13V11ZM9 4V6H15V4H9Z"
                      ></path>
                    </svg>
                  </button>
                </div>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>

  <!-- modals -->
</template>
