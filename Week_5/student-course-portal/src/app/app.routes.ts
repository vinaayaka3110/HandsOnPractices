import { Routes } from '@angular/router';

import { Home } from './pages/home/home';
import { StudentProfile } from './pages/student-profile/student-profile';
import { CourseDetail } from './pages/course-detail/course-detail';
import { CoursesLayout } from './pages/courses-layout/courses-layout';
import { CourseList } from './pages/course-list/course-list';
import { NotFound } from './pages/not-found/not-found';
import { authGuard } from './guards/auth-guard';
import { unsavedChangesGuard } from './guards/unsaved-changes-guard';
import { CourseManagement } from './pages/course-management/course-management';

export const routes: Routes = [

  {
    path: '',
    component: Home
  },

  {
    path: 'courses',
    component: CoursesLayout,
    children: [
      {
        path: '',
        component: CourseList
      },
      {
        path: ':id',
        component: CourseDetail
      }
    ]
  },

  {
  path: 'profile',
  canActivate: [authGuard],
  component: StudentProfile
},

  {
  path: 'enroll',
  canActivate: [authGuard],
  loadComponent: () =>
    import('./pages/enrollment-form/enrollment-form')
      .then(c => c.EnrollmentForm)
},

 {
  path: 'enroll-reactive',
  canActivate: [authGuard],
  canDeactivate: [unsavedChangesGuard],
  loadComponent: () =>
    import('./pages/reactive-enrollment-form/reactive-enrollment-form')
      .then(c => c.ReactiveEnrollmentForm)
},

{
  path: 'course-management',
  component: CourseManagement
},

  {
    path: '**',
    component: NotFound
  }

];