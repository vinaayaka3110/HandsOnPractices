import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, NgForm } from '@angular/forms';

@Component({
  selector: 'app-enrollment-form',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule
  ],
  templateUrl: './enrollment-form.html',
  styleUrl: './enrollment-form.css'
})
export class EnrollmentForm {

  studentName = '';

  studentEmail = '';

  courseId: number | null = null;

  preferredSemester = '';

  agreeToTerms = false;

  submitted = false;

  onSubmit(form: NgForm): void {

    console.log(form.value);

    console.log(form.valid);

    if (form.valid) {
      this.submitted = true;
    }

  }

}