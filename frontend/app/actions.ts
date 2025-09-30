"use server"

import { cookies } from "next/headers";
import { AUTH_COOKIE, ERROR_COOKIE, SUCCESS_COOKIE } from "./constants";
import { revalidatePath } from "next/cache";
import { jwtDecode } from "jwt-decode";

export async function handleError(msg: string, path: string) {
    cookies().set(ERROR_COOKIE, msg, {
        maxAge: 0,
        path: '/',
        httpOnly: true,
        sameSite: 'lax'
    })

    revalidatePath(path)
}

export async function handleSuccess(msg: string, path: string) {
    cookies().set(SUCCESS_COOKIE, msg, {
        maxAge: 0,
        path: '/',
        httpOnly: true,
        sameSite: 'lax'
    })

    revalidatePath(path)
}

export async function getErrorMessage() {
    const message = cookies().get(ERROR_COOKIE)?.value
    return message;
}


export async function getSuccessMessage() {
    const message = cookies().get(SUCCESS_COOKIE)?.value
    return message;
}

export async function getProfile() {
    const auth = cookies().get(AUTH_COOKIE)?.value
    if (!auth) {
        return
    }

    try {
        const { sub } = jwtDecode(auth)
        return sub
    } catch (error) {
        console.error("jwt decoding error: ", error)
        return
    }
}