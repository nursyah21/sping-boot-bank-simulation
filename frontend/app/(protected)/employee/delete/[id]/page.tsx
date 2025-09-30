import { getErrorMessage } from "@/app/actions";
import { getEmployee, mutationEmployee } from "../../actions";
import type { Employee, Params } from "../../type";

export default async function DeleteEmployee(
  { params }: Params
) {
  const errorMessage = await getErrorMessage()
  const data = await getEmployee(params.id)

  if (!data) {
    return (
      <main className="container">
        <div className="alert alert-danger">
          Employee ID Not Found
        </div>
      </main>
    )
  }

  const { id, firstName, lastName, emailId } = data as Employee

  return (
    <main className="container">
      <header>
        <h2>Delete Employee</h2>
      </header>
      <form action={mutationEmployee}>
        <input type="hidden" name="id" defaultValue={id} />
        <input type="hidden" name="method" value='delete' />
        <label htmlFor="firstName">
          First Names
          <input
            id="firstName"
            name="firstName"
            placeholder="First Name"
            defaultValue={firstName}
            required
            disabled
          />
        </label>
        <label htmlFor="lastName">
          Last Name
          <input
            id="lastName"
            name="lastName"
            placeholder="Last Name"
            defaultValue={lastName}
            required
            disabled
          />
        </label>
        <label htmlFor="emailId">
          Email ID
          <input
            id="emailId"
            name="emailId"
            placeholder="Email ID"
            defaultValue={emailId}
            required
            disabled
          />
        </label>
        <button type="submit">Delete Employee</button>

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