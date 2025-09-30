import { getErrorMessage } from "@/app/actions";
import { mutationEmployee } from "../actions";

export default async function Employee() {
  const errorMessage = await getErrorMessage()

  return (
    <main className="container">
      <header>
        <h2>Employee</h2>
      </header>

      <form action={mutationEmployee}>
        <input type="hidden" name="id" />
        <input type="hidden" name="method" value='post' />
        <label htmlFor="firstName">
          First Names
          <input
            id="firstName"
            name="firstName"
            placeholder="First Name"
            required
          />
        </label>
        <label htmlFor="lastName">
          Last Name
          <input
            id="lastName"
            name="lastName"
            placeholder="Last Name"
            required
          />
        </label>
        <label htmlFor="emailId">
          Email ID
          <input
            id="emailId"
            name="emailId"
            placeholder="Email ID"
            required
          />
        </label>
        <button type="submit">Add Employee</button>

      </form>
      {
        errorMessage &&
        <div className="alert alert-danger">
          {errorMessage}
        </div>
      }

    </main>
  )
}