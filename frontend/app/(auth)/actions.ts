"use server"

import { handleError, handleSuccess } from "@/app/actions"
import { AUTH_COOKIE, BACKEND_URL } from "@/app/constants"
import { jwtDecode } from "jwt-decode"
import { cookies } from "next/headers"
import { redirect } from "next/navigation"

export async function signUp(formData: FormData) {
    const username = formData.get('username')
    const password = formData.get('password')
    const confirmPassword = formData.get('confirmPassword')

    if (password !== confirmPassword) {
        handleError('Password do not match', '/register')
        return
    }

    if (!username || !password) {
        handleError('Username and Password are required', '/register')
        return
    }

    const res = await fetch(BACKEND_URL + '/auth/register', {
        method: 'post',
        headers: {
            'content-type': 'application/json'
        },
        body: JSON.stringify({ username, password })
    })

    const { message } = await res.json()
    if (!res.ok) {
        handleError(message, '/register')
        return
    }

    handleSuccess(message, '/register')
}

export async function signIn(formData: FormData) {
    const username = formData.get('username')
    const password = formData.get('password')

    if (!username || !password) {
        handleError('Username and Password are required', '/register')
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
        handleError(message, '/login')
        return
    }

    const { token } = data

    const { exp } = jwtDecode(token)

    cookies().set(AUTH_COOKIE, token, {
        expires: new Date(exp! * 1000),
        httpOnly: true,
        sameSite: 'lax'
    })

    redirect("/")
}