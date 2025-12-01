import { useLoaderData, useSearchParams } from "react-router-dom";
import { useCallback } from "react";
import { Box, Container, Grid, Text, GridItem, Flex } from "@chakra-ui/react";
import "./pages.css";
import type { ResponseList, Student } from "../types";
import PageListDetails from "../components/list_details/list_details";
import PageListPagination from "../components/list_pagination/list_pagination";


export default function StudentPage() {
  const data = useLoaderData() as ResponseList<Student> | Student[];
  const [params, setParams] = useSearchParams();

  const page = Number(params.get("page") ?? 0);
  const size = Number(params.get("size") ?? 20);
  const sortBy = params.get("sortBy") ?? "name";
  const sortDirection = params.get("sortDirection") ?? "DESC";
  const name = params.get("name") ?? "";
  const email = params.get("email") ?? "";
  const gradeLevelMin = Number(params.get("gradeLevelMin") ?? 0);
  const gradeLevelMax = Number(params.get("gradeLevelMax") ?? 0);
  const expectedGraduationYearMin = Number(params.get("expectedGraduationYearMin") ?? 0);
  const expectedGraduationYearMax = Number(params.get("expectedGraduationYearMax") ?? 0);
  const courseId = Number(params.get("courseId") ?? 0);
  const status = params.get("status") ?? "";

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

  const students = Array.isArray(data) ? data : (data.items ?? []);

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
          <Text textStyle="2xl" color="#000020" fontWeight={"bold"}>Students</Text>
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
              items={students}
              setSize={setSize}
              onChange={(value) => {
                const n = typeof value === "string" ? Number(value) : value;
                if (!Number.isNaN(n)) setSize(n);
              }}
            />
            <Box>
              {students.map((student) => (
                <Box
                  key={student.id}
                  borderWidth="1px"
                  borderRadius="lg"
                  p="4"
                  mb="4"
                >
                    <Text fontWeight={"bold"}>{student.first_name} {student.last_name}</Text>
                    <Text>Email: {student.email}</Text>
                    <Text>Grade Level: {student.grade_level}</Text>
                    <Text>Enrollment Year: {student.enrollment_year}</Text>
                    <Text>Expected Graduation Year: {student.expected_graduation_year}</Text>
                    <Text>Status: {student.status}</Text>
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
                items={students}
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