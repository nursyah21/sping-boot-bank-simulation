import { getAccount } from "@/app/actions/account";
import { getSuccessMessage } from "@/app/actions/message";
import { SearchToolbar } from "@/app/components/SearchToolbar";
import { Snackbar } from "@/app/components/Snackbar";
import type { Account } from "@/app/type";

export default async function Account({
  searchParams
}: { searchParams: { keyword?: string; page?: string } 
}) {

  const data = await getAccount(undefined, searchParams)
  const successMessage = await getSuccessMessage()
  const content = data?.content as Account[]
  const offset = data?.pageable.offset as number

  return (
    <>
      <Snackbar message={successMessage} />
      <header>
        <div className="row">
          <h2 style={{ margin: 0 }}>Account</h2>
        </div>
        <SearchToolbar data={data} />
      </header>


      <div style={{ overflow: "auto", marginTop: '1rem' }}>
        <table className="">
          <thead>
            <tr>
              <th scope="col">#</th>
              <th scope="col">Username</th>
              <th scope="col">Account Id</th>
              <th scope="col">Balance</th>
            </tr>
          </thead>
          <tbody>
            {content.map((account, index) => (
              <tr key={index}>
                <th scope="row">{index + offset + 1}</th>
                <td>{account.username}</td>
                <td>{account.accountId}</td>
                <td>{account.balance}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </>
  )
}