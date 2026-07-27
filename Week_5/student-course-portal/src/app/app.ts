import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';

import { Header } from './components/header/header';
import { LoadingSpinner } from './components/loading-spinner/loading-spinner';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    Header,
    RouterOutlet,
    LoadingSpinner
  ],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {

}