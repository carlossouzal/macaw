import { useLoaderData, useSearchParams } from "react-router-dom";
import { useCallback } from "react";
import { Box, Container, Grid, Text, GridItem } from "@chakra-ui/react";
import "./pages.css";
import type { Semester, ResponseList } from "../types";
import PageListDetails from "../components/list_details/list_details";
import PageListPagination from "../components/list_pagination/list_pagination";


export default function SemesterPage() {
  const data = useLoaderData() as ResponseList<Semester> | Semester[];
  const [params, setParams] = useSearchParams();

  const page = Number(params.get("page") ?? 0);
  const size = Number(params.get("size") ?? 20);
  const sortBy = params.get("sortBy") ?? "name";
  const sortDirection = params.get("sortDirection") ?? "DESC";
  const name = params.get("name") ?? "";

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

  const semesters = Array.isArray(data) ? data : (data.items ?? []);
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
          <Text textStyle="2xl" color="#000020" fontWeight={"bold"}>Semesters</Text>
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
              items={semesters}
              setSize={setSize}
              onChange={(value) => {
                const n = typeof value === "string" ? Number(value) : value;
                if (!Number.isNaN(n)) setSize(n);
              }}
            />
            <Box>
              {semesters.map((semester) => (
                <Box 
                  key={semester.id} 
                  borderWidth="1px" 
                  borderRadius="lg" 
                  p="4" 
                  mb="4"
                >
                  <Text fontWeight={"bold"}>{semester.name} ({semester.year} - {semester.order_in_year})</Text>
                  <Text>Start Date: {semester.start_date}</Text>
                  <Text>End Date: {semester.end_date}</Text>
                  <Text>Active: {semester.is_active ? "Yes" : "No"}</Text>
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
                items={semesters}
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