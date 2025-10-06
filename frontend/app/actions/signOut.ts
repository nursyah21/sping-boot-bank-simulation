"use server"

import { cookies } from "next/headers"
import { redirect } from "next/navigation"
import { AUTH_COOKIE } from "../constants"

export async function signOut() {
    cookies().delete(AUTH_COOKIE)

    redirect("/login")
}