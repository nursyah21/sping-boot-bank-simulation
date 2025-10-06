"use server"

import { cookies, headers } from "next/headers";
import { AUTH_COOKIE, BACKEND_URL } from "../constants";
import { setErrorMessage, setSuccessMessage } from "./message";
import { redirect } from "next/navigation";

export async function getTransaction(
  id?: number,
  searchParams?: {
    keyword?: string,
    page?: string
  }
) {
  let url = BACKEND_URL + '/transaction'
  if (id) {
    url += '/' + id
  }

  const keyword = searchParams?.keyword ?? '';
  const page = Number(searchParams?.page);
  const sizePage = 25
  url += "?size=" + sizePage + "&keyword=" + keyword + "&page=" + (page - 1)

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
  return data
}

export async function mutationTransaction(formData: FormData) {
    const id = formData.get('id')
    const destinationId = formData.get('destinationId')
    const amount = formData.get('amount')
    const method = formData.get('method') as 'get' | 'post' | 'put' | 'delete'
    const referer = headers().get('referer')!

    let url = BACKEND_URL + '/transaction'
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
        options.body = JSON.stringify({ destinationId, amount })
    }

    const res = await fetch(url, options)
    const { message } = await res.json()

    if (!res.ok) {
        setErrorMessage(message, referer)
        return
    }

    setSuccessMessage(message, '/transaction')
    redirect('/transaction')
}