import Link from "next/link";
import { getErrorMessage, getSuccessMessage } from "@/app/actions";
import { redirectToLogin, signUp } from "../actions";

export default async function Register() {
  const errorMessage = await getErrorMessage()
  const successMessage = await getSuccessMessage()

  if (successMessage) {
    return (
      <main className="container">
        <article className="grid">
          <div>
            <hgroup>
              <h1>{successMessage}</h1>
              <form className="container" action={redirectToLogin}>
                <button type="submit">Go to login page</button>
              </form>
            </hgroup>
          </div>
        </article>
      </main>
    )
  }

  return (
    <main className="container">
      <article className="grid">
        <div>
          <hgroup>
            <h1>Sign Up</h1>
            <h2>Create new account</h2>
          </hgroup>
          <form action={signUp}>
            <label htmlFor="username">
              Username
              <input
                id="username"
                name="username"
                placeholder="username"
                autoComplete="username"
                required
              />
            </label>
            <label htmlFor="password">
              Password
              <input
                type="password"
                id="password"
                name="password"
                placeholder="Password"
                required
                autoComplete="new-password"
              />
            </label>
            <label htmlFor="confirmPassword">
              Confirm Password
              <input
                type="password"
                id="confirmPassword"
                name="confirmPassword"
                placeholder="Confirm Password"
                required
                autoComplete="new-password"
              />
            </label>
            <button type="submit">Register</button>

          </form>
          <p className="text-center">
            <Link href="/login">Already have account</Link>
          </p>
        </div>
      </article>
      {
        errorMessage &&
        <div className="alert alert-danger">
          {errorMessage}
        </div>
      }
    </main>
  );
}
