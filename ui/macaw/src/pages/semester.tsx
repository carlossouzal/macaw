import { useLoaderData, useSearchParams } from "react-router-dom";
import { useCallback, useState } from "react";
import { Box, Container, Grid, Text, GridItem, Button, Drawer, Flex, Portal, CloseButton, Spinner } from "@chakra-ui/react";
import "./pages.css";
import type { Semester, CourseSection, ResponseList } from "../types";
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

  const [sectionParams, setSectionParams] = useSearchParams();
  const [sections, setSections] = useState<CourseSection[]>([]);
  const [sectionData, setSectionData] = useState<ResponseList<CourseSection>>({ total: 0, page: 0, size: 0, items: [] });

  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const [sectionPage, setSectionPage] = useState(Number(sectionParams.get("page") ?? 1));
  const [sectionSize, setSectionSize] = useState(Number(sectionParams.get("size") ?? 20));
  const [sectionSortBy, setSectionSortBy] = useState(sectionParams.get("sortBy") ?? "code");
  const [sectionSortDirection, setSectionSortDirection] = useState(sectionParams.get("sortDirection") ?? "DESC");
  const [sectionSemesterId, setSectionSemesterId] = useState(sectionParams.get("semesterId") ?? "");

  const sectionGoToPage = (p: number) => setSectionPage(Math.max(0, p));
  const sectionSetSize = (s: number) => setSectionSize(Math.max(1, s));
  const sectionSetSort = (by: string, dir: "ASC" | "DESC") => {
    setSectionSortBy(by);
    setSectionSortDirection(dir);
  };

  const sectionQuery = (() => {
    const sp = new URLSearchParams();
    sp.set("page", String(sectionPage-1));
    sp.set("size", String(sectionSize));
    sp.set("sortBy", sectionSortBy);
    sp.set("sortDirection", sectionSortDirection);
    if(sectionSemesterId) sp.set("semesterId", sectionSemesterId);
    return sp;
  })();

  const openSchedule = async () => {
    setLoading(true);
    setError(null);
    try {
      const qs = sectionQuery;
      const res = await fetch(`/api/course-sections?${qs.toString()}`, {
        method: "GET",
        headers: { "Accept": "application/json" },
      });
      if (!res.ok) throw new Error(`Request failed: ${res.status}`);
      const json = await res.json();

      setSectionData(json);
      setSections(Array.isArray(json) ? json : (json.items ?? []));
    } catch (e: any) {
      setError(e?.message ?? "Failed to load course sections");
      setSections([]);
    } finally {
      setLoading(false);
    }
  };

  const semesters = Array.isArray(data) ? data : (data.items ?? []);
  const SLOT_BOUNDARIES: { start: string; end: string }[] = [
    /* 0 unused for 1-based slot indexing */
    { start: "", end: "" },
    { start: "09:00 AM", end: "10:00 AM" }, // 1
    { start: "10:00 AM", end: "11:00 AM" }, // 2
    { start: "11:00 AM", end: "12:00 PM" }, // 3
    { start: "01:00 PM", end: "02:00 PM" }, // 4
    { start: "02:00 PM", end: "03:00 PM" }, // 5
    { start: "03:00 PM", end: "04:00 PM" }, // 6
    { start: "04:00 PM", end: "05:00 PM" }, // 7
  ];

  function formatConciseRange(start: string, end: string): string {
    const [sTime, sMer] = start.split(" ");
    const [eTime, eMer] = end.split(" ");
    const trimLeadingZero = (t: string) => t.replace(/^0/, "");

    const sShort = trimLeadingZero(sTime.replace(":00", ""));
    const eShort = trimLeadingZero(eTime.replace(":00", ""));

    if (sMer === eMer) {
      // Same meridiem → “9-12AM”
      return `${sShort}-${eShort}${sMer}`;
    }
    // Cross meridiem → “11AM-1PM”
    return `${sShort}${sMer}-${eShort}${eMer}`;
  }

  function formatMergedSchedule(assignments?: { day_of_week?: string; time_slot?: number }[]) {
    if (!assignments?.length) return "";

    // Normalize day labels to “Monday”, “Tuesday”, …
    const capDay = (d?: string) =>
      (d ?? "")
        .toLowerCase()
        .replace(/^\w/, c => c.toUpperCase());

    // Group by day
    const byDay = new Map<string, number[]>();
    for (const a of assignments) {
      if (!a?.day_of_week || !a?.time_slot) continue;
      const day = capDay(a.day_of_week);
      const slots = byDay.get(day) ?? [];
      slots.push(a.time_slot);
      byDay.set(day, slots);
    }

    // For each day, sort slots and merge consecutive ones (gap = 1, lunch break is naturally not consecutive)
    const parts: string[] = [];
    for (const [day, slots] of byDay.entries()) {
      const sorted = [...new Set(slots)].sort((a, b) => a - b);

      const ranges: Array<{ startSlot: number; endSlot: number }> = [];
      let start = sorted[0];
      let prev = sorted[0];
      for (let i = 1; i < sorted.length; i++) {
        const cur = sorted[i];
        if (cur === prev + 1) {
          // still consecutive
          prev = cur;
        } else {
          // close previous range
          ranges.push({ startSlot: start, endSlot: prev });
          // start new range
          start = cur;
          prev = cur;
        }
      }
      // close last range
      if (start !== undefined) ranges.push({ startSlot: start, endSlot: prev });

      // Format each merged range
      const dayParts = ranges.map(r => {
        const s = SLOT_BOUNDARIES[r.startSlot]?.start;
        const e = SLOT_BOUNDARIES[r.endSlot]?.end;
        if (!s || !e) return "";
        return `${day} ${formatConciseRange(s, e)}`;
      }).filter(Boolean);

      parts.push(...dayParts);
    }

    // Join all day-ranges
    return parts.join(", ");
  }

  

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
              key="semester-details"
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
                <Flex 
                  justifyContent={"space-between"}
                  key={semester.id} 
                  borderWidth="1px" 
                  borderRadius="lg" 
                  p="4" 
                  mb="4"
                >
                  <Box>
                    <Text fontWeight={"bold"}>{semester.name} ({semester.year} - {semester.order_in_year})</Text>
                    <Text>Start Date: {semester.start_date}</Text>
                    <Text>End Date: {semester.end_date}</Text>
                    <Text>Active: {semester.is_active ? "Yes" : "No"}</Text>
                  </Box>
                  <Box>
                    <Drawer.Root 
                      size="xl"
                      placement={{ mdDown: "bottom", md: "end" }}
                    >
                      <Drawer.Trigger asChild>
                        <Button variant="outline" size="sm" onClick={() => openSchedule(Number(semester.id))}>
                          Schedule
                        </Button>
                      </Drawer.Trigger>
                      <Portal>
                        <Drawer.Backdrop />
                        <Drawer.Positioner>
                          <Drawer.Content>
                            <Drawer.Header>
                              <Drawer.Title>{semester.name} {semester.year}</Drawer.Title>
                            </Drawer.Header>
                            <Drawer.Body>
                              {loading && (
                                <Flex align="center" gap={2}>
                                  <Spinner size="sm" /> <Text>Loading sections...</Text>
                                </Flex>
                              )}
                              {error && <Text color="red.500">{error}</Text>}
                              {!loading && !error && sections.length === 0 && (
                                <Text>No sections found for this semester.</Text>
                              )}
                              {!loading && !error && sections.length > 0 && (
                                <Box>
                                  <PageListDetails 
                                    key="course-section-details"
                                    value={sectionData.size}
                                    page={sectionPage-1}
                                    size={sectionSize}
                                    data={sectionData}
                                    items={sections}
                                    setSize={sectionSetSize}
                                    onChange={(value) => {
                                      const n = typeof value === "string" ? Number(value) : value;
                                      if (!Number.isNaN(n)) {
                                        sectionSetSize(n);
                                        openSchedule();
                                      }
                                    }}
                                  />
                                  {sections.map((s) => (
                                    <Box key={s.id} borderWidth="1px" borderRadius="md" p="3" mb="2">
                                      <Text fontWeight="bold"> {s.course?.name} ({s.course?.code}) / {s.code}</Text>
                                      <Text>Teacher: {s.teacher?.first_name} {s.teacher?.last_name}</Text>
                                      <Text>Room: {s.schedule_assignments?.[0]?.classroom?.name}</Text>
                                      <Text>
                                        Schedule: {formatMergedSchedule(s.schedule_assignments)}
                                      </Text>
                                      <Text>
                                        Students: {s.students?.length ?? 0}{" "}
                                        {((s.students?.length ?? 0) === (s.schedule_assignments?.[0]?.classroom?.capacity ?? 0))
                                          ? "(capacity full)"
                                          : `(${(s.schedule_assignments?.[0]?.classroom?.capacity ?? 0) - (s.students?.length ?? 0)} spot available)`}
                                      </Text>
                                    </Box>
                                  ))}
                                </Box>
                              )}
                              
                            </Drawer.Body>
                            <Drawer.CloseTrigger asChild>
                              <CloseButton size="sm" />
                            </Drawer.CloseTrigger>
                          </Drawer.Content>
                        </Drawer.Positioner>
                      </Portal>
                    </Drawer.Root>
                  </Box>
                </Flex>
              ))}
            </Box>
            <Box
              justifyContent={"center"} 
            >
              <PageListPagination
                key="semester-pagination"
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