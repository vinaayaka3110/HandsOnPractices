import { HttpErrorResponse, HttpInterceptorFn } from '@angular/common/http';
import { catchError, throwError } from 'rxjs';

export const errorInterceptor: HttpInterceptorFn = (req, next) => {

  return next(req).pipe(

    catchError((error: HttpErrorResponse) => {

      console.error('HTTP Error:', error);

      if (error.status === 401) {
        alert('Unauthorized access.');
      } else if (error.status === 404) {
        alert('Requested resource not found.');
      } else if (error.status >= 500) {
        alert('Server error. Please try again later.');
      } else {
        alert('Something went wrong.');
      }

      return throwError(() => error);

    })

  );

};