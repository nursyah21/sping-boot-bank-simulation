import { redirect } from "next/navigation"
import { getProfile } from "../actions"
import { Navbar } from "../components/Navbar"

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
            <hr />
            {children}
        </>
    )
}