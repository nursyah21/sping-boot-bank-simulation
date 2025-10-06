import { signOut } from "@/app/actions/signOut";

export default function Logout() {
  return (
    <section>
      <form action={signOut}>
        <button type="submit">Logout</button>
      </form>
    </section>
  )
}