import AuthorSubmissionView from "@/pages/submissions/AuthorSubmissionView.vue";
import AuthorSubmissionsIndex from "@/pages/submissions/AuthorSubmissionsIndex.vue";
import BookSubmissionView from "@/pages/submissions/BookSubmissionView.vue";
import BookSubmissionsIndex from "@/pages/submissions/BookSubmissionsIndex.vue";
import NewAuthor from "@/pages/submissions/NewAuthor.vue";
import NewBook from "@/pages/submissions/NewBook.vue";
import SubmissionsIndex from "@/pages/submissions/SubmissionsIndex.vue";
import { roles } from "@/util/enums";

const submissionsRoutes = [
  {
    path: "/submissions",
    component: SubmissionsIndex,
  },

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
  {
    path: "/submissions/authors",
    component: AuthorSubmissionsIndex,
  },

  // Books
  {
    path: "/submissions/books/new",
    component: NewBook,
    meta: {
      requiresAuth: true,
      minimalRole: roles.user,
      redirectTo: "/submissions/books",
    },
  },
  {
    path: "/submissions/books/:id",
    component: BookSubmissionView,
  },
  {
    path: "/submissions/books",
    component: BookSubmissionsIndex,
  },
];

export default submissionsRoutes;
