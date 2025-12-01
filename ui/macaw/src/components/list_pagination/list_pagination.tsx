<Stack gap="8" align="center" width="100%"></Stack>

import {
  ButtonGroup,
  Center,
  IconButton,
  Pagination,
  Stack,
} from "@chakra-ui/react"
import { LuChevronLeft, LuChevronRight } from "react-icons/lu"

interface PageListPaginationProps {
    value: string | number;
    page: string | number;
    size: number;
    data: any;
    items: any[];
    onChange: (value: string ) => void;
}

const PageListPagination = (props: PageListPaginationProps) => {
    const totalItems = () => ((props.data.total ?? 0));
    const currentPage = () => (props.page);

    return (
        <Stack gap="8" align="center" width="100%">
            <Pagination.Root 
                count={totalItems()} 
                pageSize={props.size} 
                currentPage={currentPage()}
                onPageChange={(details) => props.onChange(String(details.page))}
            >
                <ButtonGroup variant="ghost" size={"xs"}>
                    <Pagination.PrevTrigger asChild>
                    <IconButton>
                        <LuChevronLeft />
                    </IconButton>
                    </Pagination.PrevTrigger>

                    <Pagination.Items
                        render={(page) => (
                            <IconButton variant={{ base: "ghost", _selected: "solid" }}>
                            {page.value}
                            </IconButton>
                        )}
                    />

                    <Pagination.NextTrigger asChild>
                    <IconButton>
                        <LuChevronRight />
                    </IconButton>
                    </Pagination.NextTrigger>
                </ButtonGroup>
            </Pagination.Root>
        </Stack>
    )
}

export default PageListPagination