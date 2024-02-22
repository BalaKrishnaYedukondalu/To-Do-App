import { Component, OnInit } from '@angular/core';
import { TodoService } from '../services/todo.service';
import { NgToastService } from 'ng-angular-popup';
import { ToDo } from '../model/todo';

@Component({
  selector: 'app-viewnotification',
  templateUrl: './viewnotification.component.html',
  styleUrls: ['./viewnotification.component.css'],
})
export class ViewnotificationComponent implements OnInit {
  constructor(
    private todoService: TodoService,
    private toast: NgToastService,
    private toDoService: TodoService
  ) {}

  notifications: any;
  p: any;
  totalLength: number = 0;
  allToDO: ToDo[] = [];

  ngOnInit(): void {
    this.getFilteredNotifications();
  }

  getFilteredNotifications() {
    const today = new Date();
    const tomorrow = new Date();
    tomorrow.setDate(today.getDate() + 1);
    const twoDaysLater = new Date(today);
    twoDaysLater.setDate(today.getDate() + 2);

    this.todoService.getAllNotification().subscribe({
      next: (res) => {
        this.totalLength = res.jsonObject.notificationWithToDoList.filter(
          (data: any) => {
            const dueDate = new Date(data.dueDate);
            return (
              dueDate >= tomorrow &&
              dueDate <= twoDaysLater &&
              data.notificationRead == false
            );
          }
        );

        this.notifications = res.jsonObject.notificationWithToDoList.filter(
          (data: any) => {
            const dueDate = new Date(data.dueDate);
            return (
              dueDate >= tomorrow &&
              dueDate <= twoDaysLater &&
              data.notificationRead == false
            );
          }
        );

        console.log(this.notifications);
      },
      error: (err) => console.log(err + 'due to the network issue'),
    });
  }

  readNotification(notification: any) {
    notification.notificationRead = true;
    this.todoService.updateToDo(notification).subscribe({
      next: (data) => {
        this.toast.success({
          detail: 'ToDo update successfully',
          summary: 'UPDATE',
          duration: 4000,
        });
        this.getAllToDo();
        window.location.reload();
      },
      error: (err) => {
        this.toast.error({
          detail: 'Due to the network issue',
          summary: 'ERROR',
          duration: 4000,
        });
      },
    });
    return (notification.notificationRead = true);
  }

  getAllNotification() {
    this.todoService.getAllNotification().subscribe({
      next: (res) => {
        this.totalLength = res.jsonObject.notificationWithToDoList.filter(
          (data: any) => data.notificationRead == false
        ).length;
        this.notifications = res.jsonObject.notificationWithToDoList.filter(
          (data: any) => data.notificationRead == false
        );
        console.log(this.notifications);
      },
      error: (err) => console.log(err + 'due to the network issue'),
    });
  }

  getAllToDo() {
    this.toDoService.getAllToDo().subscribe((data) => {
      this.allToDO = data.filter(
        (a: { archive: boolean; completed: boolean }) =>
          a.completed == false && a.archive == false
      );
      this.totalLength = this.allToDO.filter(
        (data) => data.notificationRead == false
      ).length;
      this.toDoService.todoList = this.allToDO;
      this.toDoService.todoList.forEach((x) => (x.isEditable = false));
    });
  }

  getRemainingDays(dueDate: string): number {
    const dueDateTime = new Date(dueDate).getTime();
    const currentTime = new Date().getTime();
    const remainingTime = dueDateTime - currentTime;
    return Math.ceil(remainingTime / (1000 * 60 * 60 * 24));
  }
}
