<script setup>
import api from '@/axios'
import router from '@/router'
import axios from 'axios'
import { ref } from 'vue'

const loginInfo = ref({
  username: null,
  password: null,
})

const errors = ref([])

const attemptLogin = async () => {
  await api
    .post('/auth/login', loginInfo.value)
    .then((res) => {
      localStorage.setItem('token', res.data.token)
      localStorage.setItem('username', res.data.username)
      router.push({ path: '/' })
    })
    .catch((err) => {
      errors.value.push(err.response.data)
    })
}
</script>
<template>
  <div class="w-full h-full flex flex-col justify-center bg-base-200">
    <div class="mx-auto card bg-base-100 w-1/3 text-center">
      <div class="card-body items-center text-center flex flex-col">
        <h2 class="card-title text-center">Login To</h2>
        <h1 class="text-3xl">Schkoul</h1>

        <div v-for="e in errors" :key="e" role="alert" class="alert alert-error">
          {{ e }}
        </div>

        <div class="flex flex-col justify-start w-full gap-2 mt-2">
          <label>Username</label>
          <input
            class="input w-full"
            type="text"
            placeholder="username"
            v-model="loginInfo.username"
          />
        </div>

        <div class="flex flex-col justify-start w-full gap-2 mt-2">
          <label>Password</label>
          <input
            class="input w-full"
            type="password"
            placeholder="Password"
            v-model="loginInfo.password"
          />
        </div>

        <button class="btn btn-primary mt-2" @click.prevent="attemptLogin()">Login</button>
      </div>
    </div>
  </div>
</template>
