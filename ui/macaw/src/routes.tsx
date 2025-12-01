import { createBrowserRouter } from "react-router-dom"
import SemesterPage from "./pages/semester.tsx"
import Layout from "./layout.tsx";
import TeacherPage from "./pages/teacher.tsx";
import StudentPage from "./pages/student.tsx";
import ClassroomPage from "./pages/classroom.tsx";
import CoursePage from "./pages/course.tsx";

const router = createBrowserRouter([
    {
        path: "/",
        element: (
            <Layout />
        ),
        children: [
            {
                index: true,
                element: <SemesterPage />,
                loader: async () => {
                    const res = await fetch(`/api/semesters`, {
                        headers: { "Accept": "application/json" },
                    });
                    if (!res.ok) {
                        throw new Response("Failed to load semesters", { status: res.status });
                    }
                    return res.json();
                },
            }, 
            {
                path: "/semesters",
                element: <SemesterPage />,
                loader: async ({ request }) => {
                    const url = new URL(request.url);
                    const qs = url.search;
                    const response = await fetch(`/api/semesters${qs}`, {
                        headers: { "Accept": "application/json" },
                    });
                    if (!response.ok) {
                        throw new Response("Failed to load semesters", { status: response.status });
                    }
                    return response.json();
                },
            },
            {
                path: "/teachers",
                element: <TeacherPage />,
                loader: async ({ request }) => {
                    const url = new URL(request.url);
                    const qs = url.search;
                    const response = await fetch(`/api/teachers${qs}`, {
                        headers: { "Accept": "application/json" },
                    });
                    if (!response.ok) {
                        throw new Response("Failed to load teachers", { status: response.status });
                    }
                    return response.json();
                },
            },
            {
                path: "/students",
                element: <StudentPage />,
                loader: async ({ request }) => {
                    const url = new URL(request.url);
                    const qs = url.search;
                    const response = await fetch(`/api/students${qs}`, {
                        headers: { "Accept": "application/json" },
                    });
                    if (!response.ok) {
                        throw new Response("Failed to load students", { status: response.status });
                    }
                    return response.json();
                },
            },
            {
                path: "/classrooms",
                element: <ClassroomPage />,
                loader: async ({ request }) => {
                    const url = new URL(request.url);
                    const qs = url.search;
                    const response = await fetch(`/api/classrooms${qs}`, {
                        headers: { "Accept": "application/json" },
                    });
                    if (!response.ok) {
                        throw new Response("Failed to load classrooms", { status: response.status });
                    }
                    return response.json();
                },
            },
            {
                path: "/courses",
                element: <CoursePage />,
                loader: async ({ request }) => {
                    const url = new URL(request.url);
                    const qs = url.search;
                    const response = await fetch(`/api/courses${qs}`, {
                        headers: { "Accept": "application/json" },
                    });
                    if (!response.ok) {
                        throw new Response("Failed to load courses", { status: response.status });
                    }
                    return response.json();
                },
            }
        ],
    },
]);

export default router;