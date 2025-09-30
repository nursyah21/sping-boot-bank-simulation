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
    <html lang="en" data-theme="light">
      <head>
        <meta name="viewport" content="width=device-width, initial-scale=1"></meta>
        <script src="/global.js" defer></script>
      </head>
      <body>
        {children}
        <footer />
      </body>
    </html>
  );
}
