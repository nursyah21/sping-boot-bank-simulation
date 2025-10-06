
import { signIn } from "../../actions/signIn";
import { getErrorMessage } from "@/app/actions/message";

export default async function Login() {
  const errorMessage = await getErrorMessage()

  return (
    <main className="container">
      <article className="grid">
        <div>
          <hgroup>
            <h1>Sign In</h1>
            <h2>Access your account</h2>
          </hgroup>
          <form action={signIn}>
            <label htmlFor="username">
              Username
              <input
                id="username"
                name="username"
                placeholder="username"
                required
                autoComplete="username"
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
                autoComplete="current-password"
              />
            </label>
            <button type="submit">Log in</button>
          </form>
          <p>
            <a href="/register">Create new account</a>
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
