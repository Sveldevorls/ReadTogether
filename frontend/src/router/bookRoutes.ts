import BookDetails from "@/pages/BookDetails.vue";

// identifier: {id}-{slug}, slug is decorative
const bookRoutes = [
  {
    path: "/books/:identifier",
    component: BookDetails,
  },
];

export default bookRoutes;