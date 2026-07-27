import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { CourseService } from '../../services/course';
import { Course } from '../../models/course.model';

@Component({
  selector: 'app-course-management',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule
  ],
  templateUrl: './course-management.html',
  styleUrl: './course-management.css'
})
export class CourseManagement implements OnInit {

  courses: Course[] = [];

  editing = false;

  editCourseId = 0;

  newCourse = {
    name: '',
    code: '',
    credits: 0,
    gradeStatus: 'pending' as 'passed' | 'failed' | 'pending'
  };

  constructor(
    private courseService: CourseService
  ) {}

  ngOnInit(): void {
    this.loadCourses();
  }

  loadCourses(): void {

    this.courseService.getCourses().subscribe({

      next: courses => this.courses = courses,

      error: err => console.error(err)

    });

  }

  createCourse(): void {

    this.courseService.createCourse(this.newCourse).subscribe({

      next: () => {

        alert('Course Added');

        this.resetForm();

        this.loadCourses();

      }

    });

  }

  edit(course: Course): void {

    this.editing = true;

    this.editCourseId = course.id;

    this.newCourse = {

      name: course.name,

      code: course.code,

      credits: course.credits,

      gradeStatus: course.gradeStatus

    };

  }

  updateCourse(): void {

    const updated: Course = {

      id: this.editCourseId,

      ...this.newCourse

    };

    this.courseService.updateCourse(updated).subscribe({

      next: () => {

        alert('Course Updated');

        this.resetForm();

        this.loadCourses();

      }

    });

  }

  deleteCourse(id: number): void {

    this.courseService.deleteCourse(id).subscribe({

      next: () => {

        alert('Course Deleted');

        this.loadCourses();

      }

    });

  }

  resetForm(): void {

    this.editing = false;

    this.editCourseId = 0;

    this.newCourse = {

      name: '',

      code: '',

      credits: 0,

      gradeStatus: 'pending'

    };

  }

}