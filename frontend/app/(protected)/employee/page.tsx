import Link from "next/link";
import type { Employee } from "./type";
import { getEmployee } from "./actions";
import { getSuccessMessage } from "@/app/actions";
import { Snackbar } from "@/app/components/snackbar";

export default async function Employee() {
  const employees = await getEmployee() as Employee[]
  const successMessage = await getSuccessMessage()

  return (
    <main className="container">
      <Snackbar message={successMessage} />
      <header>
        <h2>Employee</h2>
        <Link href="/employee/add" role="button">
          Add Employee
        </Link>
      </header>

      <table className="container">
        <thead>
          <tr>
            <th scope="col">#</th>
            <th scope="col">firstName</th>
            <th scope="col">lastName</th>
            <th scope="col">emailId</th>
            <th scope="col">action</th>
          </tr>
        </thead>
        <tbody>
          {employees.map((employee, index) => (
            <tr key={employee.id}>
              <th scope="row">{index + 1}</th>
              <td>{employee.firstName}</td>
              <td>{employee.lastName}</td>
              <td>{employee.emailId}</td>
              <td style={{
                display: 'flex',
                gap: 8
              }}>
                <Link href={'/employee/edit/' + employee.id}>
                  <i className="fas fa-edit"></i>
                </Link>
                <Link href={'/employee/delete/' + employee.id}>
                  <i className="fas fa-trash"></i>
                </Link>
              </td>
            </tr>
          ))}
        </tbody>
      </table>

    </main>
  )
}