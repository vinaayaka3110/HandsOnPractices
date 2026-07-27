import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

import { NotificationService } from '../../services/notification';

@Component({
  selector: 'app-notification',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './notification.html',
  styleUrl: './notification.css',

  // Component-level provider.
  // This creates a NEW NotificationService instance
  // only for this component and its child components.
  // It does not use the singleton instance provided at
  // the application root.
  providers: [NotificationService]
})
export class Notification {

  constructor(
    private notificationService: NotificationService
  ) {}

  get message(): string {
    return this.notificationService.getMessage();
  }

}