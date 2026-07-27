import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { switchMap } from 'rxjs';

import { CourseService } from '../../services/course';
import { Course } from '../../models/course.model';

@Component({
  selector: 'app-course-detail',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './course-detail.html',
  styleUrl: './course-detail.css',
})
export class CourseDetail implements OnInit {

  course?: Course;

  constructor(
    private route: ActivatedRoute,
    private courseService: CourseService
  ) {}

  ngOnInit(): void {

    /*
      switchMap cancels any previous HTTP request if the
      route parameter changes before the earlier request finishes.
      This prevents stale data from being displayed.
    */

    this.route.paramMap
      .pipe(
        switchMap(params => {
          const id = Number(params.get('id'));
          return this.courseService.getCourseById(id);
        })
      )
      .subscribe({

        next: (course) => {
          this.course = course;
        },

        error: (err) => {
          console.error(err);
        }

      });

  }

}