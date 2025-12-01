import { useLoaderData, useSearchParams } from "react-router-dom";
import { useCallback } from "react";
import { Box, Container, Grid, Text, GridItem, Flex } from "@chakra-ui/react";
import "./pages.css";
import type { ResponseList, Teacher } from "../types";
import PageSizeSelector from "../components/page_size_selector/page_size_selector";
import PageListDetails from "../components/list_details/list_details";
import PageListPagination from "../components/list_pagination/list_pagination";


export default function TeacherPage() {
  const data = useLoaderData() as ResponseList<Teacher> | Teacher[];
  const [params, setParams] = useSearchParams();

  const page = Number(params.get("page") ?? 0);
  const size = Number(params.get("size") ?? 20);
  const sortBy = params.get("sortBy") ?? "name";
  const sortDirection = params.get("sortDirection") ?? "DESC";
  const name = params.get("name") ?? "";
  const email = params.get("email") ?? "";
  const maxDailyHoursBegin = Number(params.get("maxDailyHoursBegin") ?? 0);
  const maxDailyHoursEnd = Number(params.get("maxDailyHoursEnd") ?? 0);
  const specializationId = Number(params.get("specializationId") ?? 0);
  const createdAtBegin = params.get("createdAtBegin") ?? "";
  const createdAtEnd = params.get("createdAtEnd") ?? "";

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

  const teachers = Array.isArray(data) ? data : (data.items ?? []);

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
          <Text textStyle="2xl" color="#000020" fontWeight={"bold"}>Teachers</Text>
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
              items={teachers}
              setSize={setSize}
              onChange={(value) => {
                const n = typeof value === "string" ? Number(value) : value;
                if (!Number.isNaN(n)) setSize(n);
              }}
            />
            <Box>
              {teachers.map((teacher) => (
                <Box
                  key={teacher.id}
                  borderWidth="1px"
                  borderRadius="lg"
                  p="4"
                  mb="4"
                >
                  <Text fontWeight={"bold"}>{teacher.first_name} {teacher.last_name}</Text>
                  <Text>Email: {teacher.email}</Text>
                  <Text>Max Daily Hours: {teacher.max_daily_hours}</Text>
                  <Text>Specialization: {teacher.specialization ? teacher.specialization.name : "N/A"}</Text>
                </Box>
              ))}
            </Box>
            <Box
              justifyContent={"center"}
            >
              <PageListPagination
                value={size}
                page={page}
                size={size}
                data={data}
                items={teachers}
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