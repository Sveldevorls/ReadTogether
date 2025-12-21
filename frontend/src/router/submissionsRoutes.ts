import AuthorSubmissionView from "@/pages/submissions/AuthorSubmissionView.vue";
import BookSubmissionView from "@/pages/submissions/BookSubmissionView.vue";
import NewAuthor from "@/pages/submissions/NewAuthor.vue";
import NewBook from "@/pages/submissions/NewBook.vue";
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
];

export default submissionsRoutes