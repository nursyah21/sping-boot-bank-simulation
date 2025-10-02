import type { Metadata } from "next";
import "./globals.css";

export const metadata: Metadata = {
  title: "Demo App",
  description: "demo app",
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="en" data-theme="">
      <body>
        {children}
        <footer />
      </body>
    </html>
  );
}
