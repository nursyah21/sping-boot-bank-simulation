import { cookies } from "next/headers";
import { AUTH_COOKIE, BACKEND_URL } from "../constants";

export async function getAccount(
  id?: number,
  searchParams?: {
    keyword?: string,
    page?: string
  }
) {
  let url = BACKEND_URL + '/admin/account'
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