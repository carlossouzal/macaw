import { Bleed, Flex, Box, Link, Text } from '@chakra-ui/react'
import './navbar.css'

function Navbar() {

  return (
    <Bleed 
      as="header" 
      className="navbar"
      bg="blue.950"
    
    >
      <Flex as="nav"
        maxW="container.lg"
        w="100%"
        h="10"
        justify="center"
        align="center"
    >
        <Flex align="center" className='flex space-x-4 justify-between' gap="10">
            <Box>
              <Link variant="plain" href="/classrooms">
                <Text textStyle="xl" className="item" >Classroom</Text>
              </Link>
            </Box>
            <Box>
              <Link variant="plain" href="/courses">
                <Text textStyle="xl" className="item" >Course</Text>
              </Link>
            </Box>
            <Box>
              <Link variant="plain" href="/semesters">
                <Text textStyle="xl" className="item" >Semester</Text>
              </Link>
            </Box>
            <Box>
              <Link variant="plain" href="/teachers">
                <Text textStyle="xl" className="item" >Teacher</Text>
              </Link>
            </Box>
            <Box>
              <Link variant="plain" href="/students">
                <Text textStyle="xl" className="item" >Student</Text>
              </Link>
            </Box>
        </Flex>
      </Flex>
    </Bleed>
  )
}

export default Navbar
