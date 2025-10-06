import { cookies } from "next/headers"
import { redirect } from "next/navigation"
import { AUTH_COOKIE } from "../constants"

export default async function AuthLayout({
    children
}: {
    children: React.ReactNode
}) {
    const token = cookies().get(AUTH_COOKIE)?.value
    if (token) {
        redirect('/')
    }

    return (
        <div>
            {children}
        </div>
    )
}