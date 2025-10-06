import { getProfile } from "@/app/actions/getProfile"
import { redirect } from "next/navigation"

export default async function AdminLayout({
  children
}: {
  children: React.ReactNode
}) {
  const data = await getProfile()
  if (data?.roles !== 'ADMIN') {
    redirect('/')
  }

  return (
    <>
      {children}
    </>
  )
}