"use client"

import { useEffect, useState } from "react"

export function Snackbar(
  { message }: { message: string | undefined }
) {
  const [isVisible, setIsVisible] = useState(false)

  useEffect(()=>{
    if(message){
      setIsVisible(true)
      const timer = setTimeout(()=>{
        setIsVisible(false)
      }, 3000)

      return () => clearTimeout(timer)
    }
  }, [message])

  if(!isVisible || !message) {
    return
  }

  return (
    <div id="snackbar">{message}</div>
  )
}