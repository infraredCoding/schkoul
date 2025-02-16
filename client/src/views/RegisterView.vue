<script setup>
import api from '@/axios'
import router from '@/router'
import { computed, ref } from 'vue'

const userInfo = ref({
  name: null,
  username: null,
  email: null,
  password: null,
})

const pw2 = ref(null)

const checkPassword = computed(() => {
  if (userInfo.value.password != null || userInfo.value.password != '') {
    return userInfo.value.password === pw2.value
  } else {
    return true
  }
})

const errors = ref([])

const registerUser = async () => {
  if (checkPassword.value) {
    await api
      .post('/auth/register', userInfo.value)
      .then((res) => {
        router.push({ path: '/login' })
      })
      .catch((err) => {
        errors.value.push(err.response.data)
        console.log(err)
      })
  }
}
</script>
<template>
  <div class="w-full h-full flex flex-col justify-center items-center bg-base-200">
    <div class="mx-auto card bg-base-100 w-1/3 text-center">
      <div class="card-body items-center text-center flex flex-col">
        <h2 class="card-title text-center">Sign Up to</h2>
        <h1 class="text-3xl">Schkoul</h1>

        <div v-for="e in errors" :key="e" role="alert" class="alert alert-error">
          {{ e }}
        </div>

        <div class="flex flex-col justify-start w-full gap-2 mt-5">
          <label>Name</label>
          <input class="input w-full" type="text" placeholder="Name" v-model="userInfo.name" />
        </div>

        <div class="flex flex-col justify-start w-full gap-2 mt-2">
          <label>Email</label>
          <input class="input w-full" type="email" placeholder="Email" v-model="userInfo.email" />
        </div>

        <div class="flex flex-col justify-start w-full gap-2 mt-2">
          <label>Username</label>
          <input
            class="input w-full"
            type="text"
            placeholder="username"
            v-model="userInfo.username"
          />
        </div>

        <div class="flex flex-col justify-start w-full gap-2 mt-2">
          <label>Password</label>
          <input
            class="input w-full"
            type="password"
            placeholder="Password"
            v-model="userInfo.password"
          />
        </div>

        <div class="flex flex-col justify-start w-full gap-2 mt-2">
          <label>Confirm Password</label>
          <input
            class="input w-full"
            type="password"
            placeholder="Confirm Password"
            v-model="pw2"
          />
        </div>

        <div role="alert" class="alert alert-error alert-soft" v-if="!checkPassword">
          Passwords do not match
        </div>

        <button class="btn btn-primary mt-2" @click.prevent="registerUser()">Register</button>
      </div>
    </div>
  </div>
</template>
