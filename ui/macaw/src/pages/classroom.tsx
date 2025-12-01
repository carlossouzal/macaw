import { useLoaderData, useSearchParams } from "react-router-dom";
import { useCallback } from "react";
import { Box, Container, Grid, Text, GridItem, Flex } from "@chakra-ui/react";
import "./pages.css";
import type { ResponseList, Classroom } from "../types";
import PageListDetails from "../components/list_details/list_details";
import PageListPagination from "../components/list_pagination/list_pagination";


export default function ClassroomPage() {
    const data = useLoaderData() as ResponseList<Classroom> | Classroom[];
    const [params, setParams] = useSearchParams();

    const page = Number(params.get("page") ?? 0);
    const size = Number(params.get("size") ?? 20);
    const sortBy = params.get("sortBy") ?? "name";
    const sortDirection = params.get("sortDirection") ?? "DESC";
    const name = params.get("name") ?? "";
    const capacityMin = Number(params.get("capacityMin") ?? 0);
    const capacityMax = Number(params.get("capacityMax") ?? 0);
    const equipment = params.get("equipment") ?? "";
    const floor = Number(params.get("floor") ?? 0);
    const roomTypeId = Number(params.get("roomTypeId") ?? 0);
    const available = params.get("available") === "true" ? true : params.get("available") === "false" ? false : undefined;

    const updateParam = useCallback((key: string, value?: string) => {
        const next = new URLSearchParams(params);
        if (value === undefined || value === "") next.delete(key);
        else next.set(key, value);
        setParams(next, { replace: false });
    }, [params, setParams]);

    const goToPage = (p: number) => updateParam("page", String(Math.max(0, p)));
    const setSize = (s: number) => updateParam("size", String(Math.max(1, s)));
    const setSort = (by: string, dir: "ASC" | "DESC") => {
        updateParam("sortBy", by);
        updateParam("sortDirection", dir);
    };

    const classrooms = Array.isArray(data) ? data : (data.items ?? []);

    return (
        <Container 
            maxW="container.lg" 
            justifyContent={"center"} 
            mx={"auto"}
            px={150}  
        >
            <Box 
                justifyContent={"left"} 
                className="header"  
            >
                <Text textStyle="2xl" color="#000020" fontWeight={"bold"}>Classrooms</Text>
            </Box>  
            <Grid
                templateColumns="repeat(8, 1fr)"
                gap={4}
            >
            <GridItem
                className="content"
                colSpan={6}
            >
                <PageListDetails 
                    value={size}
                    page={page}
                    size={size}
                    data={data}
                    items={classrooms}
                    setSize={setSize}
                    onChange={(value) => {
                        const n = typeof value === "string" ? Number(value) : value;
                        if (!Number.isNaN(n)) setSize(n);
                    }}
                />
                <Box>
                {classrooms.map((classroom) => (
                    <Box
                    key={classroom.id}
                    borderWidth="1px"
                    borderRadius="lg"
                    p="4"
                    mb="4"
                    >
                        <Text fontWeight={"bold"}>{classroom.name}</Text>
                        <Text>Capacity: {classroom.capacity}</Text>
                        <Text>Equipment: {classroom.equipment}</Text>
                        <Text>Floor: {classroom.floor}</Text>
                        <Text>Room Type: {classroom.room_type.name}</Text>
                    </Box>
                ))}
                </Box>
                <Box
                    justifyContent={"center"}
                >
                <PageListPagination
                    page={page}
                    size={size}
                    data={data}
                    onChange={(value) => {
                        const n = typeof value === "string" ? Number(value) : value;
                        if (!Number.isNaN(n)) goToPage(n - 1);
                    }}
                />
                </Box>
            </GridItem>
            <GridItem
                className="content"
                colSpan={2}
            >
                <Text fontWeight={"bold"}>Filters</Text>

            </GridItem>
            </Grid>
        </Container>
    )
}