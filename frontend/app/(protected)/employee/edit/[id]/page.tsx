import { getErrorMessage } from "@/app/actions";
import { getEmployee, mutationEmployee } from "../../actions";
import type { Employee, Params } from "../../type";

export default async function EditEmployee(
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
        <h2>Edit Employee</h2>
      </header>
      <form action={mutationEmployee}>
        <input type="hidden" name="id" defaultValue={id} />
        <input type="hidden" name="method" value='put' />
        <label htmlFor="firstName">
          First Names
          <input
            id="firstName"
            name="firstName"
            placeholder="First Name"
            defaultValue={firstName}
            required
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
          />
        </label>
        <button type="submit">Edit Employee</button>

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