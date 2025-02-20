<script setup>
import api from '@/axios'
import { computed, onMounted, ref } from 'vue'
import { useRoute } from 'vue-router'

const course = ref({})
const route = useRoute()

const gradingPolicy = [
  {
    range: '97 - 100',
    start: 97,
    end: 100,
    grade: 'A+',
    letter: '(4.00)',
  },
  {
    range: '90 - 96',
    start: 90,
    end: 96,
    grade: 'A',
    letter: '(4.00)',
  },
  {
    range: '85 - 89',
    start: 85,
    end: 89,
    grade: 'A-',
    letter: '(3.7)',
  },
  {
    range: '80 - 84',
    start: 80,
    end: 84,
    grade: 'B+',
    letter: '(3.3)',
  },
  {
    range: '75 - 79',
    start: 75,
    end: 79,
    grade: 'B',
    letter: '(3.00)',
  },
  {
    range: '70 - 74',
    start: 70,
    end: 74,
    grade: 'B-',
    letter: '(2.7)',
  },
  {
    range: '65 - 69',
    start: 65,
    end: 69,
    grade: 'C+',
    letter: '(2.3)',
  },
  {
    range: '60 - 64',
    start: 60,
    end: 64,
    grade: 'C',
    letter: '(2.00)',
  },
  {
    range: '57 - 59',
    start: 57,
    end: 59,
    grade: 'C-',
    letter: '(1.7)',
  },
  {
    range: '55 - 56',
    start: 55,
    end: 56,
    grade: 'D+',
    letter: '(1.3)',
  },
  {
    range: '52 - 54',
    start: 52,
    end: 54,
    grade: 'D',
    letter: '(1.00)',
  },
  {
    range: '50 - 51',
    start: 50,
    end: 51,
    grade: 'D-',
    letter: '(0.7)',
  },
  {
    range: '< 50',
    start: 0,
    end: 49,
    grade: 'F',
    letter: '(0.00)',
  },
]
// templates
// assessments
const newMarksDistiburionSection = ref({ assessmentType: null, weight: null })
const selectedMarksDistribution = ref({
  id: null,
  assessmentType: null,
  weight: null,
  marksAchieved: null,
})

const toggleEditAssessment = (assessment) => {
  selectedMarksDistribution.value = assessment
  document.querySelector('#edit_md')?.showModal()
}

const toggleUpdateMarks = (assessment) => {
  selectedMarksDistribution.value = { ...assessment }
  document.querySelector('#edit_md_marks')?.showModal()
}

// assignments
const newAssignmentInfo = ref({
  title: null,
  details: null,
  date: null,
  courseId: route.params.id,
})

const selectedAssignment = ref({
  id: null,
  title: null,
  details: null,
  date: null,
})

const toggleAssignmentEditModal = (_selectedAssignment) => {
  selectedAssignment.value = _selectedAssignment
  document.querySelector('#update_assignment')?.showModal()
}

const toggleAssignmentDelModal = (_selectedAssignment) => {
  selectedAssignment.value = _selectedAssignment
  document.querySelector('#del_assignment')?.showModal()
}

// quizzes
const newquizInfo = ref({
  title: null,
  syllabus: null,
  date: null,
  courseId: route.params.id,
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

const toggleQuizEditModal = (_selectedquiz) => {
  selectedquiz.value = _selectedquiz
  document.querySelector('#update_quiz')?.showModal()
}

const toggleQuizDelModal = (_selectedquiz) => {
  selectedquiz.value = _selectedquiz
  document.querySelector('#del_quiz')?.showModal()
}

// api calls
onMounted(async () => {
  await api
    .get(`/course/${route.params.id}`)
    .then((res) => {
      console.log(res.data)
      course.value = res.data
    })
    .catch((err) => console.log(err))
})

// assessments
const createMarksDistibution = async () => {
  await api
    .post(`/course/${route.params.id}/marks-distribution`, newMarksDistiburionSection.value)
    .then((res) => {
      course.value.marksDistributionList.push(res.data)
    })
    .catch((err) => console.log(err))
}

const updateAssessment = async () => {
  await api
    .patch(
      `course/marks-distribution/${selectedMarksDistribution.value.id}`,
      selectedMarksDistribution.value,
    )
    .then((res) => {
      const index = course.value.marksDistributionList.findIndex(
        (md) => md.id === selectedMarksDistribution.value.id,
      )
      if (index !== -1) {
        course.value.marksDistributionList[index] = {
          ...course.value.marksDistributionList[index],
          ...selectedMarksDistribution.value,
        }
      }

      selectedMarksDistribution.value = {
        id: null,
        assessmentType: null,
        weight: null,
        marksAchieved: null,
      }

      document.querySelector('#edit_md')?.close()
    })
    .catch((err) => console.log(err))
}

const updateMarks = async () => {
  await api
    .post(`course/update-marks/${selectedMarksDistribution.value.id}`, {
      marksAchieved: selectedMarksDistribution.value.marksAchieved,
    })
    .then((res) => {
      const index = course.value.marksDistributionList.findIndex(
        (md) => md.id === selectedMarksDistribution.value.id,
      )
      if (index !== -1) {
        course.value.marksDistributionList[index] = {
          ...course.value.marksDistributionList[index],
          ...selectedMarksDistribution.value,
        }
      }

      selectedMarksDistribution.value = {
        id: null,
        assessmentType: null,
        weight: null,
        marksAchieved: null,
      }

      document.querySelector('#edit_md_marks')?.close()
    })
    .catch((err) => console.log(err))
}

// assignments
const createAssignment = async () => {
  await api
    .post('/assignments', newAssignmentInfo.value)
    .then((res) => {
      course.value.assignmentsList.push(res.data)
      newAssignmentInfo.value.title = null
      newAssignmentInfo.value.details = null
      newAssignmentInfo.value.date = null

      document.querySelector('#add_assignment')?.close()
    })
    .catch((err) => console.log(err))
}

const updateAssignment = async () => {
  await api
    .patch(`/assignments/${selectedAssignment.value.id}`, selectedAssignment.value)
    .then((res) => {
      console.log(res)
      const index = course.value.assignmentsList.findIndex(
        (assignment) => assignment.id === selectedAssignment.value.id,
      )
      if (index !== -1) {
        course.value.assignmentsList[index] = {
          ...course.value.assignmentsList[index],
          ...selectedAssignment.value,
        }
      }

      selectedAssignment.value = {
        id: null,
        title: null,
        details: null,
        date: null,
      }
      document.querySelector('#update_assignment')?.close()
    })
    .catch((err) => console.log(err))
}

const deleteAssignment = async () => {
  await api
    .delete(`/assignments/${selectedAssignment.value.id}`)
    .then((res) => {
      course.value.assignmentsList = course.value.assignmentsList.filter(
        (c) => c.id !== selectedAssignment.value.id,
      )

      selectedAssignment.value = {
        id: null,
        title: null,
        details: null,
        date: null,
      }

      document.querySelector('#del_assignment')?.close()
    })
    .catch((err) => console.log(err))
}

// quizzes
const createquiz = async () => {
  await api
    .post('/quiz', newquizInfo.value)
    .then((res) => {
      course.value.quizList.push(res.data)
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
      const index = course.value.quizList.findIndex((quiz) => quiz.id === selectedquiz.value.id)
      if (index !== -1) {
        course.value.quizList[index] = {
          ...course.value.quizList[index],
          ...selectedquiz.value,
        }
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
      course.value.quizList = course.value.quizList.filter((c) => c.id !== selectedquiz.value.id)

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

const totalAssessmentMarks = computed(() => {
  return (
    course.value?.marksDistributionList?.reduce(
      (acc, curr) => acc + parseFloat(curr.weight || 0),
      0,
    ) || 0
  )
})

const totalAchievedMarks = computed(() => {
  return (
    course.value?.marksDistributionList?.reduce(
      (acc, curr) => acc + parseFloat(curr.marksAchieved || 0),
      0,
    ) || 0
  )
})

const assessmentIsHundred = computed(() => {
  return totalAssessmentMarks.value == 100
})

const getGrade = computed(() => {
  const score = parseFloat(totalAchievedMarks.value)
  const gradeObj = gradingPolicy.find((policy) => score >= policy.start && score <= policy.end)
  if (gradeObj) {
    return [gradeObj.grade, gradeObj.end + 1 - score]
  }
  return 'Invalid'
})

// utils
const getDateDiff = (d) => {
  return Math.ceil((new Date(d) - new Date()) / (1000 * 60 * 60 * 24))
}

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

      <dialog id="add_md" class="modal">
        <div class="modal-box">
          <form method="dialog">
            <button class="btn btn-sm btn-circle btn-ghost absolute right-2 top-2">✕</button>
          </form>
          <h3 class="text-lg font-bold mb-5">Add Marks Distribution Item</h3>
          <form @submit.prevent="createMarksDistibution()" class="flex flex-col gap-5">
            <select v-model="newMarksDistiburionSection.assessmentType" class="select w-full">
              <option value="">select assessment</option>
              <option value="ASSIGNMENT">ASSIGNMENT</option>
              <option value="QUIZ">QUIZ</option>
              <option value="MID">MID</option>
              <option value="FINAL">FINAL</option>
              <option value="LAB">LAB</option>
              <option value="PRESENTATION">PRESENTATION</option>
              <option value="OTHERS">OTHERS</option>
            </select>
            <input
              type="text"
              v-model="newMarksDistiburionSection.weight"
              placeholder="Weight"
              class="input w-full"
            />

            <span
              class="text-rose-700"
              v-if="
                totalAssessmentMarks + (parseFloat(newMarksDistiburionSection.weight) || 0) > 100
              "
            >
              total marks cannot be more than 100
            </span>
            <button
              class="btn btn-primary"
              type="submit"
              :class="{
                'btn-disabled':
                  totalAssessmentMarks + (parseFloat(newMarksDistiburionSection.weight) || 0) > 100,
              }"
            >
              Add
            </button>
          </form>
        </div>
      </dialog>

      <dialog id="edit_md" class="modal">
        <div class="modal-box">
          <form method="dialog">
            <button class="btn btn-sm btn-circle btn-ghost absolute right-2 top-2">✕</button>
          </form>
          <h3 class="text-lg font-bold mb-5">Edit Assessment Item</h3>
          <form @submit.prevent="updateAssessment()" class="flex flex-col gap-5">
            <select v-model="selectedMarksDistribution.assessmentType" class="select w-full">
              <option
                value="ASSIGNMENT"
                :selected="selectedMarksDistribution.assessmentType === 'ASSIGNMENT'"
              >
                ASSIGNMENT
              </option>
              <option value="QUIZ" :selected="selectedMarksDistribution.assessmentType === 'QUIZ'">
                QUIZ
              </option>
              <option value="MID" :selected="selectedMarksDistribution.assessmentType === 'MID'">
                MID
              </option>
              <option
                value="FINAL"
                :selected="selectedMarksDistribution.assessmentType === 'FINAL'"
              >
                FINAL
              </option>
              <option value="LAB" :selected="selectedMarksDistribution.assessmentType === 'LAB'">
                LAB
              </option>
              <option
                value="PRESENTATION"
                :selected="selectedMarksDistribution.assessmentType === 'PRESENTATION'"
              >
                PRESENTATION
              </option>
              <option
                value="OTHERS"
                :selected="selectedMarksDistribution.assessmentType === 'OTHERS'"
              >
                OTHERS
              </option>
            </select>
            <input
              type="text"
              v-model="selectedMarksDistribution.weight"
              placeholder="Weight"
              class="input w-full"
            />

            <button class="btn btn-primary" type="submit">Save Changes</button>
          </form>
        </div>
      </dialog>

      <dialog id="edit_md_marks" class="modal">
        <div class="modal-box">
          <form method="dialog">
            <button class="btn btn-sm btn-circle btn-ghost absolute right-2 top-2">✕</button>
          </form>
          <h3 class="text-lg font-bold mb-5">Edit Marks</h3>
          <form @submit.prevent="updateMarks()" class="flex flex-col gap-5">
            <input
              type="text"
              v-model="selectedMarksDistribution.marksAchieved"
              placeholder="Marks"
              class="input w-full"
            />

            <button class="btn btn-primary" type="submit">Update marks</button>
          </form>
        </div>
      </dialog>

      <dialog id="grade_strategy" class="modal">
        <div class="modal-box">
          <form method="dialog">
            <button class="btn btn-sm btn-circle btn-ghost absolute right-2 top-2">✕</button>
          </form>
          <h3 class="text-lg font-bold mb-5">Grading Strategy</h3>
          <div class="overflow-x-auto">
            <table class="table">
              <!-- head -->
              <thead>
                <tr>
                  <th>Range</th>
                  <th>Grade</th>
                </tr>
              </thead>
              <tbody>
                <tr class="hover:bg-base-300" v-for="g in gradingPolicy" :key="g.grade">
                  <td>{{ g.range }}</td>
                  <td>{{ g.grade }} {{ g.letter }}</td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </dialog>

      <div class="flex w-full gap-3">
        <div class="grid grid-cols-4 gap-3 w-5/6">
          <div
            class="card bg-base-100"
            v-for="assessment in course.marksDistributionList"
            :key="assessment.id"
          >
            <div class="card-body justify-between">
              <div class="text-xl">{{ assessment.assessmentType }}</div>
              <div class="text-xl">{{ assessment.marksAchieved }}/{{ assessment.weight }}</div>
              <button class="btn btn-sm btn-success" @click="toggleUpdateMarks(assessment)">
                Update Marks
              </button>
              <button class="btn btn-sm btn-accent" @click="toggleEditAssessment(assessment)">
                Edit Assessment
              </button>
            </div>
          </div>

          <div class="card bg-base-100">
            <div class="card-body justifycenter items-center">
              <button
                class="btn btn-primary btn-soft my-auto"
                onclick="add_md.showModal()"
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
              <div class="card-title flex justify-between">
                <h4>Your Progress</h4>
                <button class="btn btn-square btn-ghost" onclick="grade_strategy.showModal()">
                  <svg
                    xmlns="http://www.w3.org/2000/svg"
                    class="w-6 h-6"
                    viewBox="0 0 24 24"
                    fill="currentColor"
                  >
                    <path
                      d="M12 22C6.47715 22 2 17.5228 2 12C2 6.47715 6.47715 2 12 2C17.5228 2 22 6.47715 22 12C22 17.5228 17.5228 22 12 22ZM12 20C16.4183 20 20 16.4183 20 12C20 7.58172 16.4183 4 12 4C7.58172 4 4 7.58172 4 12C4 16.4183 7.58172 20 12 20ZM11 15H13V17H11V15ZM13 13.3551V14H11V12.5C11 11.9477 11.4477 11.5 12 11.5C12.8284 11.5 13.5 10.8284 13.5 10C13.5 9.17157 12.8284 8.5 12 8.5C11.2723 8.5 10.6656 9.01823 10.5288 9.70577L8.56731 9.31346C8.88637 7.70919 10.302 6.5 12 6.5C13.933 6.5 15.5 8.067 15.5 10C15.5 11.5855 14.4457 12.9248 13 13.3551Z"
                    ></path>
                  </svg>
                </button>
              </div>
              <div class="flex justify-around">
                <div
                  class="radial-progress"
                  :style="`--value: ${totalAchievedMarks}`"
                  :aria-valuenow="totalAchievedMarks"
                  role="progressbar"
                >
                  {{ totalAchievedMarks }}/100
                </div>
                <div class="text-4xl font-bold text-rose-400">{{ getGrade[0] }}</div>
              </div>

              <div class="flex flex-col gap-1">
                <h4 class="text-slate-500">
                  Marks required for grade bump:
                  <span class="text-slate-900 text-lg font-bold">{{ getGrade[1] }}</span>
                </h4>

                <h4 class="text-slate-500" v-if="totalAchievedMarks < 90">
                  Marks required for
                  <span class="text-slate-900 text-lg font-bold"
                    >A: {{ 90 - totalAchievedMarks }}</span
                  >
                </h4>
                <h4 v-else class="text-slate-500">A is secured</h4>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="flex w-full gap-3 items-start">
        <!-- Assignments -->
        <div class="card bg-base-100 w-1/2">
          <div class="card-body">
            <div class="card-title flex justify-between">
              Assignments
              <button class="btn btn-primary" onclick="add_assignment.showModal()">
                Add Assignment
              </button>
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
                          @click="toggleAssignmentEditModal(assignment)"
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
                          @click="toggleAssignmentDelModal(assignment)"
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

        <!-- Quizzes -->
        <div class="card bg-base-100 w-1/2">
          <div class="card-body">
            <div class="card-title flex justify-between">
              Quizzes
              <button class="btn btn-primary" onclick="add_quiz.showModal()">Add Quiz</button>
            </div>
            <div class="overflow-x-auto">
              <table class="table">
                <!-- head -->
                <thead>
                  <tr>
                    <th>Title</th>
                    <th>Marks</th>
                    <th>Date</th>
                    <th>Actions</th>
                  </tr>
                </thead>
                <tbody>
                  <!-- row 1 -->
                  <tr class="hover:bg-base-300" v-for="quiz in course.quizList" :key="quiz.id">
                    <td>
                      {{ quiz.title }}
                    </td>
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
                        <button
                          class="btn btn-square btn-accent btn-sm"
                          @click="toggleQuizEditModal(quiz)"
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
                          @click="toggleQuizDelModal(quiz)"
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
          <h3 class="text-lg font-bold mb-5">Delete Quiz</h3>
          <p>Are you sure you want to permanently delete quiz {{ selectedquiz.title }} ?</p>
          <button class="btn btn-error" @click.prevent="deletequiz()">Yes Delete</button>
        </div>
      </dialog>
    </div>
  </div>

  <!-- modals -->
</template>
