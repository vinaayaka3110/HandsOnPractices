import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  AbstractControl,
  AsyncValidatorFn,
  FormArray,
  FormBuilder,
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  ValidationErrors,
  ValidatorFn,
  Validators
} from '@angular/forms';

@Component({
  selector: 'app-reactive-enrollment-form',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule
  ],
  templateUrl: './reactive-enrollment-form.html',
  styleUrl: './reactive-enrollment-form.css'
})
export class ReactiveEnrollmentForm implements OnInit {

  enrollForm!: FormGroup;

  constructor(private fb: FormBuilder) { }

  ngOnInit(): void {

    this.enrollForm = this.fb.group({

      studentName: [
        '',
        [
          Validators.required,
          Validators.minLength(3)
        ]
      ],

      studentEmail: this.fb.control(
        '',
        {
          validators: [
            Validators.required,
            Validators.email
          ],
          asyncValidators: [
            this.simulateEmailCheck()
          ],
          updateOn: 'blur'
        }
      ),

      courseId: [
        '',
        [
          Validators.required,
          this.noCourseCode()
        ]
      ],

      preferredSemester: [
        'Odd',
        Validators.required
      ],

      agreeToTerms: [
        false,
        Validators.requiredTrue
      ],

      additionalCourses: this.fb.array<FormControl<string | null>>([])

    });

  }

  // Custom synchronous validator
  noCourseCode(): ValidatorFn {

    return (control: AbstractControl): ValidationErrors | null => {

      const value = (control.value ?? '').toString().trim().toUpperCase();

      if (value.startsWith('XX')) {
        return {
          noCourseCode: true
        };
      }

      return null;

    };

  }

  // Custom asynchronous validator
  simulateEmailCheck(): AsyncValidatorFn {

    return (control: AbstractControl): Promise<ValidationErrors | null> => {

      return new Promise(resolve => {

        setTimeout(() => {

          const value = (control.value ?? '').toString().toLowerCase();

          if (value.includes('test@')) {

            resolve({
              emailTaken: true
            });

          } else {

            resolve(null);

          }

        }, 800);

      });

    };

  }

  // Typed getter
  get additionalCourses(): FormArray<FormControl<string |null>> {

    return this.enrollForm.get(
      'additionalCourses'
    ) as FormArray<FormControl<string | null>>;

    /*
      Using a getter keeps the template clean.
      Otherwise we would repeatedly cast
      enrollForm.get('additionalCourses')
      as FormArray in the HTML.
    */

  }

  addCourse(): void {

    this.additionalCourses.push(

      new FormControl<string | null>(
        '',
        Validators.required
      )

    );

  }

  removeCourse(index: number): void {

    this.additionalCourses.removeAt(index);

  }

  trackByIndex(index: number): number {

    return index;

  }

  onSubmit(): void {

    if (this.enrollForm.invalid) {

      this.enrollForm.markAllAsTouched();

      return;

    }

    console.log('Form Value');
    console.log(this.enrollForm.value);

    console.log('Raw Value');
    console.log(this.enrollForm.getRawValue());

    /*
      enrollForm.value
      ----------------
      Returns values of enabled controls only.

      enrollForm.getRawValue()
      ------------------------
      Returns values of all controls,
      including disabled controls.
    */

  }

}