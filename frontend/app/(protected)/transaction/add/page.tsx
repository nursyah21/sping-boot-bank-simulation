import { getErrorMessage } from "@/app/actions/message"
import { mutationTransaction } from "@/app/actions/transaction"

export default async function Employee() {
  const errorMessage = await getErrorMessage()

  return (
    <>
      <header>
        <h2>New Transaction</h2>
      </header>

      <form action={mutationTransaction}>
        <input type="hidden" name="id" />
        <input type="hidden" name="method" value='post' />
        <label htmlFor="destinationId">
          Destination Id
          <input
            id="destinationId"
            name="destinationId"
            placeholder="Destination Id"
            required
          />
        </label>
        <label htmlFor="amount">
          Amount
          <input
            id="amount"
            name="amount"
            type="number"
            required
          />
        </label>
        <button type="submit">Transfer</button>

      </form>
      {
        errorMessage &&
        <div className="alert alert-danger">
          {errorMessage}
        </div>
      }

    </>
  )
}