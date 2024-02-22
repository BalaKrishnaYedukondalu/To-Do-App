import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { NgToastService } from 'ng-angular-popup';
import { TodoService } from '../services/todo.service';
import { formatDate } from '@angular/common';

@Component({
  selector: 'app-update-todo',
  templateUrl: './update-todo.component.html',
  styleUrls: ['./update-todo.component.css'],
})
export class UpdateTodoComponent implements OnInit {
  constructor(
    private fb: FormBuilder,
    private toDoService: TodoService,
    private router: Router,
    private ar: ActivatedRoute,
    private toast: NgToastService
  ) {}

  taskForm: any = this.fb.group({
    title: ['', [Validators.required, Validators.minLength(4)]],
    description: ['', [Validators.required, Validators.minLength(10)]],
    priority: ['', [Validators.required]],
    dueDate: ['', [Validators.required]],
    isCompleted: [false],
    isArchive: [false],
    updatedTask: [''],
    createdDateTime: [''],
  });

  get title() {
    return this.taskForm.get('title');
  }
  get description() {
    return this.taskForm.get('description');
  }
  get priority() {
    return this.taskForm.get('priority');
  }
  get dueDate() {
    return this.taskForm.get('dueDate');
  }

  ngOnInit(): void {
    this.ar.paramMap.subscribe((data) => {
      console.log(data);
      let title = data.get('title') ?? '';
      console.log(title);
      if (title != '') {
        this.toDoService.getToDoByTitle(title).subscribe((data) => {
          this.taskForm.setValue({
            title: data.title,
            description: data.description,
            priority: data.priority,
            dueDate: data.dueDate,
            createdDateTime: data.createdDateTime,

            isCompleted: data.completed,
            isArchive: data.archive,
            updatedTask: data.updatedTask,
          });
        });
      }
    });
  }

  updateToDo() {
    const date = this.taskForm.value.dueDate.toString().slice(3, 15);

    const originalDate = new Date(date);
    const year = originalDate.getFullYear();
    const month = String(originalDate.getMonth() + 1).padStart(2, '0');
    const day = String(originalDate.getDate()).padStart(2, '0');

    const convertedDate = `${year}-${month}-${day}`;

    this.taskForm.value.dueDate = convertedDate;
    this.toDoService.updateToDo(this.taskForm.value).subscribe({
      next: (data) => {
        this.toast.success({
          detail: 'ToDo update successfully',
          summary: 'UPDATE',
          duration: 4000,
        });
        this.router.navigateByUrl('home/header');
      },

      error: (err) => {
        this.toast.error({
          detail: 'Due to the network issue',
          summary: 'ERROR',
          duration: 4000,
        });
      },
    });
  }
  todayDates: string = formatDate(new Date(), 'yyyy-MM-dd', 'en-in');
}
