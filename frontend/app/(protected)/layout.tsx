import { getProfile } from "../actions/getProfile"

export default async function ProtectedLayout({
  children
}: {
  children: React.ReactNode
}) {
  const data = await getProfile()

  return (
    <>
      <nav className="container">
        <ul>
          <li><a href="/" className="contrast">Home</a></li>
          {
            data?.roles === "ADMIN" &&
            <li><a href="/account">Account</a></li>
          }
          <li><a href="/transaction">Transaction</a></li>
        </ul>
        <ul>
          <li><strong>{data?.username}</strong></li>
        </ul>
      </nav>
      <hr />
      <main className="container">
        {children}
      </main>
    </>
  )
}