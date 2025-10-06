"use server"

import { BACKEND_URL } from "@/app/constants"
import { setErrorMessage, setSuccessMessage } from "./message"

export async function signUp(formData: FormData) {
    const username = formData.get('username')
    const password = formData.get('password')
    const confirmPassword = formData.get('confirmPassword')

    if (password !== confirmPassword) {
        setErrorMessage('Password do not match', '/register')
        return
    }

    if (!username || !password) {
        setErrorMessage('Username and Password are required', '/register')
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
        setErrorMessage(message, '/register')
        return
    }

    setSuccessMessage(message, '/register')
}

