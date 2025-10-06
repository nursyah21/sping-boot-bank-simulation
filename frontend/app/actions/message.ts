"use server"

import { cookies } from "next/headers"
import { ERROR_COOKIE, SUCCESS_COOKIE } from "../constants"
import { redirect } from "next/navigation"

export async function setErrorMessage(msg: string, pathRedirect: string) {
  cookies().set(ERROR_COOKIE, msg, {
    maxAge: 0,
    httpOnly: true,
    sameSite: 'lax'
  })
  redirect(pathRedirect)
}

export async function getErrorMessage() {
  return cookies().get(ERROR_COOKIE)?.value
}

export async function setSuccessMessage(msg: string, pathRedirect: string) {
  cookies().set(SUCCESS_COOKIE, msg, {
    maxAge: 0,
    httpOnly: true,
    sameSite: 'lax'
  })
  redirect(pathRedirect)
}

export async function getSuccessMessage() {
  return cookies().get(SUCCESS_COOKIE)?.value
}