<script setup>
import api from '@/axios'
import { ref, onMounted } from 'vue'

const courses = ref([])
const newCourseInfo = ref({
  code: null,
  title: null,
  faculty: null,
})

const selectedCourse = ref({
  id: null,
  code: null,
  title: null,
  faculty: null,
})

const toggleEditModal = (_selectedCourse) => {
  selectedCourse.value = _selectedCourse
  document.querySelector('#edit_course')?.showModal()
}

const toggleDelModal = (_selectedCourse) => {
  selectedCourse.value = _selectedCourse
  document.querySelector('#del_course')?.showModal()
}

onMounted(async () => {
  await api
    .get('/course')
    .then((res) => {
      courses.value = res.data
    })
    .catch((err) => console.log(err))
})

const createCourse = async () => {
  await api
    .post('/course', newCourseInfo.value)
    .then((res) => {
      courses.value.push(res.data)
      newCourseInfo.value.code = null
      newCourseInfo.value.faculty = null
      newCourseInfo.value.title = null
    })
    .catch((err) => console.log(err))
}

const updateCourse = async () => {
  await api
    .patch(`/course/${selectedCourse.value.id}`, selectedCourse.value)
    .then((res) => {
      console.log(res)
      const index = courses.value.findIndex((course) => course.id === selectedCourse.value.id)
      if (index !== -1) {
        courses.value[index] = { ...courses.value[index], ...selectedCourse.value }
      }

      selectedCourse.value.id = null
      selectedCourse.value.code = null
      selectedCourse.value.faculty = null
      selectedCourse.value.title = null
      document.querySelector('#del_course')?.close()
    })
    .catch((err) => console.log(err))
}

const deleteCourse = async () => {
  await api
    .delete(`/course/${selectedCourse.value.id}`)
    .then((res) => {
      courses.value = courses.value.filter((c) => c.id !== selectedCourse.value.id)

      selectedCourse.value.id = null
      selectedCourse.value.code = null
      selectedCourse.value.faculty = null
      selectedCourse.value.title = null
    })
    .catch((err) => console.log(err))
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
        <h2 class="text-3xl font-bold">My Courses</h2>
        <button class="btn btn-primary" onclick="add_course.showModal()">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            class="w-5 h-5 fill-base-100"
            viewBox="0 0 24 24"
            fill="currentColor"
          >
            <path d="M11 11V5H13V11H19V13H13V19H11V13H5V11H11Z"></path>
          </svg>
          Add Course
        </button>
      </div>

      <dialog id="add_course" class="modal">
        <div class="modal-box">
          <form method="dialog">
            <button class="btn btn-sm btn-circle btn-ghost absolute right-2 top-2">✕</button>
          </form>
          <h3 class="text-lg font-bold mb-5">Add New Course</h3>
          <form @submit.prevent="createCourse()" class="flex flex-col gap-5">
            <input
              type="text"
              v-model="newCourseInfo.code"
              placeholder="Course Code"
              class="input w-full"
            />
            <input
              type="text"
              v-model="newCourseInfo.title"
              placeholder="Course Title"
              class="input w-full"
            />
            <input
              type="text"
              v-model="newCourseInfo.faculty"
              placeholder="Faculty"
              class="input w-full"
            />
            <button class="btn btn-primary" type="submit">Add Course</button>
          </form>
        </div>
      </dialog>

      <dialog id="edit_course" class="modal">
        <div class="modal-box">
          <form method="dialog">
            <button class="btn btn-sm btn-circle btn-ghost absolute right-2 top-2">✕</button>
          </form>
          <h3 class="text-lg font-bold mb-5">Edit Course</h3>
          <form @submit.prevent="updateCourse()" class="flex flex-col gap-5">
            <input
              type="text"
              v-model="selectedCourse.code"
              placeholder="Course Code"
              class="input w-full"
            />
            <input
              type="text"
              v-model="selectedCourse.title"
              placeholder="Course Title"
              class="input w-full"
            />
            <input
              type="text"
              v-model="selectedCourse.faculty"
              placeholder="Faculty"
              class="input w-full"
            />
            <button class="btn btn-primary" type="submit">Save Changes</button>
          </form>
        </div>
      </dialog>

      <dialog id="del_course" class="modal">
        <div class="modal-box">
          <form method="dialog">
            <button class="btn btn-sm btn-circle btn-ghost absolute right-2 top-2">✕</button>
          </form>
          <h3 class="text-lg font-bold mb-5">Delete Course</h3>
          <p>Are you sure you want to permanently delete course {{ selectedCourse.title }} ?</p>
          <button class="btn btn-error" @click.prevent="deleteCourse()">Yes Delete</button>
        </div>
      </dialog>

      <div class="overflow-x-auto">
        <table class="table">
          <!-- head -->
          <thead>
            <tr>
              <th>Course Code</th>
              <th>Course Title</th>
              <th>Faculty</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            <!-- row 1 -->
            <tr class="hover:bg-base-300" v-for="course in courses" :key="course.id">
              <td>{{ course.code }}</td>
              <td>{{ course.title }}</td>
              <td>{{ course.faculty }}</td>
              <td>
                <div class="flex gap-3">
                  <RouterLink
                    class="btn btn-square bg-amber-400 btn-sm"
                    :to="{ name: 'course-details', params: { id: course.id } }"
                  >
                    <svg
                      xmlns="http://www.w3.org/2000/svg"
                      class="w-5 h-5 fill-base-100"
                      viewBox="0 0 24 24 "
                      fill="currentColor"
                    >
                      <path
                        d="M12.0003 3C17.3924 3 21.8784 6.87976 22.8189 12C21.8784 17.1202 17.3924 21 12.0003 21C6.60812 21 2.12215 17.1202 1.18164 12C2.12215 6.87976 6.60812 3 12.0003 3ZM12.0003 19C16.2359 19 19.8603 16.052 20.7777 12C19.8603 7.94803 16.2359 5 12.0003 5C7.7646 5 4.14022 7.94803 3.22278 12C4.14022 16.052 7.7646 19 12.0003 19ZM12.0003 16.5C9.51498 16.5 7.50026 14.4853 7.50026 12C7.50026 9.51472 9.51498 7.5 12.0003 7.5C14.4855 7.5 16.5003 9.51472 16.5003 12C16.5003 14.4853 14.4855 16.5 12.0003 16.5ZM12.0003 14.5C13.381 14.5 14.5003 13.3807 14.5003 12C14.5003 10.6193 13.381 9.5 12.0003 9.5C10.6196 9.5 9.50026 10.6193 9.50026 12C9.50026 13.3807 10.6196 14.5 12.0003 14.5Z"
                      ></path>
                    </svg>
                  </RouterLink>
                  <button class="btn btn-square btn-accent btn-sm" @click="toggleEditModal(course)">
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
                  <button class="btn btn-square btn-sm btn-error" @click="toggleDelModal(course)">
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
