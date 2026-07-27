import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { CourseService } from '../../services/course';
import { CourseSummaryWidget } from '../../components/course-summary-widget/course-summary-widget';
import { Notification } from '../../components/notification/notification';

@Component({
  selector: 'app-home',
  standalone: true,
 imports: [
  FormsModule,
  CourseSummaryWidget,
  Notification
],
  templateUrl: './home.html',
  styleUrl: './home.css'
})
export class Home implements OnInit, OnDestroy {

  portalName = 'Student Course Portal';

  isPortalActive = true;

  message = '';

  searchTerm = '';

  availableCourses = 0;

  constructor(
    private courseService: CourseService
  ) {

    console.log('Constructor called');

  }

  ngOnInit(): void {

  this.courseService.getCourses().subscribe({

    next: (courses) => {

      this.availableCourses = courses.length;

    },

    error: (err) => {

      console.error(err);

    }

  });

  console.log('HomeComponent initialised');

}

  ngOnDestroy(): void {

    console.log('HomeComponent destroyed');

  }

  onEnrollClick(): void {

    this.message = 'Enrollment opened!';

  }

}