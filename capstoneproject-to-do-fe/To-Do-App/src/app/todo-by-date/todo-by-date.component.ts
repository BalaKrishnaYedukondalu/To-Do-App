import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router, ActivatedRoute } from '@angular/router';
import { NgToastService } from 'ng-angular-popup';
import { ToDo } from '../model/todo';
import { LoginService } from '../services/login.service';
import { TodoService } from '../services/todo.service';

@Component({
  selector: 'app-todo-by-date',
  templateUrl: './todo-by-date.component.html',
  styleUrls: ['./todo-by-date.component.css'],
})
export class TodoByDateComponent implements OnInit {
  constructor(
    private toDoService: TodoService,
    private userService: LoginService,
    private router: Router,
    private ar: ActivatedRoute,
    private toast: NgToastService,
    public dialog: MatDialog
  ) {}
  allToDo: ToDo[] = [];
  searchedDueDate: string = '';
  showMessage: boolean = false;
  p: any;

  ngOnInit(): void {
    this.getAllToDo();
  }

  @Output()
  sendData: EventEmitter<any> = new EventEmitter();
  
  searchinput() {
    this.sendData.emit(this.searchedDueDate);
  }
 

  deleteTaskById(title: any) {
    this.toDoService.deleteToDoByTitle(title).subscribe({
      next: (data) => {
        this.getAllToDo();
        this.toast.success({
          detail: 'ToDo Deleted successfully',
          summary: 'DELETED',
          duration: 4000,
        });
      },
      error: (err) =>
        this.toast.error({
          detail: 'Due to the network issue toDo is not deleted',
          summary: 'ERROR',
          duration: 4000,
        }),
    });
  }
  addToDo() {
    this.router.navigateByUrl('home/addToDo/');
  }

  search(title: string) {
    if (title === '' || !title) {
      this.getAllToDo();
    } else {
      this.allToDo = this.allToDo;
      this.allToDo = this.allToDo.filter(
        (data) =>
          data.title?.toLocaleLowerCase().includes(title) ||
          data.description?.toLocaleLowerCase().includes(title)
      ); 
    }
  }

  filter(priority: string) {
    if (priority == '') {
      this.getAllToDo();
    } else {
      this.toDoService
        .getAllListByPriority(priority)
        .subscribe((data) => (this.allToDo = data));
    }
  }

  completionStatus(isCompleted: boolean) {
    this.toDoService.getToDOByCompletedStatus(isCompleted).subscribe((data) => {
      this.allToDo = data;
    });
  }

  getAllToDo() {
    this.toDoService.getToDOByDate().subscribe((data) => {
      this.allToDo = data.filter(
        (a: { completed: boolean }) => a.completed == false
      );
      if (!(this.allToDo == null || this.allToDo.length == 0)) {
        this.showMessage = false;
      } else {
        this.showMessage = true;
      }
    });
  }

  updateTaskArchiveStatus(card: any) {
    this.toDoService.updateTaskArchiveStatus(card).subscribe({
      next: (data) => {
        this.toast.success({
          detail: 'Archive ToDo Is Added',
          summary: 'Added',
          duration: 4000,
        });
      },
      error: (err) => {
        this.toast.success({
          detail: 'due to the network issues',
          summary: 'Error',
          duration: 4000,
        });
      },
    });
  }

  updateTaskAsCompletedTask(card: any) {
    this.toDoService.updateTaskAsCompletedTask(card).subscribe({
      next: () => {
        this.getAllToDo();
        this.toast.success({
          detail: 'Completed ToDo Is Added',
          summary: 'Added',
          duration: 4000,
        });
      },
      error: (err) => {
        this.toast.success({
          detail: 'due to the network issues',
          summary: 'Error',
          duration: 4000,
        });
      },
    });
  }

  updateToDoAsImportantTask(card: any) {
    this.toDoService.updateToDoAsImportantTask(card).subscribe({
      next: () => {
        this.toast.success({
          detail: 'Important ToDo Is Added',
          summary: 'Added',
          duration: 4000,
        });
      },
      error: (err) => {
        this.toast.success({
          detail: 'due to the network issues',
          summary: 'Error',
          duration: 4000,
        });
      },
    });
  }
}
