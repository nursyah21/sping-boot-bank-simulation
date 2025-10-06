"use server"

import { cookies } from "next/headers"
import { redirect } from "next/navigation"
import { AUTH_COOKIE, BACKEND_URL } from "../constants"
import { Profile } from "../type"

export async function getProfile() {
  const token = cookies().get(AUTH_COOKIE)?.value
  if (!token) {
    redirect('/login')
  }

  const res = await fetch(BACKEND_URL + '/auth/profile', {
    headers: {
      "Authorization": "Bearer " + token
    }
  })

  if (!res.ok) {
    return
  }

  const {data} = await res.json()

  return data as Profile
}