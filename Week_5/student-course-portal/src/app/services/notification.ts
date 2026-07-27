import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class NotificationService {

  private message = 'Welcome to Student Course Portal!';

  getMessage(): string {
    return this.message;
  }

  setMessage(message: string): void {
    this.message = message;
  }

}