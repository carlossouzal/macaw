import { useLoaderData, useSearchParams } from "react-router-dom";
import { useCallback } from "react";
import { Box, Container, Grid, Text, GridItem } from "@chakra-ui/react";
import "./pages.css";
import type { ResponseList, Course } from "../types";
import PageListDetails from "../components/list_details/list_details";
import PageListPagination from "../components/list_pagination/list_pagination";

export default function CoursePage() {
    const data = useLoaderData() as ResponseList<Course> | Course[];
    const [params, setParams] = useSearchParams();

    const page = Number(params.get("page") ?? 0);
    const size = Number(params.get("size") ?? 20);
    const sortBy = params.get("sortBy") ?? "name";
    const sortDirection = params.get("sortDirection") ?? "DESC";
    const code = params.get("code") ?? "";
    const name = params.get("name") ?? "";
    const description = params.get("description") ?? "";
    const creditsMin = params.get("creditsMin") ? Number(params.get("creditsMin")!) : undefined;
    const creditsMax = params.get("creditsMax") ? Number(params.get("creditsMax")!) : undefined;
    const hoursPerWeekMin = params.get("hoursPerWeekMin") ? Number(params.get("hoursPerWeekMin")) : undefined;
    const hoursPerWeekMax = params.get("hoursPerWeekMax") ? Number(params.get("hoursPerWeekMax")) : undefined;
    const courseType = params.get("courseType") ?? "";
    const gradeLevelMin = params.get("gradeLevelMin") ? Number(params.get("gradeLevelMin")) : undefined;
    const gradeLevelMax = params.get("gradeLevelMax") ? Number(params.get("gradeLevelMax")) : undefined;
    const semesterOrder = params.get("semesterOrder") ?? "";
    const specializationId = params.get("specializationId") ? Number(params.get("specializationId")) : undefined;
    const prerequisiteId = params.get("prerequisiteId") ? Number(params.get("prerequisiteId")) : undefined;

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

    const courses = Array.isArray(data) ? data : (data.items ?? []);

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
                <Text textStyle="2xl" color="#000020" fontWeight={"bold"}>Courses</Text>
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
                    items={courses}
                    setSize={setSize}
                    onChange={(value) => {
                        const n = typeof value === "string" ? Number(value) : value;
                        if (!Number.isNaN(n)) setSize(n);
                    }}
                />
                <Box>
                {courses.map((course) => (
                    <Box
                        key={course.id}
                        borderWidth="1px"
                        borderRadius="lg"
                        p="4"
                        mb="4"
                    >   
                        <Text fontWeight={"bold"}>{course.code} - {course.name}</Text>
                        <Text>{course.description}</Text>
                        <Text>Credits: {course.credits}</Text>
                        <Text>Hours per week: {course.hours_per_week}</Text>
                        <Text>Type: {course.course_type}</Text>
                        <Text>Grade Level: {course.grade_level_min} - {course.grade_level_max}</Text>
                        <Text>Semester Order: {course.semester_order}</Text>
                        <Text>Specialization: {course.specialization?.name ?? "None"}</Text>
                        <Text>Prerequisite: {course.prerequisite ? `${course.prerequisite.code} - ${course.prerequisite.name}` : "None"}</Text>
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
                    items={courses}
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
