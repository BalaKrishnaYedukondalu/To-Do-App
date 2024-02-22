import { Component, OnInit } from '@angular/core';
import { TodoService } from '../services/todo.service';
import { ActivatedRoute, Router } from '@angular/router';
import { NgToastService } from 'ng-angular-popup';
import { FormBuilder, Validators } from '@angular/forms';
import { Category } from '../model/category';

@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.css'],
})
export class CategoryComponent implements OnInit {
  categories: Category[] = [];
  titleName: any;

  constructor(
    private fb: FormBuilder,
    private toDoService: TodoService,
    private router: Router,
    private ar: ActivatedRoute,
    private toast: NgToastService
  ) {}
  ngOnInit(): void {
    this.toDoService.getAllCategory();
  }

  taskForm: any = this.fb.group({
    title: ['', [Validators.required]],
  });

  onSubmit() {
    this.titleName = this.taskForm.value.title;
    this.toDoService.addCustomCategory(this.taskForm.value).subscribe(
      (data) => {
        console.log(data);
        alert('Category Add Successfully');
        this.toast.success({
          detail: 'Category added successfully',
          summary: 'DONE',
          duration: 8000,
        });
        location.reload();
        // this.router.navigateByUrl('home/header');
        // alert('Task added successfully');
      },
      (error) => {
        this.toast.error({
          detail: 'This ToDo Detail already present',
          summary: 'ERROR',
          duration: 4000,
        });
        //  alert('This task already exists');
        console.log(error);
      }
    );
  }
}
