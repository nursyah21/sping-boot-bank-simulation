import { getProfile } from "@/app/actions";
import { signOut } from "./actions";
import { redirect } from "next/navigation";

export default async function Profile() {
  const profile = await getProfile()

  if (!profile) {
    redirect('/login')
  }

  return (
    <main className="container">
      <h1>{profile}</h1>

      <section>
        <form action={signOut}>
          <button type="submit">Logout</button>
        </form>
      </section>

    </main >
  )
}