import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CourseSummaryWidget } from './course-summary-widget';

describe('CourseSummaryWidget', () => {
  let component: CourseSummaryWidget;
  let fixture: ComponentFixture<CourseSummaryWidget>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CourseSummaryWidget],
    }).compileComponents();

    fixture = TestBed.createComponent(CourseSummaryWidget);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
