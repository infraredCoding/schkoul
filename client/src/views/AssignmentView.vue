<script setup>
import api from '@/axios'
import axios from 'axios'
import { ref, onMounted } from 'vue'

const assignments = ref([])
const courses = ref([])

const newAssignmentInfo = ref({
  title: null,
  details: null,
  date: null,
  courseId: null,
})

const selectedAssignment = ref({
  title: null,
  details: null,
  date: null,
})

const toggleEditModal = (_selectedAssignment) => {
  selectedAssignment.value = _selectedAssignment
  document.querySelector('#update_assignment')?.showModal()
}

const toggleDelModal = (_selectedAssignment) => {
  selectedAssignment.value = _selectedAssignment
  document.querySelector('#del_assignment')?.showModal()
}

onMounted(async () => {
  await api
    .get('/assignments')
    .then((res) => {
      res.data.forEach((el) => {
        el.date = el.date.split('T')[0]
      })
      assignments.value = res.data
    })
    .catch((err) => console.log(err))

  try {
    const [coursesRes, assignmentsRes] = await axios.all([
      api.get(`/course`),
      api.get('/assignments'),
    ])

    courses.value = coursesRes.data
    assignmentsRes.value = assignmentsRes.data
  } catch (error) {
    console.error(error)
  }
})

const createAssignment = async () => {
  await api
    .post('/assignments', newAssignmentInfo.value)
    .then((res) => {
      assignments.value.push(res.data)
      newAssignmentInfo.value.title = null
      newAssignmentInfo.value.details = null
      newAssignmentInfo.value.date = null
      newAssignmentInfo.value.courseId = null
      document.querySelector('#add_assignment')?.close()
    })
    .catch((err) => console.log(err))
}

const updateAssignment = async () => {
  await api
    .patch(`/assignments/${selectedAssignment.value.id}`, selectedAssignment.value)
    .then((res) => {
      console.log(res)
      const index = assignments.value.findIndex(
        (assignment) => assignment.id === selectedAssignment.value.id,
      )
      if (index !== -1) {
        assignments.value[index] = { ...assignments.value[index], ...selectedAssignment.value }
      }

      newAssignmentInfo.value.title = null
      newAssignmentInfo.value.details = null
      newAssignmentInfo.value.date = null
      document.querySelector('#update_assignment')?.close()
    })
    .catch((err) => console.log(err))
}

const deleteAssignment = async () => {
  await api
    .delete(`/assignments/${selectedAssignment.value.id}`)
    .then((res) => {
      assignments.value = assignments.value.filter((c) => c.id !== selectedAssignment.value.id)

      newAssignmentInfo.value.title = null
      newAssignmentInfo.value.details = null
      newAssignmentInfo.value.date = null

      document.querySelector('#del_assignment')?.close()
    })
    .catch((err) => console.log(err))
}

const toggleAssignment = async (toggleAssignmentId) => {
  await api.post(`/assignments/${toggleAssignmentId}`).catch((err) => console.log(err))
}

const username = ref(localStorage.getItem('username') || 'N/A')
</script>

<template>
  <div class="w-full flex flex-col py-5">
    <div class="flex justify-end gap-4">
      <span class="">{{ username }}</span>
    </div>
    <div
      class="h-full w-full bg-base-100 shadow-lg rounded-2xl mt-5 flex flex-col gap-10 px-10 py-5"
    >
      <div class="flex justify-between">
        <h2 class="text-3xl font-bold">Due Assignments</h2>
        <button class="btn btn-primary" onclick="add_assignment.showModal()">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            class="w-5 h-5 fill-base-100"
            viewBox="0 0 24 24"
            fill="currentColor"
          >
            <path d="M11 11V5H13V11H19V13H13V19H11V13H5V11H11Z"></path>
          </svg>
          Add Assignment
        </button>
      </div>

      <dialog id="add_assignment" class="modal">
        <div class="modal-box">
          <form method="dialog">
            <button class="btn btn-sm btn-circle btn-ghost absolute right-2 top-2">✕</button>
          </form>
          <h3 class="text-lg font-bold mb-5">Add New Assignment</h3>
          <form @submit.prevent="createAssignment()" class="flex flex-col gap-5">
            <input
              type="text"
              v-model="newAssignmentInfo.title"
              placeholder="Title"
              class="input w-full"
            />
            <input
              type="text"
              v-model="newAssignmentInfo.details"
              placeholder="Details"
              class="input w-full"
            />
            <input
              type="date"
              v-model="newAssignmentInfo.date"
              placeholder="Date"
              class="input w-full"
            />
            <select class="select w-full" v-model="newAssignmentInfo.courseId">
              <option :value="null">Select course</option>
              <option v-for="c in courses" :key="c.id" :value="c.id">
                {{ c.code }}: {{ c.title }}
              </option>
            </select>
            <button class="btn btn-primary" type="submit">Add Assignment</button>
          </form>
        </div>
      </dialog>

      <dialog id="update_assignment" class="modal">
        <div class="modal-box">
          <form method="dialog">
            <button class="btn btn-sm btn-circle btn-ghost absolute right-2 top-2">✕</button>
          </form>
          <h3 class="text-lg font-bold mb-5">Edit Assignment</h3>
          <form @submit.prevent="updateAssignment()" class="flex flex-col gap-5">
            <input
              type="text"
              v-model="selectedAssignment.title"
              placeholder="Title"
              class="input w-full"
            />
            <input
              type="text"
              v-model="selectedAssignment.details"
              placeholder="Details"
              class="input w-full"
            />
            <input
              type="date"
              v-model="selectedAssignment.date"
              placeholder="Date"
              class="input w-full"
            />
            <button class="btn btn-primary" type="submit">Save Changes</button>
          </form>
        </div>
      </dialog>

      <dialog id="del_assignment" class="modal">
        <div class="modal-box">
          <form method="dialog">
            <button class="btn btn-sm btn-circle btn-ghost absolute right-2 top-2">✕</button>
          </form>
          <h3 class="text-lg font-bold mb-5">Delete Assignment</h3>
          <p>
            Are you sure you want to permanently delete assignment {{ selectedAssignment.title }} ?
          </p>
          <button class="btn btn-error" @click.prevent="deleteAssignment()">Yes Delete</button>
        </div>
      </dialog>

      <div class="overflow-x-auto">
        <table class="table">
          <!-- head -->
          <thead>
            <tr>
              <th></th>
              <th>Title</th>
              <th>Course</th>
              <th>Due Date</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            <!-- row 1 -->
            <tr
              class="hover:bg-base-300"
              v-for="assignment in assignments"
              :key="assignment.id"
              :class="{ 'line-through': assignment.done }"
            >
              <th>
                <input
                  type="checkbox"
                  :checked="assignment.done == true"
                  @change="
                    () => {
                      assignment.done = !assignment.done
                      toggleAssignment(assignment.id)
                    }
                  "
                  class="checkbox"
                />
              </th>
              <td>
                {{ assignment.title }}
              </td>
              <td>{{ assignment.course.code }}: {{ assignment.course.title }}</td>
              <td class="flex flex-col">
                {{ new Date(assignment.date).toDateString() }}
                <div
                  class="badge badge-error"
                  v-if="
                    Math.ceil((new Date(assignment.date) - new Date()) / (1000 * 60 * 60 * 24)) < 3
                  "
                >
                  {{ Math.ceil((new Date(assignment.date) - new Date()) / (1000 * 60 * 60 * 24)) }}
                  days remaining
                </div>
              </td>
              <td>
                <div class="flex gap-3">
                  <button
                    class="btn btn-square btn-accent btn-sm"
                    @click="toggleEditModal(assignment)"
                  >
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
                  <button
                    class="btn btn-square btn-sm btn-error"
                    @click="toggleDelModal(assignment)"
                  >
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
