import { getSuccessMessage } from "@/app/actions";
import { getEmployee } from "./actions";
import type { Employee } from "@/app/type";
import { Snackbar } from "@/app/components/Snackbar";
import { SearchToolbar } from "@/app/components/SearchToolbar";

export default async function Employee({
  searchParams,
}: {
  searchParams: { keyword?: string; page?: string };
}) {
  const data = await getEmployee(undefined, searchParams)
  const successMessage = await getSuccessMessage()
  const content = data?.content as Employee[]
  const offset = data?.pageable.offset as number
  
  return (
    <main className="container">
      <Snackbar message={successMessage} />
      <header>
        <div className="row">
          <h2 style={{ margin: 0 }}>Employee</h2>
          <a href="/employee/add" aria-label="add">
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
              <th scope="col">firstName</th>
              <th scope="col">lastName</th>
              <th scope="col">emailId</th>
              <th scope="col">action</th>
            </tr>
          </thead>
          <tbody>
            {content.map((employee, index) => (
              <tr key={employee.id}>
                <th scope="row">{index + offset + 1}</th>
                <td>{employee.firstName}</td>
                <td>{employee.lastName}</td>
                <td>{employee.emailId}</td>
                <td style={{
                  display: 'flex',
                  gap: '1rem'
                }}>
                  <a href={'/employee/edit/' + employee.id} aria-label="edit">
                    <i className="fas fa-edit"></i>
                  </a>
                  <a href={'/employee/delete/' + employee.id} aria-label="delete">
                    <i className="fas fa-trash"></i>
                  </a>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>


    </main>
  )
}