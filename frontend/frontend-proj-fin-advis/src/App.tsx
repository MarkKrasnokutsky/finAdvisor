import { useState } from 'react'
import './App.css'
import {Button}  from "@/components/ui/button"

function App() {
  const [count, setCount] = useState(0)

  return (
    <>
        <h1 className="text-amber-900">Test tailwindCSS in TS</h1>
        <Button onClick={() => setCount((count) => count + 1)}>
            count is {count}
        </Button>
    </>
  )
}

export default App
