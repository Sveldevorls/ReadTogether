import AuthorSubmissionView from "@/pages/submissions/AuthorSubmissionView.vue";
import NewAuthor from "@/pages/submissions/NewAuthor.vue";
import { roles } from "@/util/enums";

const submissionsRoutes = [
  {
    path: "/submissions/authors/new",
    component: NewAuthor,
    meta: {
      requiresAuth: true,
      minimalRole: roles.user,
      redirectTo: "/submissions/authors",
    },
  },
  {
    path: "/submissions/authors/:id",
    component: AuthorSubmissionView,
  },
];

export default submissionsRoutes