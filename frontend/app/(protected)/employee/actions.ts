"use server"

import { handleError, handleSuccess } from "@/app/actions"
import { AUTH_COOKIE, BACKEND_URL } from "@/app/constants"
import { cookies, headers } from "next/headers"
import { redirect } from "next/navigation"
import { Employee } from "./type"

export async function getEmployee(id?: number) {
    let url = BACKEND_URL + '/employee'
    if (id) {
        url += '/' + id
    }

    const res = await fetch(url, {
        method: 'get',
        headers: {
            'authorization': 'Bearer ' + cookies().get(AUTH_COOKIE)?.value,
        }
    })

    if (!res.ok) {
        return
    }

    const { data } = await res.json()

    return data as Employee | Employee[]
}

export async function mutationEmployee(formData: FormData) {
    const id = formData.get('id')
    const firstName = formData.get('firstName')
    const lastName = formData.get('lastName')
    const emailId = formData.get('emailId')
    const method = formData.get('method') as 'get' | 'post' | 'put' | 'delete'
    const referer = headers().get('referer')!

    let url = BACKEND_URL + '/employee'
    if (id) {
        url += '/' + id
    }
    const options: RequestInit = {
        method,
        headers: {
            'authorization': 'Bearer ' + cookies().get(AUTH_COOKIE)?.value,
            'content-type': 'application/json'
        },
    }
    if (method == 'post' || method == 'put') {
        options.body = JSON.stringify({ firstName, lastName, emailId })
    }

    const res = await fetch(url, options)
    const {message} = await res.json()
    
    if (!res.ok) {
        handleError(message, referer)
        return
    }

    handleSuccess(message, '/employee')
    redirect('/employee')
}