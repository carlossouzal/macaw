import { Box } from '@chakra-ui/react'
import Navbar from './components/navbar/navbar'
import { Outlet } from 'react-router-dom';

function Layout() {

  return (
    <Box 
      minHeight={"100vh"} 
      height={"100%"}
      className='layout'
    >
      <Navbar />
      <Box 
        as="main" 
        p="4"
      >
      <Outlet />
      </Box>
    </Box>
  )
}

export default Layout;
