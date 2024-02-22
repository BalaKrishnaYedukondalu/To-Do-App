import { BreakpointObserver } from '@angular/cdk/layout';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TodoService } from '../services/todo.service';
import { LoginService } from '../services/login.service';
import { ToDo } from '../model/todo';
import { NgToastService } from 'ng-angular-popup';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
})
export class HomeComponent implements OnInit {
 
  constructor(
    private observer: BreakpointObserver,
    private router: Router,
    private toDoService: TodoService,
    public loginService: LoginService,
    private toast: NgToastService
  ) {}
  allToDO: ToDo[] = [];

 
  totalLength: number = 0;
  notifications: any[] = [];
  ngOnInit(): void {
 
    this.getAllToDo();

    console.log(this.loginService.getToken);
  }
  notification() {
    this.router.navigateByUrl('/home/notification');
  }

  toDayTask() {
    this.router.navigateByUrl('/home/todobydate');
  }
  getAllToDo() {
    this.toDoService.getAllToDo().subscribe((data) => {
      this.allToDO = data;
      // this.totalLength = this.allToDO.length;
      this.totalLength = this.allToDO.filter(
        (data) => data.notificationRead == false
      ).length;
    });
  }
  getAllNotification() {
    this.router.navigateByUrl('/home/notification');
    this.totalLength = 0;
  }
  badgevisible = false;
  badgevisibility() {
    this.badgevisible = false;
  }

  // reload() {
  //   location.reload();
  // }

  addTodo() {
    this.router.navigateByUrl('/addToDo/');
  }

  deleteAllToDo() {
    const confirmed = window.confirm(
      'Are you sure you want to delete all tasks?'
    );
    if (confirmed) {
      this.toDoService.deleteAllToDo().subscribe({
        next: (data) => {
          this.toast.success({
            detail: 'AllToDo Deleted successfully',
            summary: 'AllToDo Deleted successfully',
            duration: 4000,
          });
        },
        error: (err) =>
          this.toast.error({
            detail: 'Due to the network issue All toDo is not deleted',
            summary: 'ERROR',
            duration: 4000,
          }),
      });
    }
  }

  logOut() {
    this.loginService.logout();
    this.router.navigateByUrl('/login');
  }
}
