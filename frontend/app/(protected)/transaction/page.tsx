import { getSuccessMessage } from "@/app/actions/message";
import { getTransaction } from "@/app/actions/transaction";
import { SearchToolbar } from "@/app/components/SearchToolbar";
import { Snackbar } from "@/app/components/Snackbar";
import { formatTime } from "@/app/lib/formatTime";
import type { Transaction } from "@/app/type";

export default async function Transaction({
  searchParams
}: { searchParams: { keyword?: string; page?: string } 
}) {

  const data = await getTransaction(undefined, searchParams)
  const successMessage = await getSuccessMessage()
  const content = data?.content as Transaction[]
  const offset = data?.pageable.offset as number

  return (
    <>
      <Snackbar message={successMessage} />
      <header>
        <div className="row">
          <h2 style={{ margin: 0 }}>Transaction</h2>
          <a href="/transaction/add" aria-label="add">
            <i className="fas fa-plus"></i>
          </a>
        </div>
        <SearchToolbar data={data} />
      </header>


      <div style={{ overflow: "auto", marginTop: '1rem' }}>
        <table className="">
          <thead>
            <tr>
              <th scope="col">#</th>
              <th scope="col">Amount</th>
              <th scope="col">Source</th>
              <th scope="col">Destination</th>
              <th scope="col">Created At</th>
            </tr>
          </thead>
          <tbody>
            {content.map((transaction, index) => (
              <tr key={index}>
                <th scope="row">{index + offset + 1}</th>
                <td>{transaction.amountTransfer}</td>
                <td>{transaction.sourceId}</td>
                <td>{transaction.destinationId}</td>
                <td>{formatTime(transaction.createdAt)}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </>
  )
}