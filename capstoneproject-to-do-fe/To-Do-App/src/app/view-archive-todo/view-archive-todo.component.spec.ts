import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewArchiveTodoComponent } from './view-archive-todo.component';

describe('ViewArchiveTodoComponent', () => {
  let component: ViewArchiveTodoComponent;
  let fixture: ComponentFixture<ViewArchiveTodoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ViewArchiveTodoComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ViewArchiveTodoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
