"use server"

import { AUTH_COOKIE, BACKEND_URL } from "@/app/constants"
import { cookies } from "next/headers"
import { redirect } from "next/navigation"
import { setErrorMessage } from "./message"

export async function signIn(formData: FormData) {
  const username = formData.get('username')
  const password = formData.get('password')

  if (!username || !password) {
    setErrorMessage('Username and Password are required', '/login')
    return
  }

  const res = await fetch(BACKEND_URL + '/auth/login', {
    method: 'post',
    headers: {
      'content-type': 'application/json'
    },
    body: JSON.stringify({ username, password })
  })


  const { message, data } = await res.json()

  if (!res.ok) {
    setErrorMessage(message, '/login')
    return
  }

  const { token, expiresIn } = data

  cookies().set(AUTH_COOKIE, token, {
    expires: new Date(expiresIn),
    httpOnly: true,
    sameSite: 'lax'
  })

  redirect("/")
}