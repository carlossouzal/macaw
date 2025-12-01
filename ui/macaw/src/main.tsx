import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import { Provider } from './components/ui/provider.tsx'
import { RouterProvider } from 'react-router-dom'
import router from './routes.tsx'

createRoot(document.getElementById('root')! ).render(
  <StrictMode>
    <Provider themes={["light", "dark"]} defaultTheme="light">
      <RouterProvider router={router} />
    </Provider>
  </StrictMode>,
)
