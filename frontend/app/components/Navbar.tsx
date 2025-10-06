"use client"

import { usePathname } from "next/navigation"

export function Navbar(
  { username }: { username: string }
) {

  const pathname = usePathname()
  const isActive = (href: string) => pathname.startsWith(href)

  return (
    <nav className="container">
      <ul>
        <li>
          <a href="/"
            className={isActive('/') ? 'contrast' : ''}>
            Home
          </a>
        </li>
        <li>
          <a href="/profile"
            className={isActive('/profile') ? 'contrast' : ''}>
            profile
          </a>
        </li>
      </ul>
      <ul>
        <li><strong>{username}</strong></li>
      </ul>
    </nav>
  )
}