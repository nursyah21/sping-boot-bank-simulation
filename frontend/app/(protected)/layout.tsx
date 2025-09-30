import { redirect } from "next/navigation"
import { Navbar } from "./navbar"
import { getProfile } from "../actions"

export default async function ProtectedLayout({
    children
}: {
    children: React.ReactNode
}) {
    const profile = await getProfile()

    if (!profile) {
        redirect('/login')
    }

    return (
        <>
            <Navbar username={profile} />
            <hr/>
            {children}
        </>
    )
}