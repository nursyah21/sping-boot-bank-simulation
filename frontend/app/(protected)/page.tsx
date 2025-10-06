import { getProfile } from "../actions/getProfile";
import { signOut } from "../actions/signOut";

export default async function Home() {
  const data = await getProfile()

  return (
    <>
      <h1>Simulasi Bank</h1>
      <p>
        Selamat datang di aplikasi simulasi bank.
      </p>
      <div className="container">
        <p>Username: {data?.username}</p>
        <p>Account ID: {data?.accountId}</p>
        <p>Balance: {data?.balance}</p>
        {data?.roles === 'ADMIN' &&
          <p>
            <strong>
              login as admin
            </strong>
          </p>
        }
      </div>

      <section>
        <form action={signOut}>
          <button type="submit">Logout</button>
        </form>
      </section>
    </>
  )
}