import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { ToDo } from '../model/todo';
import { TodoService } from '../services/todo.service';
import { NgToastService } from 'ng-angular-popup';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { LoginService } from '../services/login.service';

@Component({
  selector: 'app-view-completed-todo',
  templateUrl: './view-completed-todo.component.html',
  styleUrls: ['./view-completed-todo.component.css'],
})
export class ViewCompletedTodoComponent implements OnInit {
  allToDO: ToDo[] = [];
  showMessage: boolean = true;

  constructor(
    private toDoService: TodoService,
    private toast: NgToastService,
    private userService: LoginService,
    private router: Router,
    private ar: ActivatedRoute,

    public dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.getToDOByCompletedStatus(true);
  }

  isOpen = false;
  animateButton() {
    let elem = <HTMLInputElement>document.getElementById('sidebar_sidenav');
    elem.setAttribute('visibility', 'visible');
    if (this.isOpen) this.isOpen = false;
    else this.isOpen = true;
  }

  selected = '';
  @Output()
  sendToContainer1: EventEmitter<any> = new EventEmitter();
  searchinput1() {
    this.sendToContainer1.emit(this.selected);
  }
  home() {
    this.router.navigateByUrl('/home/header');
  }

  important() {
    this.router.navigateByUrl('/home/viewimportant');
  }
  archive() {
    this.router.navigateByUrl('/home/viewarchive');
  }
  complete() {
    this.router.navigateByUrl('/home/viewcompleted');
  }

  Personal() {
    this.router.navigateByUrl('/home/Personal');
  }

  Travel() {
    this.router.navigateByUrl('/home/Travel');
  }

  getToDOByCompletedStatus(completed: boolean) {
    this.toDoService.getToDOByCompletedStatus(completed).subscribe((data) => {
      this.allToDO = data;
      if (!(this.allToDO == null || this.allToDO.length == 0)) {
        this.showMessage = false;
      } else {
        this.showMessage = true;
      }
    });
  }

  updateTaskArchiveStatus(card: any) {
    this.toDoService.updateTaskArchiveStatus(card).subscribe({
      next: () => {
        this.getToDOByCompletedStatus(true);
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
        this.getToDOByCompletedStatus(true);
        this.toast.success({
          detail: 'Completed ToDo Is Remove',
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
  p: any;

  deleteTaskById(title: any) {
    this.toDoService.deleteToDoByTitle(title).subscribe({
      next: (data) => {
        this.getToDOByCompletedStatus(true);
        this.toast.success({
          detail: 'ToDo Deleted successfully',
          summary: 'DELETED',
          duration: 4000,
        });
        this.allToDO;
      },
      error: (err) =>
        this.toast.error({
          detail: 'Due to the network issue toDo is not deleted',
          summary: 'ERROR',
          duration: 4000,
        }),
    });
  }
  search(title: string) {
    if (title === '' || !title) {
      this.getToDOByCompletedStatus(true);
    } else {
      // this.toDoService
      //   .getToDoByTitle(title)
      //   .subscribe((data) => (this.allToDO = data));
      this.allToDO = this.allToDO;
      this.allToDO = this.allToDO.filter(
        (data) =>
          data.title?.includes(title) || data.description?.includes(title)
      );
    }
  }

  filter(priority: string) {
    if (priority == '') {
      this.getToDOByCompletedStatus(true);
    } else {
      this.toDoService
        .getAllListByPriority(priority)
        .subscribe((data) => (this.allToDO = data));
    }
  }

  completionStatus(isCompleted: boolean) {
    this.toDoService.getToDOByCompletedStatus(isCompleted).subscribe((data) => {
      this.allToDO = data;
    });
  }
}
