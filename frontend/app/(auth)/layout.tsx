import { redirect } from "next/navigation"
import { getProfile } from "@/app/actions"

export default async function AuthLayout({
    children
}: {
    children: React.ReactNode
}) {
    const profile = await getProfile()
    if(profile) {
        redirect('/')
    }

    return (
        <div>
            {children}
        </div>
    )
}