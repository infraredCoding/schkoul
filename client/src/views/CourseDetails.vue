<script setup>
import { computed, ref } from 'vue'

const course = ref({
  code: 'CSE321',
  title: 'Operating System',
  faculty: 'ACH',
  assignmentsList: [
    {
      id: 1,
      title: 'some assignment',
      done: true,
      date: '2025-02-21',
    },
    {
      id: 2,
      title: 'some assignment',
      done: false,
      date: '2025-02-21',
    },
    {
      id: 3,
      title: 'some assignment',
      done: false,
      date: '2025-02-21',
    },
    {
      id: 4,
      title: 'some assignment',
      done: false,
      date: '2025-02-21',
    },
    {
      id: 5,
      title: 'some assignment',
      done: false,
      date: '2025-02-21',
    },
    {
      id: 6,
      title: 'some assignment',
      done: false,
      date: '2025-02-21',
    },
    {
      id: 7,
      title: 'some assignment',
      done: false,
      date: '2025-02-21',
    },
  ],
  assessmentList: [
    {
      id: 1,
      assessmentType: 'QUIZ',
      weight: 20,
      marksAchieved: 0,
    },
    {
      id: 1,
      assessmentType: 'ASSIGNMENT',
      weight: 20,
      marksAchieved: 0,
    },
    {
      id: 1,
      assessmentType: 'MID',
      weight: 20,
      marksAchieved: 0,
    },
    {
      id: 1,
      assessmentType: 'FINAL',
      weight: 20,
      marksAchieved: 0,
    },
    {
      id: 1,
      assessmentType: 'LAB',
      weight: 20,
      marksAchieved: 0,
    },
  ],
})

const totalAssessmentMarks = computed(() => {
  return course.value.assessmentList.reduce((acc, curr) => acc + curr.weight, 0)
})

const totalAchievedMarks = computed(() => {
  return course.value.assessmentList.reduce((acc, curr) => acc + curr.marksAchieved, 0)
})

const assessmentIsHundred = computed(() => {
  return totalAssessmentMarks.value == 100
})

const username = ref(localStorage.getItem('username') || 'N/A')
</script>

<template>
  <div class="w-full flex flex-col py-5">
    <div class="flex justify-end gap-4">
      <span class="">{{ username }}</span>
    </div>
    <div class="w-full mt-5 flex flex-col gap-7 px-10 py-5">
      <div class="flex justify-between">
        <div class="flex gap-2">
          <h2 class="text-3xl font-bold my-auto">
            {{ course.code }} : {{ course.title }} ({{ course.faculty }})
          </h2>
        </div>
      </div>

      <div class="flex w-full gap-3">
        <div class="grid grid-cols-4 gap-3 w-5/6">
          <div
            class="card bg-base-100"
            v-for="assessment in course.assessmentList"
            :key="assessment.id"
          >
            <div class="card-body justify-between">
              <div class="text-xl">{{ assessment.assessmentType }}</div>
              <div class="text-xl">{{ assessment.marksAchieved }}/{{ assessment.weight }}</div>
              <button class="btn btn-sm btn-success">Update Marks</button>
              <button class="btn btn-sm btn-accent">Edit Assessment</button>
            </div>
          </div>

          <div class="card bg-base-100">
            <div class="card-body justifycenter items-center">
              <button
                class="btn btn-primary btn-soft my-auto"
                :class="{ 'btn-disabled': assessmentIsHundred }"
              >
                Add Assessment
              </button>
            </div>
          </div>
        </div>

        <div class="w-1/6">
          <div class="card bg-base-100 w-full">
            <div class="card-body mx-auto">
              <h4 class="card-title">Your Progress</h4>
              <div
                class="radial-progress"
                :style="`--value: ${totalAchievedMarks}`"
                :aria-valuenow="totalAchievedMarks"
                role="progressbar"
              >
                {{ totalAchievedMarks }}/100
              </div>
              <div class="flex flex-col gap-1">
                <h4 class="text-slate-500">
                  Marks required for grade bump:
                  <span class="text-slate-900 text-lg font-bold">5</span>
                </h4>

                <h4 class="text-slate-500">
                  Marks required for
                  <span class="text-slate-900 text-lg font-bold"
                    >A: {{ 90 - totalAchievedMarks }}</span
                  >
                </h4>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="flex w-full gap-3 items-start">
        <div class="card bg-base-100 w-1/2">
          <div class="card-body">
            <div class="card-title flex justify-between">
              Assignments
              <button class="btn btn-primary">Add Assignment</button>
            </div>
            <div class="overflow-x-auto">
              <table class="table">
                <!-- head -->
                <thead>
                  <tr>
                    <th></th>
                    <th>Title</th>
                    <th>Due Date</th>
                    <th>Actions</th>
                  </tr>
                </thead>
                <tbody>
                  <!-- row 1 -->
                  <tr
                    class="hover:bg-base-300"
                    v-for="assignment in course.assignmentsList"
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
                    <td class="flex flex-col">
                      {{ new Date(assignment.date).toDateString() }}
                      <div
                        class="badge badge-error"
                        v-if="
                          Math.ceil(
                            (new Date(assignment.date) - new Date()) / (1000 * 60 * 60 * 24),
                          ) < 3
                        "
                      >
                        {{
                          Math.ceil(
                            (new Date(assignment.date) - new Date()) / (1000 * 60 * 60 * 24),
                          )
                        }}
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
      </div>

      <!-- <dialog id="add_assignment" class="modal">
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
            <button class="btn btn-primary" type="submit">Add Course</button>
          </form>
        </div>
      </dialog> -->

      <!-- <dialog id="update_assignment" class="modal">
        <div class="modal-box">
          <form method="dialog">
            <button class="btn btn-sm btn-circle btn-ghost absolute right-2 top-2">✕</button>
          </form>
          <h3 class="text-lg font-bold mb-5">Edit Course</h3>
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
      </dialog> -->

      <!-- <dialog id="del_assignment" class="modal">
        <div class="modal-box">
          <form method="dialog">
            <button class="btn btn-sm btn-circle btn-ghost absolute right-2 top-2">✕</button>
          </form>
          <h3 class="text-lg font-bold mb-5">Delete Course</h3>
          <p>
            Are you sure you want to permanently delete assignment {{ selectedAssignment.title }} ?
          </p>
          <button class="btn btn-error" @click.prevent="deleteAssignment()">Yes Delete</button>
        </div>
      </dialog> -->
    </div>
  </div>

  <!-- modals -->
</template>
