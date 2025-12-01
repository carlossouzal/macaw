import { Box, Flex, Text } from "@chakra-ui/react"
import PageSizeSelector from "../page_size_selector/page_size_selector";

interface PageListDetailsProps {
  value: string | number;
  page: number;
  size: number;
  data: any;
  items: any[];
  setSize: (s: number) => void;
  onChange: (value: string | number) => void;
}

const PageListDetails = (props: PageListDetailsProps) => {

    return (
        <Flex
            borderWidth="1px"
            borderRadius="md"
            p="3"
            mb="4"
            bg="bg.muted"
            justifyContent={"space-between"}
        >
            <Box>
                <Text
                    color="fg.subtle"
                >
                    Page {props.page + 1} of {Math.ceil(((props.data.total ?? 0)) / props.size)} |
                    {Array.isArray(props.data) ? null : typeof props.data.total === "number" ? ` | Total results: ${props.data.total}` : null}
                </Text>
            </Box>
            <Flex>
                <Box>
                    <Text
                    color="fg.subtle"
                    mr={4}
                    >
                    Page Size:
                    </Text>
                </Box>
                <Box>
                    <PageSizeSelector 
                        value={props.size} 
                        onChange={props.onChange}
                    />
                </Box>
            </Flex>
        </Flex>
    )
}

export default PageListDetails