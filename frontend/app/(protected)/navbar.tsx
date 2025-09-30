"use client"

import Link from "next/link"
import { usePathname } from "next/navigation"

export function Navbar(
  { username }: { username: string }
) {

  const pathname = usePathname()
  const isActive = (href: string) => pathname.startsWith(href)

  return (
    <nav className="container">
      <ul>
        <li><Link href="/profile"
          className={isActive('/profile') ? 'contrast' : ''}>
          Profile
        </Link></li>
        <li><Link href="/employee"
          className={isActive('/employee') ? 'contrast' : ''}>
          Employee
        </Link></li>
      </ul>
      <ul>
        <li><strong>{username}</strong></li>
      </ul>
    </nav>
  )
}