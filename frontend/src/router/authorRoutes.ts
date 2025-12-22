import AuthorProfile from "@/pages/AuthorProfile.vue";

// identifier: {id}-{slug}, slug is decorative
const authorRoutes = [
  {
    path: "/authors/:identifier",
    component: AuthorProfile,
  },
];

export default authorRoutes;